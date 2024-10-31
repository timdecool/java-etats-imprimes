package com.example.demo.controller.importer;

import com.example.demo.service.ImportCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("import/clients")
public class ImportClientController {
    @Autowired
    private ImportCsvService importClientsCSv;

    @PostMapping("csv")
    public String importClients(@RequestParam("file") MultipartFile file) throws Exception {
        importClientsCSv.importClientsCSv(file.getInputStream());
        return "redirect:/"; // Redirection vers la page d'accueil
    }

}
