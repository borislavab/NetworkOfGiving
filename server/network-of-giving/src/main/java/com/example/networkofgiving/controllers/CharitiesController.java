package com.example.networkofgiving.controllers;

import com.example.networkofgiving.models.CharityBasicResponseDTO;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.CharityDetailsResponseDTO;
import com.example.networkofgiving.services.ICharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/charities")
public class CharitiesController {

    @Autowired
    private ICharityService charityService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void createCharity(@Valid @RequestBody CharityCreationDTO charityCreationDTO) {
        this.charityService.createCharity(charityCreationDTO);
    }

    @GetMapping
    public List<CharityBasicResponseDTO> getAllCharities(@RequestParam Optional<String> titleFilter) {
        return this.charityService.getAllCharities(titleFilter)
                .stream()
                .map(CharityBasicResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public CharityDetailsResponseDTO getCharityDetails(@NotNull @PathVariable Long id) {
        return new CharityDetailsResponseDTO(this.charityService.getCharityById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharityById(@NotNull @PathVariable Long id) {
        this.charityService.deleteCharityById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editCharity(@NotNull @PathVariable Long id,
                            @Valid @RequestBody CharityCreationDTO charityCreationDTO) {
        this.charityService.editCharity(id, charityCreationDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public void badRequest() {
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoSuchElementException.class
    })
    public void notFound() {
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            AccessDeniedException.class
    })
    public void unauthorized() {
    }
}
