package com.example.demo;
import com.example.demo.controller.RoomController;
import com.example.demo.dto.CreateRoomDTO;
import com.example.demo.service.RoomServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoomControllerTest {

    @Mock
    private RoomServiceImpl roomServiceImpl;

    private RoomController roomController;

    @Before
    public void init() {
        roomController = new RoomController(roomServiceImpl);
    }

    @Test
    public void createRoomOkTest() {
        CreateRoomDTO createRoomDTO = new CreateRoomDTO("TestRoom", "Belarus");

        roomController.createRoom(createRoomDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createRoomNotBlunkTest() {
        CreateRoomDTO createRoomDTO = new CreateRoomDTO("TestRoom", "Belaruss");

        roomController.createRoom(createRoomDTO);
    }
}
