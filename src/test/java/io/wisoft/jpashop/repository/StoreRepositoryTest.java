package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("상점 Repository 테스트")
class StoreRepositoryTest {

    @Autowired StoreRepository storeRepository;

    @Test
    @DisplayName("테스트 01. 상점 조회 테스트 (상점 상태에 따라 올바른 조회가 되는지 확인)")
    void _01_findStoreByStateTest() throws Exception {

        List<Store> hiddenStores = storeRepository.findByState(StoreState.HIDDEN);
        List<Store> normalStores = storeRepository.findByState(StoreState.NORMAL);

        assertThat(hiddenStores.size()).isEqualTo(1);
        assertThat(normalStores.size()).isEqualTo(2);
        assertThat(hiddenStores.get(0).getName()).isEqualTo("Hidden Store");
    }
}