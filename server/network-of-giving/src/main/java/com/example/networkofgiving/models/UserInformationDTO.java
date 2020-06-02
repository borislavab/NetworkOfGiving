package com.example.networkofgiving.models;

import com.example.networkofgiving.entities.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserInformationDTO implements Serializable {
    private Long id;
    private String username;
    private String name;
    private Integer age;
    private Gender gender;
    private String location;
    private List<CharityBasicResponseDTO> ownedCharities;
    private List<VolunteerDTO> volunteerings;
    private List<DonationDTO> donations;

    public UserInformationDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.location = user.getLocation();
        this.ownedCharities = user.getOwnedCharities()
                .stream()
                .map(CharityBasicResponseDTO::new)
                .collect(Collectors.toList());
        this.volunteerings = user.getVolunteerings()
                .stream()
                .map(VolunteerDTO::new)
                .collect(Collectors.toList());
        this.donations = user.getDonations()
                .stream()
                .map(DonationDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLocation() {
        return location;
    }

    public List<CharityBasicResponseDTO> getOwnedCharities() {
        return ownedCharities;
    }

    public List<VolunteerDTO> getVolunteerings() {
        return volunteerings;
    }

    public List<DonationDTO> getDonations() {
        return donations;
    }
}
