package com.simplicity.resourceserver.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@Entity(name = "User")
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToOne
    private UserInfo userInfo;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();
}
