package com.mytodo.backend.task;

import com.mytodo.backend.user.UserModel;
import com.mytodo.backend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TaskRestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private final TaskService taskService;

    @Autowired
    public TaskRestController(UserRepository userRepository, TaskRepository taskRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @PostMapping("/saveTask")
    public ResponseEntity<Void> saveTask(@RequestBody TaskDTO taskDTO) {

        boolean isSaved = taskService.saveTask(taskDTO);
        if (isSaved) return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/listTasks")
    public ResponseEntity<List<TaskDTO>> getList() {
        List<TaskDTO> list = taskService.getList();
        return ResponseEntity.ok(list);
    }


}
