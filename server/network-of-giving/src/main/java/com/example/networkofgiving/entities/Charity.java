package com.example.networkofgiving.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

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

    private Byte[] image;

    public Charity(String title,
                   String description,
                   BigDecimal amountRequired,
                   Integer volunteersRequired,
                   Byte[] image) {
        this.title = title;
        this.description = description;
        this.amountRequired = amountRequired;
        this.amountCollected = new BigDecimal(0.0);
        this.volunteersRequired = volunteersRequired;
        this.volunteersApplied = 0;
        this.image = image;
    }
}
