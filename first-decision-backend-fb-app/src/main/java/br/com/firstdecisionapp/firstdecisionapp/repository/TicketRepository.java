package br.com.firstdecisionapp.firstdecisionapp.repository;

import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByEmail(String email, Pageable pageable);
    Page<Ticket> findById(Long id, Pageable pageable);
    Page<Ticket> findByTituloProblemaContaining(String tituloProblema, Pageable pageable);
    Page<Ticket> findByIdAndEmail(Long id, String email, Pageable pageable);
    Page<Ticket> findByTituloProblemaContainingAndEmail(String tituloProblema, String email, Pageable pageable);
}
