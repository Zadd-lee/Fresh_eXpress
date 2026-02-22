package com.mink.freshexpress.category.model;

import com.mink.freshexpress.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Category parent;

    @Column(name = "depth")
    private Long depth;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean enable;

    @OneToMany(mappedBy = "parent")
    List<Category> children = new ArrayList<>();


    public void updateDepth(Long depth) {
        this.depth = depth;
    }

    public void addParent(Category parent) {
        this.parent = parent;
    }

    public void delete() {
        this.enable = false;
    }
}
