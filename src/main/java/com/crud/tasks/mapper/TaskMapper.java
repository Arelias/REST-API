package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public Task mapToTask(final TaskDto taskDto){
        return new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getContent());
    }

    public TaskDto mapToTaskDto(final Task task){
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getContent());
    }
    public TaskDto mapToTaskDto(final Optional<Task> task){

        if(task.isPresent()){
            return mapToTaskDto(task.get());
        } else {
            //Or throw exception?
            return new TaskDto(-1L, "Not found", "Not founnd");
        }

    }

    public List<TaskDto> mapToTaskDtoList(final List<Task> taskList){
        return taskList.stream()
                .map(t -> new TaskDto(t.getId(),t.getTitle(),t.getContent()))
                .collect(Collectors.toList());
    }

}
