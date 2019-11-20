package com.sps.data.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static org.olap4j.metadata.XmlaConstants.Content.Data;

/**
 * Created by rana on 11/12/19.
 */
@Entity
@Table(name = "CHAPTERS")
public class Chapter {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "CHAPTERS_SEQUENCE_GENERATOR", sequenceName = "CHAPTERS_ID_SEQUENCE")

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CHAPTERS_SEQUENCE_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NAME_BANGLA")
    private String nameBangla;

    @Column(name = "SERIAL")
    private Integer serial;

    @Column(name = "TITLE")
    private String title;

    public Chapter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameBangla() {
        return nameBangla;
    }

    public void setNameBangla(String nameBangla) {
        this.nameBangla = nameBangla;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
