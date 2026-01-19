 package com.imdad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imdad.entity.UserRolesEntity;

@Repository
public interface RoleRepository extends JpaRepository<UserRolesEntity, Integer>{

}
