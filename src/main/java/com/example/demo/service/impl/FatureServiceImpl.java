package com.example.demo.service.impl;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.entity.FactureEntity;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.FactureService;
import com.example.demo.service.mapper.FactureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service contenant les actions métiers liées aux factures.
 */
@Service
@Transactional
public class FatureServiceImpl implements FactureService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private FactureMapper factureMapper;


    @Override
    public List<FactureDto> findAll() {
        return factureRepository.findAll().stream().map(article -> factureMapper.transformerEnDto(article)).collect(toList());
    }

    @Override
    public FactureDto findById(Long id) {
        return factureRepository.findById(id).map(facture -> factureMapper.transformerEnDto(facture)).orElse(null);
    }

    @Override
    public List<FactureDto> findByClientId(Long id) {
        return factureRepository.findByClientId(id).stream().map(facture -> factureMapper.transformerEnDto(facture)).collect(toList());
    }

}
