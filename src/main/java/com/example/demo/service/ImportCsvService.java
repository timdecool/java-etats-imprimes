package com.example.demo.service;

import com.example.demo.entity.ClientEntity;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

@Service
public class ImportCsvService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void importClientsCSv(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        bufferedReader.lines()
                .skip(1) // skip header
                .forEach(line -> {
                    String[] tab = line.split(";");
                    String nom = tab[0];
                    String prenom = tab[1];
                    String dateNaissanceAsString = tab[2];
                    ClientEntity client = new ClientEntity();
                    client.setNom(nom);
                    client.setPrenom(prenom);

                    String[] dateNaissanceTab = dateNaissanceAsString.split("/");
                    LocalDate dateNaissance = LocalDate.of(
                            parseInt(dateNaissanceTab[2]),
                            parseInt(dateNaissanceTab[1]),
                            parseInt(dateNaissanceTab[0]));
                    client.setDateNaissance(dateNaissance);
                    clientRepository.save(client);
                });
        reader.close();
    }
}