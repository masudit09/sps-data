package com.sps.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sps.data.enumeration.ParagraphIndexType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rana on 2/19/17.
 */
@Entity
@Table(name = "PARAGRAPHS")
public class Paragraph implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "PARAGRAPH_SEQUENCE_GENERATOR", sequenceName = "PARAGRAPH_ID_SEQUENCE")

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARAGRAPH_SEQUENCE_GENERATOR")
    private Long id;

    @Column(name = "HEADER_CHAR")
    private String headerChar;

    @Column(name = "SERIAL")
    private Integer serial;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "HAS_INDEX")
    private Boolean hasIndex = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "INDEX_TYPE")
    private ParagraphIndexType indexType;

    @Column(name = "INDEX_CHAR")
    private String indexChar;

    @JsonIgnoreProperties("section")
    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private Section section;


    public Paragraph() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeaderChar() {
        return headerChar;
    }

    public void setHeaderChar(String headerChar) {
        this.headerChar = headerChar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getHasIndex() {
        return hasIndex;
    }

    public void setHasIndex(Boolean hasIndex) {
        this.hasIndex = hasIndex;
    }

    public ParagraphIndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(ParagraphIndexType indexType) {
        this.indexType = indexType;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getIndexChar() {
        return indexChar;
    }

    public void setIndexChar(String indexChar) {
        this.indexChar = indexChar;
    }
}