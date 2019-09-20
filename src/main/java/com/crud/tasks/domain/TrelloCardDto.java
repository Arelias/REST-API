package com.crud.tasks.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrelloCardDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pos")
    private String pos;

    @JsonProperty("listId")
    private String listId;

    @JsonProperty("badges")
    private List<TrelloBadgeDto> badges;

}
