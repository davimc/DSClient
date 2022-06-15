package com.davi.DSClient.services;

import com.davi.DSClient.DTO.ClientDTO;
import com.davi.DSClient.entities.Client;
import com.davi.DSClient.repositories.ClientRepository;
import com.davi.DSClient.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
        Page<Client> obj = repository.findAll(pageRequest);
        return obj.map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Optional<Client> obj = repository.findById(id);
        return new ClientDTO(obj.orElseThrow(() ->
            new ObjectNotFoundException(id, Client.class)
        ));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client obj = new Client();
        copyDtoToEntity(dto, obj);
        obj = repository.save(obj);

        return new ClientDTO(obj);
    }

    private void copyDtoToEntity(ClientDTO dto, Client obj) {
        obj.setBirthDate(dto.getBirthDate());
        obj.setChildren(dto.getChildren());
        obj.setCpf(dto.getCpf());
        obj.setIncome(dto.getIncome());
        obj.setName(dto.getName());
    }
}
