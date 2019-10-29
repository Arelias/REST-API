package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.mapper.CreatedTrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1,trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void createTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TestCardCoverage","Coverage testing","1337","1337");
        TrelloCard trelloCard = new TrelloCard("TestCardCoverage","Coverage testing","1337","1337");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1337","TestCardCoverage", "www.short.url");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto createdTrelloCardDto2 = trelloService.createTrelloCard(trelloCardDto);

        //then
        verify(simpleEmailService, times(1)).send(any());
        assertEquals("1337", createdTrelloCardDto2.getId());
        assertEquals("TestCardCoverage", createdTrelloCardDto2.getName());
        assertEquals("www.short.url", createdTrelloCardDto2.getShortUrl());
        assertTrue(createdTrelloCardDto.equals(createdTrelloCardDto2));
    }
}