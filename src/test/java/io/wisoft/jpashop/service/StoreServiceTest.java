package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = StoreService.class)
@DisplayName("상점 Service 테스트")
class StoreServiceTest {

    @Autowired
    StoreService storeService;

    @MockBean
    StoreRepository storeRepository;

    @Test
    @DisplayName("테스트 1. 정상 영업중인 상점 조회 테스트")
    void _1_findRunningStore() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.parse("2021-03-01 09:00:00"
                , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<Store> stores = createStores();
        BDDMockito.given(storeRepository.findByState(StoreState.NORMAL))
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

        stores.add(Store.from(
                "Normal Store1", StoreState.NORMAL, businessHours
        ));

        return stores;
    }
}