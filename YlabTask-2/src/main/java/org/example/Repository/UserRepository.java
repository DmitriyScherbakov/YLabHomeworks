package org.example.Repository;

import org.example.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void save(User user);
    Optional<User> findById(int id);
    void deleteById(int id);
    List<User> getAllUsers();

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);


}
