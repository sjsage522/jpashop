package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.favoritestore.FavoriteStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {

    @Query("select fs " +
            "from FavoriteStore fs " +
            "join fetch fs.store s " +
            "where fs.id = s.id " +
            "and fs.user.id = :userId " +
            "order by fs.id desc")
    List<FavoriteStore> findByUserId(final Long userId);

    Optional<FavoriteStore> findByUserIdAndStoreId(final Long userId, final Long storeId);

    int deleteByUserIdAndStoreId(final Long userId, final Long storeId);
}
