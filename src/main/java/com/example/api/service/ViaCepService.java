package com.example.api.service;

import com.example.api.controller.dto.AddressDto;

public interface ViaCepService {

    AddressDto getAddressByCep(String cep);
}
