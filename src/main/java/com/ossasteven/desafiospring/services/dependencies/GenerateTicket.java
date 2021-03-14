package com.ossasteven.desafiospring.services.dependencies;

import com.ossasteven.desafiospring.exception.InvalidRequestParam;
import com.ossasteven.desafiospring.exception.StoreException;
import com.ossasteven.desafiospring.model.ArticleDTO;
import com.ossasteven.desafiospring.model.ClientDTO;
import com.ossasteven.desafiospring.model.ShoppingCartDTO;
import com.ossasteven.desafiospring.model.TicketDTO;
import com.ossasteven.desafiospring.repository.IArticleRepository;
import com.ossasteven.desafiospring.repository.IClientRepository;
import com.ossasteven.desafiospring.validation.PurchaseValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GenerateTicket implements IGenerateTicket<TicketDTO, ShoppingCartDTO, ArticleDTO> {

    IArticleRepository<ArticleDTO> repository;
    IClientRepository<ClientDTO> clientRepository;
    PurchaseValidation validation;

    @Autowired
    public GenerateTicket(IArticleRepository<ArticleDTO> repository, IClientRepository<ClientDTO> clientRepository, PurchaseValidation validation) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.validation = validation;
    }

    private Long ticketId;
    private Long cartId;
    private HashMap<Long, ShoppingCartDTO> carts;
    private HashMap<Long, Double> tickets;

    @PostConstruct
    public void setTickedId() {
        this.ticketId = 0L;
        this.cartId = 0L;
        this.carts = new HashMap<>();
        this.tickets = new HashMap<>();
    }

    @Override
    public TicketDTO generateTicket(List<ArticleDTO> articles) throws StoreException {

        for (ArticleDTO article : articles) {

            ArticleDTO found;

            if (article.getId() != null) {
                found = repository.getById(article.getId());

            } else if (article.getName() != null && article.getTradeMark() != null) {
                found = repository.getByNameAndBrand(article.getName(), article.getTradeMark());

            } else {
                throw new InvalidRequestParam("unable to find the product, be sure to send a valid productId or a valid name and trade mark");
            }

            if (validation.validateFields(article, found))
                validation.checkStock(article, found);
        }

        return ticketHandler(articles);
    }

    private TicketDTO ticketHandler(List<ArticleDTO> request) {
        this.ticketId++;
        TicketDTO ticket = new TicketDTO();
        double total = 0d;

        for (ArticleDTO article :
                request) {
            total = total + article.getPrice() * article.getQuantity();
        }

        ticket.setId(ticketId);
        ticket.setArticles(request);
        ticket.setTotal(total);
        this.tickets.put(ticketId, total);
        return ticket;
    }

    @Override
    public ShoppingCartDTO generateShoppingCart(List<ArticleDTO> cartComponents, Long clientId) throws StoreException {

        if (clientId == null)
            throw new InvalidRequestParam("Invalid clientId or null");


        System.out.println("clientId = " + clientId);

        ClientDTO client = clientRepository.getById(clientId);

        if (!carts.containsKey(clientId) && client != null)
            return generateNewCart(cartComponents, clientId);

        return updateShoppingCart(cartComponents, clientId);

    }

    @Override
    public Double confirmPurchase(Long ticketId) {
        return this.tickets.get(ticketId);
    }

    private ShoppingCartDTO generateNewCart(List<ArticleDTO> cartComponents, Long clientId) throws StoreException {
        ShoppingCartDTO newCArt = new ShoppingCartDTO();
        TicketDTO ticket = generateTicket(cartComponents);


        List<ArticleDTO> articles = new ArrayList<>(ticket.getArticles());

        newCArt.setClientID(clientId);
        newCArt.setProducts(articles);
        newCArt.setTotalShoppingCart(ticket.getTotal());
        newCArt.setTicketId(ticket.getId());

        this.cartId++;
        newCArt.setShoppingCartId(this.cartId);
        this.carts.put(clientId, newCArt);
        this.tickets.put(newCArt.getTicketId(), newCArt.getTotalShoppingCart());
        return newCArt;
    }

    private ShoppingCartDTO updateShoppingCart(List<ArticleDTO> newArticles, Long clientId) throws StoreException {
        ShoppingCartDTO sCart = carts.get(clientId);
        TicketDTO newTicket = generateTicket(newArticles);
        List<ArticleDTO> sCartProducts = sCart.getProducts();
        List<ArticleDTO> ticketProducts = newTicket.getArticles();
        sCartProducts.addAll(ticketProducts);
        sCart.setProducts(sCartProducts);
        sCart.setTotalShoppingCart(sCart.getTotalShoppingCart() + newTicket.getTotal());
        carts.put(clientId, sCart);
        this.tickets.put(sCart.getTicketId(), sCart.getTotalShoppingCart());
        return sCart;
    }
}