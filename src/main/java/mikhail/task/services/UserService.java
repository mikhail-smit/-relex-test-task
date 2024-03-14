package mikhail.task.services;

import lombok.RequiredArgsConstructor;
import mikhail.task.exceptions.UserNotFoundException;
import mikhail.task.models.Role;
import mikhail.task.models.User;
import mikhail.task.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found, id: " + id));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getByName("ROLE_WORKER")));
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user, int id) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    public User addRole(int id, Role role) {
        User toUpdate = getById(id);
        List<Role> roles = toUpdate.getRoles();
        roles.add(role);
        toUpdate.setRoles(roles);
        return userRepository.save(toUpdate);
    }

    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
