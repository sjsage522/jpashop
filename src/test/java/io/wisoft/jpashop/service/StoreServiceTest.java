package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("상점 Service 테스트")
class StoreServiceTest {

    @InjectMocks
    StoreService storeService;

    @Mock
    StoreRepository storeRepository;

    @Test
    @DisplayName("테스트 01. 정상 영업중인 상점 조회 테스트 (businessHours 조건에 부합하는지 확인)")
    void _01_findRunningStore() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.parse("2021-03-01 09:00:00"
                , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<Store> stores = createStores();
        given(storeRepository.findByState(StoreState.NORMAL))
            .willReturn(stores);

        List<Store> runningStore = storeService.findRunningStore(localDateTime);

        Assertions.assertThat(runningStore.size()).isEqualTo(1);
        Assertions.assertThat(runningStore.get(0).getName()).isEqualTo("Normal Store1");
    }

    private List<Store> createStores() {
        List<Store> stores = new ArrayList<>();

        BusinessHours businessHours = new BusinessHours();
        businessHours.setOffDay(7);
        businessHours.setRun24(false);
        businessHours.setOpenTime(540);
        businessHours.setCloseTime(1080);

        BusinessHours businessHours2 = new BusinessHours();
        businessHours2.setOffDay(7);
        businessHours2.setRun24(false);
        businessHours2.setOpenTime(2000);
        businessHours2.setCloseTime(2001);

        stores.add(Store.from(
                "Normal Store1", StoreState.NORMAL, businessHours
        ));
        stores.add(Store.from(
                "Normal Store2", StoreState.NORMAL, businessHours2
        ));

        return stores;
    }
}