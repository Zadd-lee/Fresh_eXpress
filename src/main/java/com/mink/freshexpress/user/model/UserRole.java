package com.mink.freshexpress.user.model;

import com.mink.freshexpress.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class UserRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

}
