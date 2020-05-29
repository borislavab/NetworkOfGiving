package com.example.networkofgiving.controllers;

import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.CharityResponseDTO;
import com.example.networkofgiving.services.ICharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
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
    public List<CharityResponseDTO> getAllCharities() {
        return this.charityService.getAllCharities()
                .stream()
                .map(CharityResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public CharityResponseDTO getCharityById(@NotNull @PathVariable Long id) {
        return new CharityResponseDTO(this.charityService.getCharityById(id));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public void badRequest() { }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoSuchElementException.class
    })
    public void notFound() {}
}
