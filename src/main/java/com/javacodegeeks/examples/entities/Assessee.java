package com.javacodegeeks.examples.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by rana on 2/19/17.
 */
@Entity
@Table(name = "ASSESSEES")
public class Assessee implements Serializable{
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "ASSESSEES_SEQUENCE_GENERATOR", sequenceName = "ASSESSEES_ID_SEQUENCE")

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ASSESSEES_SEQUENCE_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "DISTRICT_NAME", nullable = false)
    private String districtName;

    @Column(name = "MINIMUM_TAX")
    private BigDecimal minimumTax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public BigDecimal getMinimumTax() {
        return minimumTax;
    }

    public void setMinimumTax(BigDecimal minimumTax) {
        this.minimumTax = minimumTax;
    }
}
