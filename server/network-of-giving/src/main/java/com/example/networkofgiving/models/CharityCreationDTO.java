package com.example.networkofgiving.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CharityCreationDTO {

    @NotNull
    @Size(min = 5, max = 50)
    private String title;

    @NotNull
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal amountRequired;

    @NotNull
    @PositiveOrZero
    private Integer volunteersRequired;

    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountRequired() {
        return amountRequired;
    }

    public void setAmountRequired(BigDecimal amountRequired) {
        this.amountRequired = amountRequired;
    }

    public Integer getVolunteersRequired() {
        return volunteersRequired;
    }

    public void setVolunteersRequired(Integer volunteersRequired) {
        this.volunteersRequired = volunteersRequired;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
