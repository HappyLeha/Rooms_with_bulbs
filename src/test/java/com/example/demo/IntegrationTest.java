package com.example.demo;
import com.example.demo.controller.RoomController;
import com.example.demo.dto.RoomDTO;
import com.example.demo.entity.Room;
import com.example.demo.enumeration.Country;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import java.util.Optional;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
public class IntegrationTest {

    @Mock
    private static RoomRepository roomRepository;

    private Optional<Room> testRoom;

    @Configuration
    static class ContextConfiguration {

        @Bean
        public RoomService roomService() {
            RoomService roomService = new RoomServiceImpl(roomRepository);
            return roomService;
        }

        @Bean
        public RoomController roomController() {
            RoomController roomController = new RoomController(roomService());
            System.out.println("hello");
            return roomController;
        }
    }

    @Autowired
    private RoomController roomController;

    @Before
    public void initRepository() {
       testRoom = Optional.of(new Room("TestRoom", Country.Belarus));
    }

    @Test
    public void getRoomOkTest() {
        when(roomRepository.findById(1)).thenReturn(testRoom);
        RoomDTO roomDTO = roomController.getRoom(1,"46.216.189.165");
        assertNotNull(roomDTO);
    }
}
