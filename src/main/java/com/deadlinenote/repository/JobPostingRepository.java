package com.deadlinenote.repository;
import com.deadlinenote.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface JobPostingRepository extends JpaRepository<JobPosting,Long>{List<JobPosting> findByPublishedTrueAndDeadlineGreaterThanEqualOrderByDeadlineAsc(LocalDate date);boolean existsBySourceUrlAndPublishedTrue(String sourceUrl);}
