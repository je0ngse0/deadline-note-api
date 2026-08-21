package com.deadlinenote.security;

import com.deadlinenote.domain.*;
import com.deadlinenote.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserAccountRepository users;
    public CurrentUserService(UserAccountRepository users){this.users=users;}
    public UserAccount requireUser(HttpServletRequest request){
        String email=request.getHeader("X-User-Email");
        if(email==null||email.isBlank()) email="demo.user@local.test";
        String finalEmail=email.trim().toLowerCase();
        return users.findByEmail(finalEmail).orElseGet(()->users.save(new UserAccount(finalEmail,finalEmail.split("@")[0])));
    }
    public UserAccount requireAdmin(HttpServletRequest request){
        UserAccount user=requireUser(request);
        if(user.role!=Role.ADMIN) throw new ForbiddenException("관리자 권한이 필요합니다.");
        return user;
    }
    public static class ForbiddenException extends RuntimeException { public ForbiddenException(String message){super(message);} }
}
