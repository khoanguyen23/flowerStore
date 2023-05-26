package com.uit.flowerstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.uit.flowerstore.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
