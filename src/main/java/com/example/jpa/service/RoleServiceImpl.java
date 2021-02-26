package com.example.jpa.service;

import com.example.jpa.pojo.Role;
import com.example.jpa.repository.RoleRepository;

import java.util.Optional;

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
}
