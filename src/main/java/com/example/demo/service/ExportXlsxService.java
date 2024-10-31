package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportXlsxService {

    public ExportXlsxService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    private ClientService clientService;

    @Autowired
    private FactureService factureService;

    public void exportClient(OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        Row rowHeader = sheet.createRow(0);

        CellStyle cellStyleHeader = workbook.createCellStyle();
        Font fontHeader = workbook.createFont();
        fontHeader.setFontName("Helvetica");
        fontHeader.setBold(true);
        fontHeader.setColor(IndexedColors.PINK.index);
        cellStyleHeader.setFont(fontHeader);
        addBorder(cellStyleHeader);

        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        cellStyle.setFont(font);
        addBorder(cellStyle);


        createCell(rowHeader, 0, cellStyleHeader).setCellValue("Nom");
        createCell(rowHeader, 1, cellStyleHeader).setCellValue("Prénom");
        createCell(rowHeader, 2, cellStyleHeader).setCellValue("Age");

        int iRow = 1;
        for (ClientDto clientDto : clientService.findAll()) {
            Row row = sheet.createRow(iRow++);
            createCell(row, 0, cellStyle).setCellValue(clientDto.getNom());
            createCell(row, 1, cellStyle).setCellValue(clientDto.getPrenom());
            createCell(row, 2, cellStyle).setCellValue(clientDto.getAge() + " ans");
        }

        workbook.write(outputStream);
        workbook.close();
    }

    public void exportFactures(OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        CellStyle defaultStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        defaultStyle.setFont(font);

        CellStyle boldStyle = workbook.createCellStyle();
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        fontBold.setFontName("Calibri");
        fontBold.setFontHeightInPoints((short) 11);
        boldStyle.setFont(fontBold);

        CellStyle boldRightStyle = workbook.createCellStyle();
        boldRightStyle.setFont(fontBold);
        boldRightStyle.setAlignment(HorizontalAlignment.RIGHT);
        boldRightStyle.setIndention((short)1);

        for (ClientDto clientDto : clientService.findAll()) {
            List<FactureDto> factures = factureService.findByClientId(clientDto.getId());

            Sheet sheet = workbook.createSheet(clientDto.getPrenom() + " " + clientDto.getNom());
            sheet.setColumnWidth(0, 5000);

            Row rowNom = sheet.createRow(0);
            createCell(rowNom, 0, defaultStyle).setCellValue("Nom :");
            createCell(rowNom, 1, defaultStyle).setCellValue(clientDto.getNom());

            Row rowPrenom = sheet.createRow(1);
            createCell(rowPrenom, 0, defaultStyle).setCellValue("Prénom :");
            createCell(rowPrenom, 1, defaultStyle).setCellValue(clientDto.getPrenom());

            Row rowNaissance = sheet.createRow(2);
            createCell(rowNaissance, 0, defaultStyle).setCellValue("Année de naissance :");
            createCell(rowNaissance, 1, defaultStyle).setCellValue(LocalDate.now().getYear() - clientDto.getAge());

            Row rowFacture = sheet.createRow(3);
            createCell(rowFacture, 0, boldStyle).setCellValue(factures.size() + " facture(s) :");
            int i = 0;
            for(FactureDto factureDto : factures) {
                createCell(rowFacture, ++i, defaultStyle).setCellValue(factureDto.getId());
                Sheet sheetFacture = workbook.createSheet("Facture n°" + factureDto.getId());
                sheetFacture.setColumnWidth(0, 8000);
                sheetFacture.setColumnWidth(1, 3000);
                sheetFacture.setColumnWidth(2, 3000);

                // Créer En-tête
                Row rowHeader = sheetFacture.createRow(0);
                createCell(rowHeader, 0, boldStyle).setCellValue("Désignation");
                createCell(rowHeader, 1, boldStyle).setCellValue("Quantité");
                createCell(rowHeader, 2, boldStyle).setCellValue("Prix unitaire");

                int j = 1;
                for(LigneFactureDto ligneFactureDto:factureDto.getLigneFactures()) {
                    Row rowLigneFacture = sheetFacture.createRow(j++);
                    createCell(rowLigneFacture, 0, defaultStyle).setCellValue(ligneFactureDto.getArticle());
                    createCell(rowLigneFacture, 1, defaultStyle).setCellValue(ligneFactureDto.getQuantite());
                    createCell(rowLigneFacture, 2, defaultStyle).setCellValue(ligneFactureDto.getPrixUnitaire());
                }

                sheetFacture.addMergedRegion(new CellRangeAddress(j, j, 0, 1));
                Row rowTotalFacture = sheetFacture.createRow(j);
                createCell(rowTotalFacture, 0, boldRightStyle).setCellValue("Total");
                createCell(rowTotalFacture, 2, defaultStyle).setCellValue(factureDto.getPrixTotal());
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }

    private static void addBorder(CellStyle cellStyleHeader) {
        cellStyleHeader.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderTop(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderLeft(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderRight(BorderStyle.MEDIUM);
        cellStyleHeader.setBottomBorderColor(IndexedColors.BLUE.index);
        cellStyleHeader.setTopBorderColor(IndexedColors.BLUE.index);
        cellStyleHeader.setLeftBorderColor(IndexedColors.BLUE.index);
        cellStyleHeader.setRightBorderColor(IndexedColors.BLUE.index);
    }

    private static Cell createCell(Row rowHeader, int i, CellStyle cellStyle) {
        Cell cell = rowHeader.createCell(i);
        cell.setCellStyle(cellStyle);
        return cell;
    }

}
