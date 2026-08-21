package com.deadlinenote.config;

import com.deadlinenote.domain.Role;
import com.deadlinenote.domain.UserAccount;
import com.deadlinenote.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataInitializer {
    @Bean CommandLineRunner bootstrapAdmin(UserAccountRepository users,@Value("${app.bootstrap-admin-email:}") String configuredEmail){return args->{
        if(configuredEmail==null||configuredEmail.isBlank()) return;
        String email=configuredEmail.trim().toLowerCase();
        UserAccount admin=users.findByEmail(email).orElseGet(()->users.save(new UserAccount(email,email.split("@")[0])));
        if(admin.role!=Role.ADMIN){admin.role=Role.ADMIN;users.save(admin);}
    };}
}
