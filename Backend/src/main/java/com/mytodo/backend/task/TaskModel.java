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

    /**
     * name = "user_id",
     *   Specifies the name of this column in this table `task` that represents the foreign key to the user table.
     *
     * referencedColumnName = "userId",
     *   Specifies the name of the primary key column in the `user` table that this column (the FK) is referencing.
     *
     * foreignKey = @ForeignKey(name = "fk_category"))
     *   Name of the foreign key constraint.
     *   Foreign key constraint ensures that the value in the foreign key column (this one `user_id`) must be a valid primary key value in the `user` table
     */
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId",
            // FK naming: tableWHereIsLocated_tableOfThePk_FieldofThePK
            foreignKey = @ForeignKey(name = "task_user_userId_fk"))
    private UserModel userModel;

}
