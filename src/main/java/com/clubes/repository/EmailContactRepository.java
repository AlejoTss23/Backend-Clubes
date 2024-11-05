package com.clubes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clubes.model.EmailContact;

@Repository
public interface EmailContactRepository extends JpaRepository<EmailContact, Long> {
}
