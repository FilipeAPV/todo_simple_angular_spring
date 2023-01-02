package com.mytodo.backend.user;

import com.mytodo.backend.task.TaskModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "userModel") // Specifies the name of the field in the TaskModel entity that represents the other side of the relationship.
    private List<TaskModel> taskModel;
}
