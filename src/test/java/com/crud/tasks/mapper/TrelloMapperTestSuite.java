package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToBoards() {
        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1","TestingList1",false));
        trelloListsDto.add(new TrelloListDto("2","TestingList2",true));
        List<TrelloListDto> trelloListsDto2 = new ArrayList<>();
        trelloListsDto2.add(new TrelloListDto("3","TestingList3",false));
        trelloListsDto2.add(new TrelloListDto("4","TestingList4",true));
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1","CorrectBoard", trelloListsDto);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2","TestingBoard", trelloListsDto2);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        trelloBoardDtos.add(trelloBoardDto2);
        //When
        List<TrelloBoard> trelloBoard = trelloMapper.mapToBoards(trelloBoardDtos);
        TrelloBoard trelloBoard1 = trelloBoard.get(0);
        List<TrelloList> trelloList1 = trelloBoard1.getLists();
        TrelloBoard trelloBoard2 = trelloBoard.get(1);
        List<TrelloList> trelloList2 = trelloBoard2.getLists();
        //Then
        assertEquals("1", trelloBoard1.getId());
        assertEquals("CorrectBoard", trelloBoard1.getName());
        assertEquals("1",trelloList1.get(0).getId());
        assertEquals("2",trelloList1.get(1).getId());
        assertEquals("TestingList1",trelloList1.get(0).getName());
        assertEquals("TestingList2",trelloList1.get(1).getName());
        assertEquals("2", trelloBoard2.getId());
        assertEquals("TestingBoard", trelloBoard2.getName());
        assertEquals("3",trelloList2.get(0).getId());
        assertEquals("4",trelloList2.get(1).getId());
        assertEquals("TestingList3",trelloList2.get(0).getName());
        assertEquals("TestingList4",trelloList2.get(1).getName());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        List<TrelloList> trelloLists1 = new ArrayList<>();
        trelloLists1.add(new TrelloList("1","TestingList1",false));
        trelloLists1.add(new TrelloList("2","TestingList2",true));
        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloLists2.add(new TrelloList("3","TestingList3",false));
        trelloLists2.add(new TrelloList("4","TestingList4",true));
        TrelloBoard trelloBoard1 = new TrelloBoard("1","CorrectBoard", trelloLists1);
        TrelloBoard trelloBoard2 = new TrelloBoard("2","TestingBoard", trelloLists2);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        TrelloBoardDto trelloBoardDto1 = trelloBoardsDto.get(0);
        List<TrelloListDto> trelloListDto1 = trelloBoardDto1.getLists();
        TrelloBoardDto trelloBoardDto2 = trelloBoardsDto.get(1);
        List<TrelloListDto> trelloListDto2 = trelloBoardDto2.getLists();
        //Then
        assertEquals("1", trelloBoard1.getId());
        assertEquals("CorrectBoard", trelloBoard1.getName());
        assertEquals("1",trelloListDto1.get(0).getId());
        assertEquals("2",trelloListDto1.get(1).getId());
        assertEquals("TestingList1",trelloListDto1.get(0).getName());
        assertEquals("TestingList2",trelloListDto1.get(1).getName());
        assertEquals("2", trelloBoard2.getId());
        assertEquals("TestingBoard", trelloBoard2.getName());
        assertEquals("3",trelloListDto2.get(0).getId());
        assertEquals("4",trelloListDto2.get(1).getId());
        assertEquals("TestingList3",trelloListDto2.get(0).getName());
        assertEquals("TestingList4",trelloListDto2.get(1).getName());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Testing Card1", "Testing description",
                "1337", "13");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Testing Card1", trelloCardDto.getName());
        assertEquals("Testing description", trelloCardDto.getDescription());
        assertEquals("1337", trelloCardDto.getPos());
        assertEquals("13", trelloCardDto.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Testing Card1", "Testing description",
                "1337", "13");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Testing Card1", trelloCard.getName());
        assertEquals("Testing description", trelloCard.getDescription());
        assertEquals("1337", trelloCard.getPos());
        assertEquals("13", trelloCard.getListId());
    }
}