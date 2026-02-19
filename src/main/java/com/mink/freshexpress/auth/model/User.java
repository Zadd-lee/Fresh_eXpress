package com.mink.freshexpress.auth.model;

import com.mink.freshexpress.auth.constant.Role;
import com.mink.freshexpress.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = true)
    @ColumnDefault("true")
    private boolean enabled;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void updatePassword(String newPassword) {
        this.password = newPassword;

    }

    public void delete() {
        this.enabled = false;

    }
}
