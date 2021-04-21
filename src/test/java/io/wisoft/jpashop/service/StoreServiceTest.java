package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.store.Store;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@DisplayName("상점 Service 테스트")
class StoreServiceTest {

    @Autowired StoreService storeService;

    @Test
    @DisplayName("테스트 1. 정상 영업중인 상점 조회 테스트")
    void _1_findRunningStore() throws Exception {

        LocalDateTime localDateTime = LocalDateTime.parse("2021-03-01 09:00:00"
                , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<Store> runningStore = storeService.findRunningStore(localDateTime);

        Assertions.assertThat(runningStore.size()).isEqualTo(1);
        Assertions.assertThat(runningStore.get(0).getName()).isEqualTo("Normal Store1");
    }
}