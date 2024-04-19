package com.cloudbees.ticketapp.repository;

import com.cloudbees.ticketapp.dbmodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

