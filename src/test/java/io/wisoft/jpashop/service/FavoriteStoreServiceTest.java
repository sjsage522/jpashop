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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayName("즐겨찾기 상점 Service 테스트")
class FavoriteStoreServiceTest {

    @InjectMocks
    FavoriteStoreService favoriteStoreService;

    @Mock
    UserRepository userRepository;
    @Mock
    StoreRepository storeRepository;
    @Mock
    FavoriteStoreRepository favoriteStoreRepository;

    @Test
    @DisplayName("테스트 01. 즐겨찾기 상점 추가 테스트 (예외가 발생하면 안됨)")
    void _01_addFavoriteStore() throws Exception {

        // given
        User user = createUser();
        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        given(userRepository.findById(fakeUserId)).willReturn(Optional.of(user));

        Store store = createStore();
        Long fakeStoreId = 1L;
        ReflectionTestUtils.setField(store, "id", fakeStoreId);
        given(storeRepository.findById(fakeStoreId)).willReturn(Optional.of(store));

        // when
        favoriteStoreService.addFavoriteStore(fakeUserId, fakeStoreId);

        // then
    }

    @Test
    @DisplayName("테스트 02. 즐겨찾기 상점 삭제 테스트 (예외가 발생하면 안됨)")
    void _02_deleteFavoriteStore() throws Exception {

        // given
        User user = createUser();
        Long fakeUserId = 1L;
        ReflectionTestUtils.setField(user, "id", fakeUserId);
        given(userRepository.findById(fakeUserId)).willReturn(Optional.of(user));

        Store store = createStore();
        Long fakeStoreId = 1L;
        ReflectionTestUtils.setField(store, "id", fakeStoreId);
        given(storeRepository.findById(fakeStoreId)).willReturn(Optional.of(store));

        // when
        favoriteStoreService.deleteFavoriteStore(fakeUserId, fakeStoreId);

        // then
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