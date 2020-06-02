package com.example.networkofgiving.entities;

import com.example.networkofgiving.models.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String location;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Charity> ownedCharities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Volunteering> volunteerings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "donator")
    private Set<Donation> donations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Event> events;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password, String name, Integer age, Gender gender, String location) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.location = location;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Charity> getOwnedCharities() {
        return ownedCharities;
    }

    public void setOwnedCharities(Set<Charity> ownedCharities) {
        this.ownedCharities = ownedCharities;
    }

    public Set<Volunteering> getVolunteerings() {
        return volunteerings;
    }

    public void setVolunteerings(Set<Volunteering> volunteerings) {
        this.volunteerings = volunteerings;
    }

    public Set<Donation> getDonations() {
        return donations;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
