package br.com.firstdecisionapp.firstdecisionapp.repository;

import br.com.firstdecisionapp.firstdecisionapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
