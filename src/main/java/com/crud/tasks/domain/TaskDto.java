package com.crud.tasks.domain;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String content;
}
