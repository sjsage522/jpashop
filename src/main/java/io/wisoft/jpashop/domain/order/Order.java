package io.wisoft.jpashop.domain.order;

import io.wisoft.jpashop.domain.BaseTimeEntity;
import io.wisoft.jpashop.domain.orderitem.OrderItem;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @OneToMany(mappedBy = "order")
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private Cancel cancel;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    protected Order() {}

    private Order(Store store, User user, List<OrderItem> orderItems, OrderState orderState) {
        this.store = store;
        this.user = user;
        for (OrderItem orderItem : orderItems) addOrderItem(orderItem);
        this.orderState = orderState;
    }

    private void addOrderItem(OrderItem orderItem) {
        orderItem.addOrder(this);   /* 연관 관계 주인에서 데이터 작업을 해야함. */
        orderItems.add(orderItem);
    }

    public static Order from(Store store, User user, List<OrderItem> orderItems, OrderState orderState) {
        return new Order(store, user, orderItems, orderState);
    }

    public void setCancel(Cancel cancel) {
        this.cancel = cancel;
    }

    public void changeOrderState(OrderState orderState) { /* 단순 set..이 아닌 의미있는 메소드 이름으로 정의 */
        this.orderState = orderState;
    }

    public int getTotalPrice(List<OrderItem> orderItems) {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            int unitPrice = orderItem.getUnitPrice();
            int unitCount = orderItem.getUnitCount();
            totalPrice += unitPrice * unitCount;
        }
        return totalPrice;
    }
}
