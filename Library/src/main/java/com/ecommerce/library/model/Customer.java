package com.ecommerce.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="customers", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "phone"}))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;

    private String firstName;
    private String lastName;
    private String userName;

    @Column(name = "phone")
    private String phoneNumber;


    private String password;
    private String address;

    @OneToOne(mappedBy = "customer")
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;
}
