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

    public RoleModel(String name) {
        this.name = name;
    }

    public RoleModel() {

    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
