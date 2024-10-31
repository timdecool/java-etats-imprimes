package com.example.demo.service.mapper;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.ClientEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClientMapper {

    public ClientDto transformerEnDto(ClientEntity entity) {
        LocalDate dateNaissance = entity.getDateNaissance();
        LocalDate dateDuJour = LocalDate.now();
        int age = dateDuJour.getYear() - dateNaissance.getYear();
        return new ClientDto(entity.getId(), entity.getNom(), entity.getPrenom(), age);
    }

}
