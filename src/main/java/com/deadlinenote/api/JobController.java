package com.deadlinenote.api;

import com.deadlinenote.api.ApiModels.*;
import com.deadlinenote.security.CurrentUserService;
import com.deadlinenote.service.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api")
public class JobController {
    private final JobService service; private final CurrentUserService current;
    public JobController(JobService service,CurrentUserService current){this.service=service;this.current=current;}
    @GetMapping("/jobs") List<JobResponse> jobs(){return service.publicJobs();}
    @PostMapping("/submissions") ResponseEntity<SubmissionResponse> submit(HttpServletRequest req,@Valid @RequestBody SubmissionRequest body){return ResponseEntity.status(HttpStatus.CREATED).body(service.submit(current.requireUser(req),body));}
    @GetMapping("/submissions/me") List<SubmissionResponse> mine(HttpServletRequest req){return service.mine(current.requireUser(req));}
    @PutMapping("/submissions/{id}") SubmissionResponse update(HttpServletRequest req,@PathVariable Long id,@Valid @RequestBody SubmissionRequest body){return service.updateRejected(current.requireUser(req),id,body);}
    @PostMapping("/submissions/{id}/resubmit") SubmissionResponse resubmit(HttpServletRequest req,@PathVariable Long id){return service.resubmit(current.requireUser(req),id);}
    @PutMapping("/jobs/{id}/my-job") UserJobResponse saveStatus(HttpServletRequest req,@PathVariable Long id,@Valid @RequestBody UserJobRequest body){return service.saveUserJob(current.requireUser(req),id,body);}
}
