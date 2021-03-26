package com.example.demo.service;
import com.example.demo.entity.Room;
import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoom(int id, String ip);
    void createRoom(Room room);
    void changeLight(Room room);
}
