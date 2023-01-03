package com.mytodo.backend.role;

import jakarta.persistence.*;

import java.beans.ConstructorProperties;

@Entity
@Table(name = "role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;

    @Column(nullable = false)
    private String name;

}
