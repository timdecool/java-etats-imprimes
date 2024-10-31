package com.example.demo.controller.export;

import com.example.demo.service.ExportPdfService;
import com.example.demo.service.ExportXlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller pour réaliser l'export des factures.
 */
@Controller
@RequestMapping("export/factures")
public class ExportFactureController {

    @Autowired
    private ExportPdfService exportPdfService;

    @Autowired
    private ExportXlsxService exportXlsxService;

    @GetMapping("{id}/pdf")
    public void exportPDF(@PathVariable("id") Long idFacture, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"export-facture-" + idFacture + ".pdf\"");
        ServletOutputStream outputStream = response.getOutputStream();
        exportPdfService.exportFacture(idFacture, outputStream);
    }

    @GetMapping("xlsx")
    public void exportXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");
        ServletOutputStream outputStream = response.getOutputStream();
        exportXlsxService.exportFactures(outputStream);
    }
}
