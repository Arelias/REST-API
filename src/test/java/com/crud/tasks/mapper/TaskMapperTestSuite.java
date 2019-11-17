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
        assertEquals(1337L, (long)task.getId());
        assertEquals("Coverage task", task.getTitle());
        assertEquals("Coverage coverage", task.getContent());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task task = new Task(1337L, "Coverage task", "Coverage coverage");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(1337L, (long)taskDto.getId());
        assertEquals("Coverage task", taskDto.getTitle());
        assertEquals("Coverage coverage", taskDto.getContent());
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
        assertEquals(1337L, (long)taskDtos.get(0).getId());
        assertEquals(1338L, (long)taskDtos.get(1).getId());
        assertEquals(1339L, (long)taskDtos.get(2).getId());
        assertEquals(1340L, (long)taskDtos.get(3).getId());
        assertEquals("Coverage task0", taskDtos.get(0).getTitle());
        assertEquals("Coverage task1", taskDtos.get(1).getTitle());
        assertEquals("Coverage task2", taskDtos.get(2).getTitle());
        assertEquals("Coverage task3", taskDtos.get(3).getTitle());
        assertEquals(4, taskDtos.size());
    }
}