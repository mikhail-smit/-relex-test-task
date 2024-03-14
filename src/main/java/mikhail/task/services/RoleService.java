package mikhail.task.services;

import mikhail.task.exceptions.RoleNotFoundException;
import mikhail.task.models.Role;
import mikhail.task.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getById(int id) {
        return roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name).orElseThrow(RoleNotFoundException::new);
    }
}
