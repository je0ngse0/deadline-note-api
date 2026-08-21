package com.deadlinenote.repository;
import com.deadlinenote.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JobSubmissionRepository extends JpaRepository<JobSubmission,Long>{List<JobSubmission> findAllByOrderBySubmittedAtDesc();List<JobSubmission> findBySubmitterOrderBySubmittedAtDesc(UserAccount user);}
