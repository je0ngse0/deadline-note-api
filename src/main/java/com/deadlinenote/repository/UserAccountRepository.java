package com.deadlinenote.repository;
import com.deadlinenote.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long>{Optional<UserAccount> findByEmail(String email);}
