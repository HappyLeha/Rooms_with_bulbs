package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CreateRoomDTO {

    @NotBlank(message = "Title shouldn't be empty!")
    private String title;

    @NotBlank(message = "Title shouldn't be empty!")
    private String country;
}
