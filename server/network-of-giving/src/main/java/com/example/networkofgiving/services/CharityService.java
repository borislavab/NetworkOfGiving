package com.example.networkofgiving.services;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.repositories.ICharityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CharityService implements ICharityService {

    @Autowired
    private ICharityRepository charityRepository;

    @Autowired
    private IUserService userService;

    @Override
    public void createCharity(CharityCreationDTO charityCreationDTO) throws IllegalArgumentException {
        BigDecimal amountRequired = charityCreationDTO.getAmountRequired();
        if (amountRequired.compareTo(BigDecimal.ZERO) == 0 &&
                charityCreationDTO.getVolunteersRequired() == 0) {
            throw new IllegalArgumentException();
        }
        Charity newCharity = this.constructCharityFromCharityCreationDTO(charityCreationDTO);
        this.charityRepository.saveAndFlush(newCharity);
    }

    @Transactional
    @Override
    public List<Charity> getAllCharities(Optional<String> titleFilter) {
        String filter = titleFilter.orElse("");
        return this.charityRepository.findByTitleContaining(filter);
    }

    @Override
    public Charity getCharityById(Long id) throws NoSuchElementException {
        return this.charityRepository.findById(id).get();
    }

    @Override
    public void deleteCharityById(Long id) throws AccessDeniedException {
        Optional<Charity> charityToDelete = this.charityRepository.findById(id);
        if (charityToDelete.isEmpty()) {
            return;
        }
        Charity charity = charityToDelete.get();
        Long userId = this.userService.getCurrentlyAuthenticatedUser().getId();
        if (userId.equals(charity.getOwnerId())) {
            this.charityRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Only an owner can delete a charity");
        }
    }

    @Override
    public void updateCharity(Charity charity) {
        if (this.charityRepository.existsById(charity.getId())) {
            this.charityRepository.save(charity);
        }
    }

    private Charity constructCharityFromCharityCreationDTO(CharityCreationDTO charityCreationDTO) {
        User owner = this.userService.getCurrentlyAuthenticatedUser();
        return new Charity(
                charityCreationDTO.getTitle(),
                charityCreationDTO.getDescription(),
                charityCreationDTO.getAmountRequired(),
                charityCreationDTO.getVolunteersRequired(),
                charityCreationDTO.getThumbnail(),
                owner
        );
    }
}
