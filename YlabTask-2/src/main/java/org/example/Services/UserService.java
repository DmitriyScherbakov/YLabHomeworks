package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.example.Entity.AuditRecord;
import org.example.Repository.AuditService;
import org.example.Entity.User;
import org.example.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    AuditService auditService = new AuditService();

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void updateUserRole(String username, int role) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(role);
            userRepository.save(user);
            auditService.addAuditRecord(new AuditRecord(user.getId(), "update user", "currentTimestamp"));
        } else {
            // Обработка случая, когда пользователя с данным именем не существует
            System.out.println("The user with this username was not found.");
        }
    }


    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> userAuthorization(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
