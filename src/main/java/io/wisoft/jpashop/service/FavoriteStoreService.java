package io.wisoft.jpashop.service;

import com.google.common.base.Preconditions;
import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.user.User;
import io.wisoft.jpashop.repository.FavoriteStoreRepository;
import io.wisoft.jpashop.repository.StoreRepository;
import io.wisoft.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteStoreService {

    private final FavoriteStoreRepository favoriteStoreRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public List<FavoriteStore> findByUserId(final Long userId) {
        return favoriteStoreRepository.findByUserId(userId);
    }

    @Transactional
    public FavoriteStore addFavoriteStore(final Long userId, final Long storeId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("user is not exists."));
        Store findStore = storeRepository.findById(storeId).orElseThrow(() -> new IllegalArgumentException("store is not exists."));

        if (favoriteStoreRepository.findByUserIdAndStoreId(userId, storeId).isPresent())
            throw new IllegalArgumentException("already exist favorite store.");

        return favoriteStoreRepository.save(FavoriteStore.from(findUser, findStore));
    }
}
