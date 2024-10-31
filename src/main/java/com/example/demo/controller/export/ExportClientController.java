package com.example.demo.controller.export;

import com.example.demo.service.ExportCsvService;
import com.example.demo.service.ExportXlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Controller pour réaliser l'export des clients.
 */
@Controller
@RequestMapping("export/clients")
public class ExportClientController {

    @Autowired
    private ExportCsvService exportCsvService;
    @Autowired
    private ExportXlsxService exportXlsxService;

    /**
     * Export des articles au format CSV.
     */
    @GetMapping("csv")
    public void exportCSV(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();
        exportCsvService.exportArticle(writer);
    }

    @GetMapping("xlsx")
    public void exportXLSX(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
        ServletOutputStream outputStream = response.getOutputStream();
        exportXlsxService.exportClient(outputStream);
    }
}
