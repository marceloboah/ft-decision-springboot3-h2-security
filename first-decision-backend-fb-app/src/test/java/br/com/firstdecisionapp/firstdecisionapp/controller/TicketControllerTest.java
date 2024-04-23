package br.com.firstdecisionapp.firstdecisionapp.controller;

import br.com.firstdecisionapp.firstdecisionapp.dto.TicketDTO;
import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import br.com.firstdecisionapp.firstdecisionapp.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@CrossOrigin
//@WebMvcTest(TicketController.class)
//@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTicket() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setNome("Marcelo");
        ticketDTO.setEmail("marcelo@email.com");
        ticketDTO.setDepartamento("TI");
        ticketDTO.setTituloProblema("Título Problema");
        ticketDTO.setDescricaoProblema("Descricao Problema");
        ticketDTO.setCategoriaProblema("Categoria Problema");
        ticketDTO.setPrioridade("Alta");
        ticketDTO.setStatusAndamento("Em aberto");
        ticketDTO.setDataAbertura(String.valueOf(LocalDate.now()));
        ticketDTO.setDataUltimaAtualizacao(String.valueOf(LocalDate.now()));

        when(ticketService.create(any())).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isCreated());


        /*String ticketJson = "{ \"nome\": \"John Doe\", \"email\": \"john@example.com\", \"departamento\": \"IT\", \"tituloProblema\": \"Problema X\", \"descricaoProblema\": \"Descrição do problema\", \"categoriaProblema\": \"Categoria\", \"prioridade\": \"Alta\", \"statusAndamento\": \"Aberto\", \"dataAbertura\": \"2022-04-25\", \"dataUltimaAtualizacao\": \"2022-04-25\", \"feedback\": \"\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ticket/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());*/
    }

    @Test
    public void testFindAllTickets() throws Exception {
        when(ticketService.findAll()).thenReturn(Collections.singletonList(new Ticket()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllPaginatedTickets() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Ticket> page = new PageImpl<>(Collections.singletonList(new Ticket()), pageable, 1);
        when(ticketService.findAll(pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/all-paginated"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByEmail() throws Exception {
        String email = "test@example.com";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Ticket> page = new PageImpl<>(Collections.singletonList(new Ticket()), pageable, 1);
        when(ticketService.findByEmail(anyString(), any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/by-email")
                        .param("email", email))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTicketById() throws Exception {
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        when(ticketService.getById(anyLong())).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/get-by-id/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditTicket() throws Exception {

        TicketDTO ticketDto = new TicketDTO(); // Crie um objeto Ticket para editar
        ticketDto.setId(1L);

        Ticket ticket = new Ticket(); // Crie um objeto Ticket para editar
        ticket.setId(1L);


        when(ticketService.update(any(TicketDTO.class))).thenReturn(ticket);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(ticket);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/ticket/edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

    }

    @Test
    void testFindAllPaginated() throws Exception {
        // Mocking the service method
        Page<Ticket> page = new PageImpl<>(Collections.emptyList());
        when(ticketService.findByEmail(anyString(), any(Pageable.class))).thenReturn(page);

        // Performing the request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ticket/by-email-paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("email", "email@example.com"))
                .andExpect(status().isBadRequest());
    }



}