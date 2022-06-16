package com.davi.DSClient.services;

import com.davi.DSClient.DTO.ClientDTO;
import com.davi.DSClient.entities.Client;
import com.davi.DSClient.repositories.ClientRepository;
import com.davi.DSClient.services.exceptions.DatabaseException;
import com.davi.DSClient.services.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private Client findById(Long id) {
        Optional<Client> obj = repository.findById(id);
        return obj.orElseThrow(() ->
                new ObjectNotFoundException(id, Client.class)
        );
    }
    public ClientDTO findByIdDTO(Long id){
        return new ClientDTO(findById(id));
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client obj = new Client();
        copyDtoToEntity(dto, obj);
        obj = repository.save(obj);

        return new ClientDTO(obj);
    }
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client obj = this.repository.getReferenceById(id);
            copyDtoToEntity(dto, obj);
            obj = repository.save(obj);
            return new ClientDTO(obj);
        }catch (EntityNotFoundException e) {
            throw new ObjectNotFoundException(id, Client.class);
        }
    }
    @Transactional
    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }catch (EntityNotFoundException e) {
            throw new ObjectNotFoundException(id, Client.class);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
    private void copyDtoToEntity(ClientDTO dto, Client obj) {
        obj.setBirthDate(dto.getBirthDate());
        obj.setChildren(dto.getChildren());
        obj.setCpf(dto.getCpf());
        obj.setIncome(dto.getIncome());
        obj.setName(dto.getName());
    }
}
