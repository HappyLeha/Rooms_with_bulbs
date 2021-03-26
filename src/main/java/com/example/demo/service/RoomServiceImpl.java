package com.example.demo.service;
import com.example.demo.dto.GeopluginDTO;
import com.example.demo.entity.Room;
import com.example.demo.enumeration.Country;
import com.example.demo.exception.NotEnoughRightException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(int id, String ip) {
        Room room;
        Country country;

        if (!roomRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Room with id "+id+" doesn't exist");
        }
        room = roomRepository.findById(id).get();
        country = getCountryByIp(ip);
        if (room.getCountry().equals(country)) {
            return room;
        }
        else {
            throw new NotEnoughRightException("You can't enter in this room");
        }
    }

    @Override
    public void createRoom(Room room) {
        roomRepository.save(room);
        log.info("Room "+room+" was successfully created.");
    }

    @Override
    public void changeLight(Room room) {
        room.setLight(!room.isLight());
        roomRepository.save(room);
        log.info("Light in Room "+room+" was successfully changed");
    }

    private Country getCountryByIp(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        GeopluginDTO geopluginDTO;
        Country country;

        geopluginDTO = restTemplate.getForObject("http://www.geoplugin.net/json.gp?ip="
                                                 +ip, GeopluginDTO.class);
        try {
            country = Country.valueOf(geopluginDTO.getGeoplugin_countryName());
        }
        catch (IllegalArgumentException e) {
            country = Country.Other;
        }
        return country;
    }
}
