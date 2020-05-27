package com.example.networkofgiving.controllers;

import com.example.networkofgiving.entities.Charity;
import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.services.ICharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

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
    public List<Charity> getAllCharities() {
        return this.charityService.getAllCharities();
    }

    @GetMapping("{id}")
    public Charity getCharityById(@NotNull @PathVariable Long id) {
        return this.charityService.getCharityById(id);
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
