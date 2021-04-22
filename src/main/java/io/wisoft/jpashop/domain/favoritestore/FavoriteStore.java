package io.wisoft.jpashop.domain.favoritestore;

import io.wisoft.jpashop.domain.BaseTimeEntity;
import io.wisoft.jpashop.domain.store.Store;
import io.wisoft.jpashop.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "favorite_stores")
@Getter
public class FavoriteStore extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    protected FavoriteStore() {}
}
