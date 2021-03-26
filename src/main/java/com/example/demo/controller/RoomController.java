package com.example.demo.controller;

import com.example.demo.dto.CreateRoomDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.entity.Room;
import com.example.demo.enumeration.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.RoomService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDTO> roomDTOS = new ArrayList<>();

        for (Room room: rooms) {
            roomDTOS.add(new RoomDTO(room.getId(), room.getTitle(),
                         room.getCountry().name(), room.isLight()));
        }
        return roomDTOS;
    }

    @PatchMapping("light/{id}")
    public void changeLight(@PathVariable("id") int id, @RequestParam String ip) {
        Room room = roomService.getRoom(id, ip);

        roomService.changeLight(room);
    }

    @GetMapping("{id}")
    public RoomDTO getRoom(@PathVariable("id") int id, @RequestParam String ip) {
        Room room = roomService.getRoom(id, ip);

        return new RoomDTO(room.getId(), room.getTitle(),
                           room.getCountry().name(), room.isLight());
    }

    @GetMapping("/country")
    public List<String> getAllCountries() {
        List<String> countryList = new ArrayList<>();

        for (Country country: Country.values()) {
            countryList.add(country.name());
        }
        return countryList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@RequestBody @Valid CreateRoomDTO createRoomDTO) {
        roomService.createRoom(new Room(createRoomDTO.getTitle(),
                    Country.valueOf(createRoomDTO.getCountry())));

    }
}
