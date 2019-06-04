package com.example.springdemo.persistence.domain;


import lombok.Data;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@Data
@Entity(name = "UserInfo")
@Table(name = "userinfo")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(
            mappedBy = "userInfo",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }

    )
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String surname;

    private String telephone;


}
