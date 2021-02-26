package com.example.jpa.service;

import com.example.jpa.param.RoleInfo;
import com.example.jpa.pojo.Role;
import com.example.jpa.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Role> getByUsername(String username) {
        return repository.findByName(username);
    }

    @Override
    public Role getByUsernameNonNull(String username) {
        return getByUsername(username).orElseThrow(() -> new RuntimeException("32432"));
    }

    @Override
    public void createRole(RoleInfo roleInfo) {
        Role role = new Role();
        role.setName(roleInfo.getRoleName());
        repository.save(role);
        log.info(String.format("创建的角色名称:%s,角色ID: %s", role.getName(), role.getId()));
    }

    @Override
    public List<Role> getAllRoles() {
        return repository.findAll();
    }
}
