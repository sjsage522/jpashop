package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import io.wisoft.jpashop.domain.store.BusinessHours;
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
@DisplayName("즐겨찾기 상점 Repository 테스트")
class FavoriteStoreRepositoryTest {

    @Autowired FavoriteStoreRepository favoriteStoreRepository;

    @Test
    @DisplayName("테스트 01. 즐겨찾기 상점목록 조회 테스트")
    void _01_findFavoriteStoreByUserId() throws Exception {

        List<FavoriteStore> favoriteStores = favoriteStoreRepository.findByUserId(1L);

        Store store1 = favoriteStores.get(0).getStore();
        BusinessHours businessHours1 = store1.getBusinessHours();

        Store store2 = favoriteStores.get(1).getStore();
        BusinessHours businessHours2 = store2.getBusinessHours();

        assertThat(favoriteStores.size()).isEqualTo(2);

        assertThat(store1.getName()).isEqualTo("Hidden Store");
        assertThat(store1.getStoreState()).isEqualTo(StoreState.HIDDEN);
        assertThat(businessHours1.getOffDay()).isEqualTo(7);
        assertThat(businessHours1.isRun24()).isEqualTo(false);
        assertThat(businessHours1.getOpenTime()).isEqualTo(540);
        assertThat(businessHours1.getCloseTime()).isEqualTo(1080);

        assertThat(store2.getName()).isEqualTo("Normal Store1");
        assertThat(store2.getStoreState()).isEqualTo(StoreState.NORMAL);
        assertThat(businessHours2.getOffDay()).isEqualTo(7);
        assertThat(businessHours2.isRun24()).isEqualTo(false);
        assertThat(businessHours2.getOpenTime()).isEqualTo(540);
        assertThat(businessHours2.getCloseTime()).isEqualTo(1080);
    }

    @Test
    @DisplayName("테스트 02. 즐겨찾기 상점목록 조회 테스트")
    void _02_findFavoriteStoreByUserIdAndStoreId() throws Exception {

        boolean isPresent = favoriteStoreRepository.findByUserIdAndStoreId(2L, 1L).isPresent();
        boolean isPresent2 = favoriteStoreRepository.findByUserIdAndStoreId(1L, 1L).isPresent();
        assertThat(isPresent).isFalse();
        assertThat(isPresent2).isTrue();
    }

    @Test
    @DisplayName("테스트 03. 즐겨찾기 상점 삭제 테스트")
    void _03_deleteByUserIdAndStoreId() throws Exception {

        int delete = favoriteStoreRepository.deleteByUserIdAndStoreId(1L, 1L);
        assertThat(delete).isEqualTo(1);
    }
}