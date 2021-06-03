package io.wisoft.jpashop.repository;

import io.wisoft.jpashop.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * paging 처리를 하지 않기 떄문에 1:N (order, orderItems) 관계에서도 fetch join 처리
     * jpa 단에서 데이터가 뻥튀기 되므로 jpa 에서 제공하는 distinct 키워드를 통해 해결
     */
    @Query(
            "select distinct o from Order o" +
                    " join fetch o.user u" +
                    " join fetch o.store" +
                    " join fetch o.orderItems" +
                    " where u.id = :userId" +
                    " and o.createdAt >= :fromDate" +
                    " and o.createdAt <= :toDate" +
                    " order by o.id desc"
    )
    List<Order> findAll(Long userId, LocalDateTime fromDate, LocalDateTime toDate);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
