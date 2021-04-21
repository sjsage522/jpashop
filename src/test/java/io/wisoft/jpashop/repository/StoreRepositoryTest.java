package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("상점 Repository 테스트")
class StoreRepositoryTest {

    @Autowired StoreRepository storeRepository;

    @Test
    @DisplayName("테스트 1. 상점 조회 테스트")
    void _1_findStoreByStateTest() throws Exception {

        List<Store> hiddenStores = storeRepository.findByState(StoreState.HIDDEN);
        List<Store> normalStores = storeRepository.findByState(StoreState.NORMAL);

        assertThat(hiddenStores.size()).isEqualTo(1);
        assertThat(normalStores.size()).isEqualTo(2);
        assertThat(hiddenStores.get(0).getName()).isEqualTo("Hidden Store");
    }
}