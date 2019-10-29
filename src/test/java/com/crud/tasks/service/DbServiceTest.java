package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void shouldReturnAllTasks() {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1L, "Task 1", "DescriptionCoverage1");
        Task task2 = new Task(2L, "Task 2", "DescriptionCoverage2");
        tasks.add(task1);
        tasks.add(task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        //When
        List<Task> retrievedTasks = dbService.getAllTasks();

        //Then
        assertEquals(2, retrievedTasks.size());
    }

    @Test
    public void shouldFindTaskById() {
        //Given
        Task task = new Task(null, "Task CoverageTest", "Description CoverageTest");
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        //When
        Task retrievedTask = dbService.findTaskById(1337L);

        //Then
        assertThat(retrievedTask, is(equalTo(task)));
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnNull() {
        //Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        //When
        Task task = dbService.findTaskById(1L);
        //Then
        assertTrue(task.getId().equals(null));
        assertTrue(task.getTitle().equals(null));
        assertTrue(task.getContent().equals(null));
    }

    @Test
    public void shouldCreateNewTask() {
        //Given
        Task taskToCreate = new Task(null, "New task CoverageTest", "New CoverageTest");
        Task createdTask = new Task(1337L, "New task CoverageTest", "New CoverageTest");
        when(taskRepository.save(taskToCreate)).thenReturn(createdTask);

        //When
        Task retrievedTask = dbService.saveTask(taskToCreate);

        //Then
        assertThat(retrievedTask, is(equalTo(createdTask)));
    }

    @Test
    public void shouldUpdateExistingTask() {
        //Given
        Task task = new Task(1L, "Task CoverageTest", "Description CoverageTest");
        when(taskRepository.save(task)).thenReturn(task);

        //When
        Task retrievedTask = dbService.saveTask(task);

        //Then
        assertThat(retrievedTask, is(notNullValue()));
        assertThat(retrievedTask.getId(), is(1L));
        assertThat(retrievedTask.getTitle(), is("Task CoverageTest"));
        assertThat(retrievedTask.getContent(), is("Description CoverageTest"));
    }

    @Test
    public void shouldDeleteTask() {
        //Given
        //When
        dbService.deleteTask(1L);
        //Then
        verify(taskRepository).deleteById(1L);
    }
}