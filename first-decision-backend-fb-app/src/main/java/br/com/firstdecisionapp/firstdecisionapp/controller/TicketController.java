package br.com.firstdecisionapp.firstdecisionapp.controller;

import br.com.firstdecisionapp.firstdecisionapp.dto.TicketDTO;
import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import br.com.firstdecisionapp.firstdecisionapp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TicketDTO ticketDTO) {
        try {
            return new ResponseEntity<Object>(ticketService.create(ticketDTO), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Object>("Erro ao criar o ticket", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Ticket>> findAll() {
        try {
            Iterable<Ticket> tickets = ticketService.findAll();
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-paginated")
    public ResponseEntity<Page<Ticket>> findAllPaginated(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Ticket> tickets = ticketService.findAll(pageable);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-email-paginated")
    public ResponseEntity<Page<Ticket>> findAllPaginated(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam String userEmail) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Ticket> tickets = ticketService.findByEmail(userEmail, pageable);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-subject-and-search")
    public ResponseEntity<Page<Ticket>> findBySubjectAndSearch(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam String email,
                                                               @RequestParam String subject,
                                                               @RequestParam String searchInput) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Ticket> tickets = ticketService.findBySubjectAndSearch(subject, searchInput, email, pageable);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<Page<Ticket>> findByEmail(@RequestParam String email,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Ticket> tickets = ticketService.findByEmail(email, pageable);
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable("id") Long id) {
        Ticket ticket = ticketService.getById(id);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editTicket(@PathVariable("id") Long id, @RequestBody TicketDTO ticketDTO) {
        try{
            ticketService.update(ticketDTO);
            return new ResponseEntity<>(ticketService.update(ticketDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro ao editar o ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


