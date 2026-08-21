package com.deadlinenote.api;

import com.deadlinenote.api.ApiModels.*;
import com.deadlinenote.domain.UserAccount;
import com.deadlinenote.security.CurrentUserService;
import com.deadlinenote.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/admin")
public class AdminController {
    private final JobService service;private final CurrentUserService current;
    public AdminController(JobService service,CurrentUserService current){this.service=service;this.current=current;}
    @GetMapping("/submissions") List<SubmissionResponse> submissions(HttpServletRequest req){current.requireAdmin(req);return service.allSubmissions();}
    @PostMapping("/submissions/{id}/approve") JobResponse approve(HttpServletRequest req,@PathVariable Long id,@Valid @RequestBody ReviewRequest body){UserAccount admin=current.requireAdmin(req);return service.approve(admin,id,body);}
    @PostMapping("/submissions/{id}/reject") SubmissionResponse reject(HttpServletRequest req,@PathVariable Long id,@Valid @RequestBody RejectRequest body){return service.reject(current.requireAdmin(req),id,body.reason());}
    @PostMapping("/jobs") JobResponse create(HttpServletRequest req,@Valid @RequestBody ReviewRequest body){current.requireAdmin(req);return service.createDirect(body);}
}
