package mikhail.task.services;

import lombok.RequiredArgsConstructor;
import mikhail.task.exceptions.UserNotFoundException;
import mikhail.task.models.Role;
import mikhail.task.models.User;
import mikhail.task.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(cacheNames = "user", key = "#id")
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found, id: " + id));
    }

    @Cacheable(cacheNames = "user")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    // cache evict for update cache in get all method
    @CacheEvict(cacheNames = "user", allEntries = true)
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getByName("ROLE_WORKER")));
        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public User update(User user, int id) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public User addRole(int id, Role role) {
        User toUpdate = getById(id);
        List<Role> roles = toUpdate.getRoles();
        roles.add(role);
        toUpdate.setRoles(roles);
        return userRepository.save(toUpdate);
    }

    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

}
