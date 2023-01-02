package com.mytodo.backend.task;

import com.mytodo.backend.user.UserModel;
import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id") // Specifies the name of the column in this table (task) that represents the foreign key to the user table.
    private UserModel userModel;

}
