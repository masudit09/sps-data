package com.javacodegeeks.examples.entities;


import com.javacodegeeks.examples.enumeration.Role;
import com.javacodegeeks.examples.util.Util;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "User.findById", query = "SELECT e FROM User e WHERE e.id = :id")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @SequenceGenerator(
            name = "USER_SEQUENCE_GENERATOR",
            sequenceName = "USER_ID_SEQUENCE"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQUENCE_GENERATOR")
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, precision = 38, scale = 0)
    private Integer id;
    
    @Column(name = "USERNAME", length = 50)
    @NotEmpty(message = "Username is required.")
    private String username;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Transient
    private String plainPassword;

    @Column(name = "FULL_NAME", length = 100)
    private String fullName;

    @Column(name = "FULL_NAME_BANGLA", length = 200)
    private String fullNameBangla;

    @Column(name = "ENABLED")
    private Boolean enabled;


    @Transient
    private List<Role> roles;
    
    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getStatus() {
        return enabled ? "Active" : "De-active";
    }

    public void setActive(Boolean active) {
        this.enabled = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameBangla() {
        return fullNameBangla;
    }

    public void setFullNameBangla(String fullNameBangla) {
        this.fullNameBangla = fullNameBangla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
    @PrePersist 
    void onPrePersist() {
    	if (!StringUtils.isEmpty(getPlainPassword())) {
            setPassword(Util.getEncoder().encode(getPlainPassword()));
        }
    }

    public void setRolesFromAuthorities(List<Authority> authorities) {
        if(authorities == null) {
            this.roles = null;
            return;
        }

        this.roles = new ArrayList<Role>();
        for(Authority authority : authorities) {
            this.roles.add(Role.valueOf(authority.getAuthority()));
        }
    }

  
}
