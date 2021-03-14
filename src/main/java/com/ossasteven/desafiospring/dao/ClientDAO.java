package com.ossasteven.desafiospring.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.model.ClientDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository("ClientDAO")
public class ClientDAO implements IClientDAO {

    private List<ClientDTO> clients;

    private Long id;

    @PostConstruct
    public void initClients() {

        loadDatabase();
        if (clients.isEmpty()) {
            this.id = 0L;
        } else {
            this.id = clients.get(clients.size() - 1).getClientId();
        }
    }

    @Override
    public Optional<ClientDTO> findOne(Long id) throws NotFoundException {
        if (clients != null) {
            Optional<ClientDTO> item = clients.stream()
                    .filter(ClientDTO -> ClientDTO.getClientId().equals(id))
                    .findFirst();
            if (item.isPresent())
                return item;
        }

        throw new NotFoundException("Client with id " + id + " not found");
    }

    @Override
    public Collection<ClientDTO> getAll() {
        return this.clients;
    }


    @Override
    public Long save(ClientDTO clientDTO) throws AlreadyExistsException {

        validateUser(clientDTO);

        ObjectMapper mapper = new ObjectMapper();

        File file = null;

        try {
            file = ResourceUtils.getFile("src/main/resources/clients.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            id++;
            clientDTO.setClientId(id);
            clients.add(clientDTO);
            mapper.writeValue(file, clients);
            return 1L;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    private void loadDatabase() {
        List<ClientDTO> data = new ArrayList<>();

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:clients.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<ClientDTO>> typeRef = new TypeReference<>() {
        };

        try {
            data = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.clients = data;
    }

    private void validateUser(ClientDTO client) throws AlreadyExistsException {

        for (ClientDTO clientdto :
                clients) {
            if (clientdto.getEmail().equalsIgnoreCase(client.getEmail()))
                throw new AlreadyExistsException("client with the email: " + client.getEmail() + " already registered");
        }
    }

    // Unused DAO methods
    @Override
    public void update(ClientDTO clientDTO) {
    }

    @Override
    public void delete(Long id) {
    }
}