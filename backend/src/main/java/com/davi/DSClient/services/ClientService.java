package com.davi.DSClient.services;

import com.davi.DSClient.DTO.ClientDTO;
import com.davi.DSClient.entities.Client;
import com.davi.DSClient.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
        Page<Client> obj = repository.findAll(pageRequest);
        return obj.map(ClientDTO::new);
    }
}
