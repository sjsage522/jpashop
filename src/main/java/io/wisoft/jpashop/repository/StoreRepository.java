package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.store.StoreState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s " +
            "where s.storeState = :storeState " +
            "order by s.id desc")
    List<Store> findByState(final StoreState storeState);
}
