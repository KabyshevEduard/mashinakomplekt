package com.mashinakomplekt.mysystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Topic> topics;
}
