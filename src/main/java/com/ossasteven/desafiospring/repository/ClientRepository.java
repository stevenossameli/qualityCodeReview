package com.ossasteven.desafiospring.repository;

import com.ossasteven.desafiospring.dao.IClientDAO;
import com.ossasteven.desafiospring.exception.AlreadyExistsException;
import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.NotFoundException;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ClientDTO;
import com.ossasteven.desafiospring.util.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository implements IClientRepository<ClientDTO> {

    IClientDAO dao;

    Filter<ClientDTO> filter;

    @Autowired
    public ClientRepository(IClientDAO dao, Filter<ClientDTO> filter) {
        this.dao = dao;
        this.filter = filter;
    }

    @Override
    public Long saveClient(ClientDTO client) throws AlreadyExistsException {
        return dao.save(client);
    }

    @Override
    public List<ClientDTO> getAll(String param) throws NotFoundException, InvalidRequestParam {

        List<ClientDTO> clients = (List<ClientDTO>) dao.getAll();

        if (param == null)
            return clients;


        return filter.filterBy(clients, ClientDTO -> ClientDTO.getProvincia().equalsIgnoreCase(param));

    }

    @Override
    public ClientDTO getById(Long id) throws StoreException {
        return dao.findOne(id).orElse(null);
    }
}