package com.example.demo;
import com.example.demo.entity.Room;
import com.example.demo.enumeration.Country;
import com.example.demo.exception.NotEnoughRightException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    private RoomServiceImpl roomService;

    private Optional<Room> testRoom;

    @Before
    public void init() {
        roomService = new RoomServiceImpl(roomRepository);
        testRoom = Optional.of(new Room("TestRoom", Country.Belarus));
    }

    @Test
    public void getRoomOkTest() {
        when(roomRepository.findById(1)).thenReturn(testRoom);
        Room room = roomService.getRoom(1,"46.216.189.165");
        assertNotNull(room);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getRoomResourceNotFoundTest() {
        when(roomRepository.findById(1)).thenReturn(testRoom);
        roomService.getRoom(0,"46.216.189.165");
    }

    @Test(expected = NotEnoughRightException.class)
    public void getRoomNotEnoughRightTest() {
        when(roomRepository.findById(1)).thenReturn(testRoom);
        roomService.getRoom(1,"89.163.220.14");
    }
}
