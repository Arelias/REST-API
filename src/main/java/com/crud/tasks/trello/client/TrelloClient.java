package com.crud.tasks.trello.client;

import com.crud.tasks.controller.BoardNotFoundException;
import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;

    public List<TrelloBoardDto> getTrelloBoards() throws BoardNotFoundException {

        Optional<TrelloBoardDto[]> output;
        URI url = urlBuilder();

        //We are just mapping the response to array of trelloDto class instead of just one
        output = Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class));

        if (output.isPresent()) {
            return Arrays.asList(output.get());
        } else {
            throw new BoardNotFoundException();
        }
    }

    private URI urlBuilder() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id").build().encode().toUri();

        return url;
    }
}