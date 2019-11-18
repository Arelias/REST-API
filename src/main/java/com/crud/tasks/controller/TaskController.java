package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTaskById")
    public TaskDto getTasksById(@RequestParam(name = "id") Long taskId){
        return taskMapper.mapToTaskDto(service.findTaskById(taskId));
    }

    //Request param zmienia wartosc wymagana w linku, teraz jest ?id bez niego byloby ?taskId
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(@RequestParam(name = "id") Long taskId){
        service.deleteTask(taskId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask", consumes = APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody TaskDto taskDto){
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }
}
//import com.crud.tasks.domain.Task;
//import com.crud.tasks.dto.TaskDto;
//import com.crud.tasks.service.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/v1/tasks")
//@CrossOrigin(origins = "*")
//public class TaskController {
//
//    final private TaskService service;
//
//    @Autowired
//    public TaskController(final TaskService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<TaskDto> getTasks() {
//        return service.getAllTasks().stream()
//                .map(TaskDto::new)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/{id}")
//    public TaskDto getTask(@PathVariable long id) {
//        return new TaskDto(service.getTask(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteTask(@PathVariable long id) {
//        service.deleteTask(id);
//    }
//
//    @PutMapping
//    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
//        return new TaskDto(service.saveTask(new Task(taskDto)));
//    }
//
//    @PostMapping
//    public TaskDto createTask(@RequestBody TaskDto taskDto) {
//        return new TaskDto(service.saveTask(new Task(taskDto)));
//    }
//
//}