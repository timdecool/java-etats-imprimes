package com.example.demo.service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportCsvService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ArticleService articleService;

    public void exportArticle(PrintWriter writer) throws IOException {
        writer.println("Nom;Prénom;Age");
        List<ClientDto> clients = clientService.findAll();
        for (ClientDto client : clients) {
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + client.getAge());
        }
    }

    public void exportClient(PrintWriter writer) throws IOException {
        writer.println("Libellé;Prix");
        List<ArticleDto> articles = articleService.findAll();
        for (ArticleDto article : articles) {
            writer.println("\"" + article.getLibelle() + "\"" + ";" + article.getPrix());
        }
    }
}
