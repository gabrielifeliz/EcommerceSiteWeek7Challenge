package com.example.demo.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String username;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AppRole> roles;

    @OneToMany(mappedBy = "myItem")
    Set<Item> myItems;

    public AppUser() {
        roles = new HashSet<>();
    }

    public AppUser(@NotEmpty @Size(min = 1, max = 255) String firstName,
                   @NotEmpty @Size(min = 1, max = 255) String lastName,
                   @NotEmpty @Size(min = 1, max = 255) String email,
                   @NotEmpty @Size(min = 1, max = 255) String username,
                   @NotEmpty @Size(min = 1, max = 255) String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        setPassword(password);
        roles = new HashSet<>();
    }

    public AppUser(String username, String password) {
        this.username = username;
        setPassword(password);
        roles = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    public void addRole(AppRole role) {
        roles.add(role);
    }

    public Set<Item> getMyItems() {
        return myItems;
    }

    public void setMyItems(Set<Item> myItems) {
        this.myItems = myItems;
    }
}