package com.deadlinenote.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "users")
public class UserAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable=false,unique=true,length=200) public String email;
    @Column(nullable=false,length=80) public String displayName;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) public Role role = Role.USER;
    @Column(nullable=false) public Instant createdAt = Instant.now();
    protected UserAccount() {}
    public UserAccount(String email,String displayName){this.email=email;this.displayName=displayName;}
}
