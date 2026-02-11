package com.mink.freshexpress.user.model;

import com.mink.freshexpress.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "role_id")
    private List<UserRole> userRoles = new ArrayList<>();
}
