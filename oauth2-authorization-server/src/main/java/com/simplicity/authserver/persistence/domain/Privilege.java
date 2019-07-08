package com.simplicity.authserver.persistence.domain;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Privilege")
@Table(name="privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges", cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @Builder.Default
    private Collection<Role> roles = new HashSet<>();
}

