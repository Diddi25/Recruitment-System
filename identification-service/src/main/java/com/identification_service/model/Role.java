package com.identification_service.model;

import jakarta.persistence.*;

/**
 * Represents a role in the system.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnumRoles name;

    /**
     * Default constructor.
     */
    public Role() {

    }

    /**
     * Constructs a new role with the specified name.
     *
     * @param name the name of the role, represented as an enum
     */
    public Role(EnumRoles name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier for this role.
     *
     * @return the id of the role
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this role.
     *
     * @param id the id to set for the role
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of this role.
     *
     * @return the name of the role
     */
    public EnumRoles getName() {
        return name;
    }

    /**
     * Sets the name for this role.
     *
     * @param name the name to set for the role, represented as an enum
     */
    public void setName(EnumRoles name) {
        this.name = name;
    }
}
