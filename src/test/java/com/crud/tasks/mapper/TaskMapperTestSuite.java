package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1337L, "Coverage task", "Coverage coverage");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(java.util.Optional.ofNullable(1337L), java.util.Optional.ofNullable(task.getId()));
        assertEquals(java.util.Optional.ofNullable("Coverage task"), java.util.Optional.ofNullable(task.getTitle()));
        assertEquals(java.util.Optional.ofNullable("Coverage coverage"), java.util.Optional.ofNullable(task.getContent()));
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(1337L, "Coverage task", "Coverage coverage");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(java.util.Optional.ofNullable(1337L), java.util.Optional.ofNullable(taskDto.getId()));
        assertEquals(java.util.Optional.ofNullable("Coverage task"), java.util.Optional.ofNullable(taskDto.getTitle()));
        assertEquals(java.util.Optional.ofNullable("Coverage coverage"), java.util.Optional.ofNullable(taskDto.getContent()));
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1337L, "Coverage task0", "Coverage coverage"));
        tasks.add(new Task(1338L, "Coverage task1", "Coverage coverage"));
        tasks.add(new Task(1339L, "Coverage task2", "Coverage coverage"));
        tasks.add(new Task(1340L, "Coverage task3", "Coverage coverage"));
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertEquals(java.util.Optional.ofNullable(1337L), java.util.Optional.ofNullable(taskDtos.get(0).getId()));
        assertEquals(java.util.Optional.ofNullable(1338L), java.util.Optional.ofNullable(taskDtos.get(1).getId()));
        assertEquals(java.util.Optional.ofNullable(1339L), java.util.Optional.ofNullable(taskDtos.get(2).getId()));
        assertEquals(java.util.Optional.ofNullable(1340L), java.util.Optional.ofNullable(taskDtos.get(3).getId()));
        assertEquals("Coverage task0", taskDtos.get(0).getTitle());
        assertEquals("Coverage task1", taskDtos.get(1).getTitle());
        assertEquals("Coverage task2", taskDtos.get(2).getTitle());
        assertEquals("Coverage task3", taskDtos.get(3).getTitle());
        assertEquals(4, taskDtos.size());
    }
}