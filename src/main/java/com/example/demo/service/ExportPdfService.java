package com.example.demo.service;

import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class ExportPdfService {

    @Autowired
    private FactureService factureService;

    public void exportFacture(Long idFacture, OutputStream outputStream) throws Exception {
        FactureDto facture = factureService.findById(idFacture);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();

        Paragraph paragrapheHeader1 = new Paragraph();
        paragrapheHeader1.add("Facture " + facture.getId() + " de M. " + facture.getClientNom() + " " + facture.getClientPrenom());
        document.add(paragrapheHeader1);

        PdfPTable table = new PdfPTable(3);
        table.addCell("Article");
        table.addCell("Quantité");
        table.addCell("Prix unitaire");
        for (LigneFactureDto ligneFacture : facture.getLigneFactures()) {
            table.addCell(ligneFacture.getArticle());
            table.addCell("" + ligneFacture.getQuantite());
            table.addCell("" + ligneFacture.getPrixUnitaire());
        }
        document.add(table);

        document.close();
    }
}
