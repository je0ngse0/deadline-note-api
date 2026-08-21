package com.deadlinenote.api;

import com.deadlinenote.security.CurrentUserService.ForbiddenException;
import com.deadlinenote.security.CurrentUserService.UnauthorizedException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class) ResponseEntity<?> unauthorized(UnauthorizedException e){return ResponseEntity.status(401).body(Map.of("message",e.getMessage()));}
    @ExceptionHandler(ForbiddenException.class) ResponseEntity<?> forbidden(ForbiddenException e){return ResponseEntity.status(403).body(Map.of("message",e.getMessage()));}
    @ExceptionHandler({IllegalStateException.class,SecurityException.class}) ResponseEntity<?> conflict(RuntimeException e){return ResponseEntity.status(409).body(Map.of("message",e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> invalid(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("message","입력값을 확인해주세요."));}
    @ExceptionHandler(java.util.NoSuchElementException.class) ResponseEntity<?> missing(){return ResponseEntity.status(404).body(Map.of("message","대상을 찾을 수 없습니다."));}
}
