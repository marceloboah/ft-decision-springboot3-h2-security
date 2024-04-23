package br.com.firstdecisionapp.firstdecisionapp.service;

import br.com.firstdecisionapp.firstdecisionapp.dto.TicketDTO;
import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import br.com.firstdecisionapp.firstdecisionapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket create(TicketDTO ticketDTO) {
        validateTicketDTO(ticketDTO); // Validar TicketDTO antes de criar o Ticket

        Ticket ticket = new Ticket();
        ticket.setNome(ticketDTO.getNome());
        ticket.setEmail(ticketDTO.getEmail());
        ticket.setDepartamento(ticketDTO.getDepartamento());
        ticket.setTituloProblema(ticketDTO.getTituloProblema());
        ticket.setDescricaoProblema(ticketDTO.getDescricaoProblema());
        ticket.setCategoriaProblema(ticketDTO.getCategoriaProblema());
        ticket.setPrioridade(ticketDTO.getPrioridade());
        ticket.setStatusAndamento(ticketDTO.getStatusAndamento());
        ticket.setDataAbertura(String.valueOf(LocalDate.now()));
        ticket.setDataUltimaAtualizacao(String.valueOf(LocalDate.now()));
        return ticketRepository.save(ticket);


    }

    private void validateTicketDTO(TicketDTO ticketDTO) {
        if (ticketDTO.getNome() == null || ticketDTO.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode estar vazio");
        }
        if (ticketDTO.getEmail() == null || ticketDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode estar vazio");
        }
        if (ticketDTO.getDepartamento() == null || ticketDTO.getDepartamento().isEmpty()) {
            throw new IllegalArgumentException("Departamento não pode estar vazio");
        }
        if (ticketDTO.getTituloProblema() == null || ticketDTO.getTituloProblema().isEmpty()) {
            throw new IllegalArgumentException("Título do problema não pode estar vazio");
        }
        if (ticketDTO.getDescricaoProblema() == null || ticketDTO.getDescricaoProblema().isEmpty()) {
            throw new IllegalArgumentException("Descrição do problema não pode estar vazia");
        }
        if (ticketDTO.getCategoriaProblema() == null || ticketDTO.getCategoriaProblema().isEmpty()) {
            throw new IllegalArgumentException("Categoria do problema não pode estar vazia");
        }
        if (ticketDTO.getPrioridade() == null || ticketDTO.getPrioridade().isEmpty()) {
            throw new IllegalArgumentException("Prioridade não pode estar vazia");
        }
        if (ticketDTO.getStatusAndamento() == null || ticketDTO.getStatusAndamento().isEmpty()) {
            throw new IllegalArgumentException("Status de andamento não pode estar vazio");
        }
        if (ticketDTO.getDataAbertura() == null || ticketDTO.getDataAbertura().isEmpty()) {
            throw new IllegalArgumentException("Data de abertura não pode estar vazia");
        }
        if (ticketDTO.getDataUltimaAtualizacao() == null || ticketDTO.getDataUltimaAtualizacao().isEmpty()) {
            throw new IllegalArgumentException("Data de última atualização não pode estar vazia");
        }
    }

    public Iterable<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Page<Ticket> findByEmail(String email, Pageable pageable) {
        return ticketRepository.findByEmail(email, pageable);
    }

    public Ticket getById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket save(TicketDTO ticketDTO) throws Exception {

        Ticket ticket = this.findById(ticketDTO.getId());
        if (ticket == null) {
            throw new Exception("Ticket não encontrado");
        }

        if (ticketDTO.getNome() != null && !ticketDTO.getNome().isEmpty()) {
            ticket.setNome(ticketDTO.getNome());
        }
        if (ticketDTO.getEmail() != null && !ticketDTO.getEmail().isEmpty()) {
            ticket.setEmail(ticketDTO.getEmail());
        }
        if (ticketDTO.getDepartamento() != null && !ticketDTO.getDepartamento().isEmpty()) {
            ticket.setDepartamento(ticketDTO.getDepartamento());
        }
        if (ticketDTO.getTituloProblema() != null && !ticketDTO.getTituloProblema().isEmpty()) {
            ticket.setTituloProblema(ticketDTO.getTituloProblema());
        }
        if (ticketDTO.getDescricaoProblema() != null && !ticketDTO.getDescricaoProblema().isEmpty()) {
            ticket.setDescricaoProblema(ticketDTO.getDescricaoProblema());
        }
        if (ticketDTO.getCategoriaProblema() != null && !ticketDTO.getCategoriaProblema().isEmpty()) {
            ticket.setCategoriaProblema(ticketDTO.getCategoriaProblema());
        }
        if (ticketDTO.getPrioridade() != null && !ticketDTO.getPrioridade().isEmpty()) {
            ticket.setPrioridade(ticketDTO.getPrioridade());
        }
        if (ticketDTO.getStatusAndamento() != null && !ticketDTO.getStatusAndamento().isEmpty()) {
            ticket.setStatusAndamento(ticketDTO.getStatusAndamento());
        }
        if (ticketDTO.getDataAbertura() != null && !ticketDTO.getDataAbertura().isEmpty()) {
            ticket.setDataAbertura(ticketDTO.getDataAbertura());
        }
        if (ticketDTO.getDataUltimaAtualizacao() != null) {
            ticket.setDataUltimaAtualizacao(ticketDTO.getDataUltimaAtualizacao());
        }

        return ticketRepository.save(ticket);
    }

    public Ticket update(TicketDTO ticketDTO) throws Exception {

        Ticket ticket = this.findById(ticketDTO.getId());
        if (ticket == null) {
            throw new Exception("Ticket não encontrado");
        }

        if (ticketDTO.getNome() != null && !ticketDTO.getNome().isEmpty()) {
            ticket.setNome(ticketDTO.getNome());
        }
        if (ticketDTO.getEmail() != null && !ticketDTO.getEmail().isEmpty()) {
            ticket.setEmail(ticketDTO.getEmail());
        }
        if (ticketDTO.getDepartamento() != null && !ticketDTO.getDepartamento().isEmpty()) {
            ticket.setDepartamento(ticketDTO.getDepartamento());
        }
        if (ticketDTO.getTituloProblema() != null && !ticketDTO.getTituloProblema().isEmpty()) {
            ticket.setTituloProblema(ticketDTO.getTituloProblema());
        }
        if (ticketDTO.getDescricaoProblema() != null && !ticketDTO.getDescricaoProblema().isEmpty()) {
            ticket.setDescricaoProblema(ticketDTO.getDescricaoProblema());
        }
        if (ticketDTO.getCategoriaProblema() != null && !ticketDTO.getCategoriaProblema().isEmpty()) {
            ticket.setCategoriaProblema(ticketDTO.getCategoriaProblema());
        }
        if (ticketDTO.getPrioridade() != null && !ticketDTO.getPrioridade().isEmpty()) {
            ticket.setPrioridade(ticketDTO.getPrioridade());
        }
        if (ticketDTO.getStatusAndamento() != null && !ticketDTO.getStatusAndamento().isEmpty()) {
            ticket.setStatusAndamento(ticketDTO.getStatusAndamento());
        }
        if (ticketDTO.getDataAbertura() != null && !ticketDTO.getDataAbertura().isEmpty()) {
            ticket.setDataAbertura(ticketDTO.getDataAbertura());
        }
        if (ticketDTO.getFeedback() != null && !ticketDTO.getFeedback().isEmpty()) {
            ticket.setFeedback(ticketDTO.getFeedback());
        }
        ticket.setDataUltimaAtualizacao(String.valueOf(LocalDate.now()));

        return ticketRepository.save(ticket);
    }
    public Page<Ticket> findBySubjectAndSearch(String subject, String searchInput, String email, Pageable pageable) {

        if (subject == null || searchInput == null || subject.isEmpty() || searchInput.isEmpty()) {
            throw new IllegalArgumentException("Subject e searchInput não podem ser nulos ou vazios");
        }


        if (!subject.equals("id") && !subject.equals("assunto")) {
            throw new IllegalArgumentException("Subject inválido. Deve ser 'id' ou 'assunto'");
        }


        if (subject.equals("id")) {
            return ticketRepository.findByIdAndEmail(Long.parseLong(searchInput), email, pageable);
        } else {
            return ticketRepository.findByTituloProblemaContainingAndEmail(searchInput, email, pageable);
        }
    }

}

