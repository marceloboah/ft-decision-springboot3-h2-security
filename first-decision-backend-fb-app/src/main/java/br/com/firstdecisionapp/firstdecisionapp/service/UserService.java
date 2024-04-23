package br.com.firstdecisionapp.firstdecisionapp.service;

import br.com.firstdecisionapp.firstdecisionapp.model.Ticket;
import br.com.firstdecisionapp.firstdecisionapp.model.User;
import br.com.firstdecisionapp.firstdecisionapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Optional<User> createUser(User user) {
        Optional<User> existingUser = this.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return existingUser;
        } else {
            User savedUser = userRepository.save(user);
            return Optional.of(savedUser);
        }
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User updateUser(Integer id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: ");
        }
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}

