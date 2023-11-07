package com.repository;


import com.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByLoginAndPassword(String login, String password);

    Optional<Account> findByNameAndLastNameAndEmail(String name, String lastName, String Email);

    List<Account> findAllByNameAndLastNameAndEmail(String name, String lastName, String Email);


}