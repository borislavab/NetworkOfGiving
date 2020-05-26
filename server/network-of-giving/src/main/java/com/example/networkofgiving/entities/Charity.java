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
}
