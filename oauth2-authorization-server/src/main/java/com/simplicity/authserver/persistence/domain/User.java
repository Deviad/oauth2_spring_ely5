package com.simplicity.authserver.persistence.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
//    @ManyToMany(
//            fetch = FetchType.EAGER,
//            cascade = {
//                CascadeType.MERGE
//            }
//    )
//    @JoinTable(
//            name = "users_privileges",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
//    )
//
//    private Set<Privilege> privileges = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();
}
