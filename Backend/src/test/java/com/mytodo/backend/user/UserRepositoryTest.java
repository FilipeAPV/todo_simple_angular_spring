package com.mytodo.backend.user;

import com.mytodo.backend.role.RoleModel;
import com.mytodo.backend.role.RoleRepository;
import com.mytodo.backend.task.TaskModel;
import com.mytodo.backend.task.TaskRepository;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.function.Predicate;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Added because @Order was not being respected
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @Order(1)
    void save() {
        RoleModel userRole = roleRepository.findAll().stream()
                .filter(roleModel -> "user".equals(roleModel.getName()))
                .findFirst()
                .orElse(null);

        UserModel user1 = new UserModel("Lee","Tom","lt@gmail.com","1");
        user1.getRoleModel().add(userRole);

        UserModel savedUser = userRepository.save(user1);

        Assertions.assertTrue(savedUser.getRoleModel().size() == 1);
        Assertions.assertTrue(savedUser.getUserId() > 0);
    }

    @Test
    @Order(2)
    void addNewUserWithAllRoles() {

        UserModel user1 = new UserModel("Tao","Lim","tl@gmail.com","1");
        user1.setRoleModel(roleRepository.findAll());

        UserModel savedUser = userRepository.save(user1);

        Assertions.assertTrue(savedUser.getRoleModel().size() == 2);
        Assertions.assertTrue(savedUser.getUserId() > 0);

    }

    @Test
    @Order(3)
    void deleteFirstFetchedUser() {

        UserModel user1 = userRepository.findAll().stream().findFirst().get();
        Predicate<TaskModel> tasksAssignedToUser1 = (task) -> task.getUserModel().getUserId() == user1.getUserId();

        List<RoleModel> listOfAllRolesBeforeDeletion = roleRepository.findAll();
        long userTaskCountBeforeDeletion = taskRepository.findAll().stream()
                .filter(tasksAssignedToUser1)
                .count();

        userRepository.delete(user1);

        List<RoleModel> listOfAllRolesAfterDeletion = roleRepository.findAll();
        long userTaskCountAfterDeletion = taskRepository.findAll().stream()
                .filter(tasksAssignedToUser1)
                .count();

        Assertions.assertEquals(listOfAllRolesBeforeDeletion.size(), listOfAllRolesAfterDeletion.size());

        if (userTaskCountBeforeDeletion > 0) {
            Assertions.assertEquals(0, userTaskCountAfterDeletion);
        } else {
            System.out.println(user1.getEmail() + "had no tasks before deletion");
        }

        Assertions.assertFalse(userRepository.existsById(user1.getUserId()));

    }
}
