package com.example.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.api.controller.dto.AddressDto;
import com.example.api.service.ViaCepService;

@Service
public class ViaCepServiceImpl implements ViaCepService {

    private final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    private final RestTemplate restTemplate;

    public ViaCepServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AddressDto getAddressByCep(String cep) {
        String apiUrl = String.format("%s%s/json", VIA_CEP_URL, cep);
        return restTemplate.getForObject(apiUrl, AddressDto.class);
    }
}
