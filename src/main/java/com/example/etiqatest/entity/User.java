package com.example.etiqatest.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @NotNull
    private long id;

    @Column(name = "Username",unique=true)
    @NotNull
    private String username;

    @Column(name = "Password")
    @NotNull
    private String password;

    @Column(name = "Email")
    @NotNull
    private String email_id;

    @Column(name = "Address")
    @NotNull
    private String address;

    @ManyToMany
    @NotNull
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Roles> roles;

}