package com.idus.hw.core.user.domain.user.entity;

import lombok.Getter;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@Subselect("SELECT MAX(id) AS id, user_id " +
        "FROM orders " +
        "GROUP BY user_id"
)
@Synchronize("orders")
public class LastUserOrder {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;
}
