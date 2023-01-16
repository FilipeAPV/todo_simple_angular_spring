package com.mytodo.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytodo.backend.BaseEntity;
import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.task.TaskModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Column(nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @OneToMany(
            mappedBy = "userModel", // Specifies the name of the field in the TaskModel entity that represents the other side of the relationship..
            cascade = CascadeType.REMOVE
            )
    @JsonIgnore
    private List<TaskModel> taskModel = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "users_roles_user_userId_fk")),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "roleId",
                    nullable = false,
                    foreignKey = @ForeignKey(name = "users_roles_user_roleId_fk"))
    )
    @NotEmpty(message = "User must have a role assigned")
    private List<RoleModel> roleModel = new ArrayList<>();

    public UserModel(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserModel() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TaskModel> getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(List<TaskModel> taskModel) {
        this.taskModel = taskModel;
    }

    public List<RoleModel> getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(List<RoleModel> roleModel) {
        this.roleModel = roleModel;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
