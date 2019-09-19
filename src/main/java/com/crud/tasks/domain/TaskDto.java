package com.crud.tasks.domain;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    //Id w js ma sie zgadzac z tym id (nazwy)
    private Long id;
    private String title;
    private String content;
}
