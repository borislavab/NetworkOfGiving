package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.repositories.ICharityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CharityService implements ICharityService {

    @Autowired
    private ICharityRepository charityRepository;

    @Override
    public void createCharity(CharityCreationDTO charityCreationDTO) {
        BigDecimal amountRequired = charityCreationDTO.getAmountRequired();
        if (amountRequired.compareTo(BigDecimal.ZERO) == 0 &&
            charityCreationDTO.getVolunteersRequired() == 0) {
            throw new IllegalArgumentException();
        }
        Charity newCharity = this.constructCharityFromCharityCreationDTO(charityCreationDTO);
        this.charityRepository.saveAndFlush(newCharity);
    }

    @Override
    public List<Charity> getAllCharities() {
        return this.charityRepository.findAll();
    }

    @Override
    public Charity getCharityById(Long id) {
        return this.charityRepository.findById(id).get();
    }

    private Charity constructCharityFromCharityCreationDTO(CharityCreationDTO charityCreationDTO) {
        return new Charity(
                charityCreationDTO.getTitle(),
                charityCreationDTO.getDescription(),
                charityCreationDTO.getAmountRequired(),
                charityCreationDTO.getVolunteersRequired(),
                charityCreationDTO.getThumbnail()
        );
    }
}
