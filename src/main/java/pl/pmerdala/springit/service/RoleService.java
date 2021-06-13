package pl.pmerdala.springit.service;

import org.springframework.stereotype.Service;
import pl.pmerdala.springit.domain.Role;
import pl.pmerdala.springit.repositories.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String roleName){
        return roleRepository.findByName(roleName).orElseThrow();
    }
}
