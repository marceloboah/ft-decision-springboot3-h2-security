package br.com.firstdecisionapp.firstdecisionapp.service;

import br.com.firstdecisionapp.firstdecisionapp.dto.TicketDTO;
import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import br.com.firstdecisionapp.firstdecisionapp.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {

        Ticket ticket = new Ticket();
        ticket.setNome("João da Silva");
        ticket.setEmail("joao@example.com");
        ticket.setDepartamento("Departamento de TI");
        ticket.setTituloProblema("Problema na conexão de rede");
        ticket.setDescricaoProblema("Não consigo me conectar à rede corporativa.");
        ticket.setCategoriaProblema("Rede");
        ticket.setPrioridade("Alta");
        ticket.setStatusAndamento("Pendente");
        ticket.setDataAbertura(String.valueOf(LocalDate.now()));
        ticket.setDataUltimaAtualizacao(String.valueOf(LocalDate.now()));


        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setNome("João da Silva");
        ticketDTO.setEmail("joao@example.com");
        ticketDTO.setDepartamento("Departamento de TI");
        ticketDTO.setTituloProblema("Problema na conexão de rede");
        ticketDTO.setDescricaoProblema("Não consigo me conectar à rede corporativa.");
        ticketDTO.setCategoriaProblema("Rede");
        ticketDTO.setPrioridade("Alta");
        ticketDTO.setStatusAndamento("Pendente");
        ticketDTO.setDataAbertura(String.valueOf(LocalDate.now()));
        ticketDTO.setDataUltimaAtualizacao(String.valueOf(LocalDate.now()));


        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket createdTicket = ticketService.create(ticketDTO);

        assertEquals(ticket.getNome(), createdTicket.getNome());
        assertEquals(ticket.getEmail(), createdTicket.getEmail());
    }

}
