package com.deadlinenote.repository;
import com.deadlinenote.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserJobRepository extends JpaRepository<UserJob,Long>{Optional<UserJob> findByUserAndJobPosting(UserAccount user,JobPosting job);List<UserJob> findByUserOrderByUpdatedAtDesc(UserAccount user);}
