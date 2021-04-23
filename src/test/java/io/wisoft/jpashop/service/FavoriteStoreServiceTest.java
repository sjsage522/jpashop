package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.store.BusinessHours;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import io.wisoft.jpashop.domain.user.Email;
import io.wisoft.jpashop.domain.user.User;
import io.wisoft.jpashop.repository.FavoriteStoreRepository;
import io.wisoft.jpashop.repository.StoreRepository;
import io.wisoft.jpashop.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = FavoriteStoreService.class)
@DisplayName("즐겨찾기 상점 Service 테스트")
class FavoriteStoreServiceTest {

    @Autowired
    FavoriteStoreService favoriteStoreService;

    @MockBean
    UserRepository userRepository;
    @MockBean
    StoreRepository storeRepository;
    @MockBean
    FavoriteStoreRepository favoriteStoreRepository;

    @Test
    @DisplayName("테스트 1. 즐겨찾기 상점 추가 테스트")
    void _1_addFavoriteStore() throws Exception {

        User user = createUser();
        Store store = createStore();

        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(user));
        BDDMockito.given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        favoriteStoreService.addFavoriteStore(1L ,1L);
    }

    private User createUser() {
        return User.from("junseok", Email.from("test1@gmail.com"), "12345678");
    }

    private Store createStore() {

        BusinessHours businessHours = new BusinessHours();
        businessHours.setOffDay(7);
        businessHours.setRun24(false);
        businessHours.setOpenTime(540);
        businessHours.setCloseTime(1080);

        return Store.from("Normal Store1", StoreState.NORMAL, businessHours);
    }
}