package com.example.networkofgiving.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "charities")
public class Charity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal amountRequired;

    @Column(nullable = false)
    private BigDecimal amountCollected;

    @Column(nullable = false)
    private Integer volunteersRequired;

    @Column(nullable = false)
    private Integer volunteersApplied;

    @Lob
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(updatable = false)
    @CreationTimestamp
    private Date created_at;

    public Charity(String title,
                   String description,
                   BigDecimal amountRequired,
                   Integer volunteersRequired,
                   String thumbnail,
                   User owner) {
        this.title = title;
        this.description = description;
        this.amountRequired = amountRequired;
        this.amountCollected = new BigDecimal(0.0);
        this.volunteersRequired = volunteersRequired;
        this.volunteersApplied = 0;
        this.thumbnail = thumbnail;
        this.owner = owner;
    }

    public Charity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(BigDecimal amountCollected) {
        this.amountCollected = amountCollected;
    }

    public Integer getVolunteersRequired() {
        return volunteersRequired;
    }

    public void setVolunteersRequired(Integer volunteersRequired) {
        this.volunteersRequired = volunteersRequired;
    }

    public Integer getVolunteersApplied() {
        return volunteersApplied;
    }

    public void setVolunteersApplied(Integer volunteersApplied) {
        this.volunteersApplied = volunteersApplied;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
