package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.CreatedTrelloCard;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init(){
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("testUserName");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoardDtos = new TrelloBoardDto[1];
        trelloBoardDtos[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        URI uri = new URI("http://test.com/members/testUserName/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> fetchedTrelloBoardDtos = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoardDtos.size());
        assertEquals("test_id", fetchedTrelloBoardDtos.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoardDtos.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoardDtos.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        List<TrelloAttachmentDto> trelloAttachmentDtos = new ArrayList<>();
        List<TrelloBadgeDto> trelloBadgeDtos = new ArrayList<>();
        TrelloDto trelloDto = new TrelloDto(0,0);
        trelloAttachmentDtos.add(new TrelloAttachmentDto(trelloDto));
        trelloBadgeDtos.add(new TrelloBadgeDto(0, trelloAttachmentDtos));
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id",
                trelloBadgeDtos
        );

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri,null,CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoardDtos = new TrelloBoardDto[1];
        trelloBoardDtos[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        URI uri = new URI("http://test.com/members/testUserName/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> fetchedTrelloBoardDtos = trelloClient.getTrelloBoards();

        //Then
        assertNotNull(fetchedTrelloBoardDtos);
        assertEquals(0, fetchedTrelloBoardDtos.size());
    }

}