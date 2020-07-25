package com.product.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;
}
