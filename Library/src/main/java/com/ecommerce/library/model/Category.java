package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonBackReference;
=======
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
>>>>>>> ea29b7d2e076553492471404a590f1dc07c94861

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Column(name = "is_active")
    private boolean is_active;

<<<<<<< HEAD
=======
    @Column(name = "is_deleted")
    private boolean is_deleted;

>>>>>>> ea29b7d2e076553492471404a590f1dc07c94861
    public Category(String name) {
        this.name = name;
        this.is_active = true;
        this.is_deleted = false;
    }

}
