package io.wisoft.jpashop.domain.store;

import io.wisoft.jpashop.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "stores")
@Getter
public class Store extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private StoreState storeState;

    @Embedded
    private BusinessHours businessHours;

    protected Store() {}

    public Store(final String name, final StoreState storeState, final BusinessHours businessHours) {
        this.name = name;
        this.storeState = storeState;
        this.businessHours = businessHours;
    }

    public static Store from(final String name, final StoreState storeState, final BusinessHours businessHours) {
        return new Store(name, storeState, businessHours);
    }
}
