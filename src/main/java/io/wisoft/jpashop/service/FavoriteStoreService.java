package io.wisoft.jpashop.service;

import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import io.wisoft.jpashop.repository.FavoriteStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteStoreService {

    private final FavoriteStoreRepository favoriteStoreRepository;

    public List<FavoriteStore> findByUserId(final Long userId) {
        return favoriteStoreRepository.findByUserId(userId);
    }
}
