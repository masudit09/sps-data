package com.sps.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rana on 2/19/17.
 */
@Entity
@Table(name = "SECTIONS")
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "SECTION_SEQUENCE_GENERATOR", sequenceName = "SECTION_ID_SEQUENCE")

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SECTION_SEQUENCE_GENERATOR")
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NAME_BANGLA")
    private String nameBangla;

    @Column(name = "SERIAL")
    private Integer serial;

    @JsonIgnoreProperties("sections")
    @ManyToOne
    @JoinColumn(name = "CHAPTER_ID")
    private Chapter chapter;

    @JsonIgnoreProperties("section")
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private List<Paragraph> paragraphs = new ArrayList<Paragraph>();

    public Section() {}

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

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getHeader() {

        return (this.getChapter() != null? this.getChapter().getSerial():"")+"." + this.serial +" "+ this.nameBangla;
    }
}
