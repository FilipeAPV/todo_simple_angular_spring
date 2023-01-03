package com.mytodo.backend.task;

import com.mytodo.backend.role.RoleRepository;
import com.mytodo.backend.user.UserModel;
import com.mytodo.backend.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    UserModel savedUser;
    TaskModel task1;

    @BeforeEach
    void setup() {
        UserModel user1 = new UserModel("Laura","McCan","lm@gmail.com","1");
        user1.setRoleModel(roleRepository.findAll());
        savedUser = userRepository.save(user1);

        task1 = new TaskModel("Smile Often","Smile first thing in the morning");
        task1.setUserModel(savedUser);
    }

    @Test
    void save() {
        TaskModel savedTask = taskRepository.save(task1);
        Assertions.assertTrue(savedTask.getId() > 0);
    }

    @Test
    void deleteTask() {

        TaskModel savedTask = taskRepository.save(task1);
        long totalTasksBeforeDeletion = taskRepository.count();

        taskRepository.delete(savedTask);
        long totalTasksAfterDeletion = taskRepository.count();

        Assertions.assertEquals(totalTasksAfterDeletion, totalTasksBeforeDeletion-1);
    }

}
