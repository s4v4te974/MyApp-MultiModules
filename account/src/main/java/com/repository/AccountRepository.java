package com.repository;


import com.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT u FROM Account u WHERE u.login = :login AND u.password = :password")
    Account retrieveAccount(@Param("login") String login, @Param("password") String password);

}