package ru.serega.musicreviews.dtos;

import lombok.Data;
import ru.serega.musicreviews.util.Role;

@Data
public class RegistrationDTO {
    private String username;
    private String password;
}
