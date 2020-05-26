package com.example.networkofgiving.models;

import javax.validation.constraints.*;
import java.io.Serializable;

public class RegistrationDTO implements Serializable {

    @NotNull
    @Pattern(regexp = "^[aA-zZ]\\w{5,29}$")
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Pattern(regexp = "^[aA-zZ][aA-zZ ]*$")
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 150)
    private Integer age;

    private Gender gender;

    @Pattern(regexp = "^[aA-zZ][aA-zZ ]*$")
    private String location;

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
}
