package com.mink.freshexpress.user.model;

import com.mink.freshexpress.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Columns;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String phone;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enabled;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<UserRole> userRoles = new ArrayList<>();

}
