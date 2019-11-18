package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchEmptyTasks() throws Exception {
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        when(taskController.getTasks()).thenReturn(taskDtos);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) //or isOk() method
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnsTasks() throws Exception {
        //Given
        TaskDto task = new TaskDto(1L, "Task 1", "Description 1");
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskController.getTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Task 1")))
                .andExpect(jsonPath("$[0].description", is("Description 1")));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //Given
        TaskDto task = new TaskDto(1L, "Task 1", "Description 1");
        when(taskController.getTasksById(1L)).thenReturn(task);
        //When & Then
        mockMvc.perform(get("/v1/task/getTaskById?id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task 1")))
                .andExpect(jsonPath("$.content", is("Description 1")));
    }

    @Test
    public void deleteTask() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?id=1337").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(taskController).deleteTask(1337L);
    }

    @Test
    public void updateTask() throws Exception {
        //Given
        TaskDto createdTask = new TaskDto(123L, "Task 234", "Description 234");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createdTask);

        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(createdTask);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.title", is("Task 234")))
                .andExpect(jsonPath("$.content", is("Description 234")));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto initialTask = new TaskDto(null, "Task 123", "Description 123");
        TaskDto createdTask = new TaskDto(123L, "Task 123", "Description 123");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(initialTask);

        when(taskController.createTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(createdTask);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)))
                .andExpect(jsonPath("$.title", is("Task 123")))
                .andExpect(jsonPath("$.content", is("Description 123")));
    }
}