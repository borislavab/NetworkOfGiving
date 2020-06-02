package com.example.networkofgiving.models;

import java.io.Serializable;
import java.util.List;

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
}
