package com.example.demo.service;

import com.example.demo.dto.ClientDto;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ExportXlsxServiceTest {

    @Test
    public void exportClient() throws Exception {
        ClientService mockClientService = Mockito.mock(ClientService.class);
        ClientDto clientExemple = new ClientDto(1L, "John", "Doe", 20);
        List<ClientDto> clientsExemples = new ArrayList<>();
        clientsExemples.add(clientExemple);
        when(mockClientService.findAll()).thenReturn(clientsExemples);
        ExportXlsxService service = new ExportXlsxService(mockClientService);

        FileOutputStream outputStream = new FileOutputStream("c:/temp/export.xlsx");
        service.exportClient(outputStream);
        outputStream.close();
    }
}