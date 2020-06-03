package com.example.networkofgiving.controllers;

import com.example.networkofgiving.models.CharityCreationDTO;
import com.example.networkofgiving.models.CharityDetailsResponseDTO;
import com.example.networkofgiving.models.CharityResponseDTO;
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

    /**
     * Create new charity
     *
     * @param charityCreationDTO Contains the information needed to register a charity
     * @return
     * 201 CREATED status code on success
     * 400 BAD_REQUEST status code when both the requested amount and volunteer count are zero
     * or request body is invalid.
     * 403 FORBIDDEN when requested user is not authenticated
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void createCharity(@Valid @RequestBody CharityCreationDTO charityCreationDTO) {
        this.charityService.createCharity(charityCreationDTO);
    }

    /**
     * Get information about existing charities
     *
     * Available for both authenticated and unauthenticated users
     *
     * @param titleFilter An optional parameter for filtering only charities
     *                    with titles that contain the titleFilter string.

     * @return 200 OK status code on success along with information about the charities
     */
    @GetMapping
    public List<CharityResponseDTO> getAllCharities(@RequestParam Optional<String> titleFilter) {
        return this.charityService.getAllCharities(titleFilter)
                .stream()
                .map(CharityResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get details about an existing charity
     *
     * Available for both authenticated and unauthenticated users
     *
     * @param id The id of the charity

     * @return
     * 200 OK status code on success along with information about the charity
     * 404 NOT_FOUND status code on invalid charity id.
     */
    @GetMapping("{id}")
    public CharityDetailsResponseDTO getCharityDetails(@NotNull @PathVariable Long id) {
        return new CharityDetailsResponseDTO(this.charityService.getCharityById(id));
    }

    /**
     * Delete a charity
     *
     * @param id The id of the charity to delete
     *
     * @return
     * 201 NO_CONTENT status code on success
     * 403 FORBIDDEN status code if the user making the request is not
     * authenticated as the owner of the charity
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharityById(@NotNull @PathVariable Long id) {
        this.charityService.deleteCharityById(id);
    }

    /**
     * Edit a charity
     *
     * @param id The id of the charity to edit
     * @param charityCreationDTO Contains the updated charity information

     * @return
     * 201 NO_CONTENT status code on success
     * 403 FORBIDDEN status code if the user making the request is not
     * authenticated as the owner of the charity
     * 400 BAD_REQUEST status code when the request body is invalid.
     */
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
