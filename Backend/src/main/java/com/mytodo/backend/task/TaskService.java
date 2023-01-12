package com.mytodo.backend.task;

import com.mytodo.backend.user.UserModel;
import com.mytodo.backend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public boolean saveTask(TaskDTO taskDTO) {

        String currentUserName = getAuthenticatedUsername();
        TaskModel taskToSave = new TaskModel(taskDTO.getTitle(), taskDTO.getContent(), taskDTO.getDone());
        Optional<UserModel> userModel = Optional.ofNullable(userRepository.findByEmail(currentUserName));

        if (userModel.isPresent()) {
            taskToSave.setUserModel(userModel.get());
        } else {
            logger.info("Unable to find user: " + currentUserName);
            return false;
        }

        TaskModel savedTask = taskRepository.save(taskToSave);

        return isSaved(savedTask);
    }

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        logger.info("Current logged in user: " + currentUserName);
        return currentUserName;
    }

    private boolean isSaved(TaskModel savedTask) {
        if (savedTask.getId() > 0) {
            logger.info("Task with title: " + savedTask.getTitle() + " has been saved");
            return true;
        }
        return false;
    }

    public List<TaskDTO> getList() {
        String currentUserName = getAuthenticatedUsername();

        return   taskRepository.findAll().stream()
                .filter(task -> task.getUserModel().getEmail().equals(currentUserName))
                .map(task -> new TaskDTO(task.getTitle(), task.getText(), task.getDone()))
                .collect(Collectors.toList());
    }
}
