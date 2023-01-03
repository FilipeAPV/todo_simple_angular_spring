package com.mytodo.backend.user;

import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.task.TaskModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(
            mappedBy = "userModel" // Specifies the name of the field in the TaskModel entity that represents the other side of the relationship.
            )
    private List<TaskModel> taskModel;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId"),
                    foreignKey = @ForeignKey(name = "users_roles_user_userId_fk"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "roleId",
                    foreignKey = @ForeignKey(name = "users_roles_user_roleId_fk"))
    )
    private List<RoleModel> roleModel;
}
