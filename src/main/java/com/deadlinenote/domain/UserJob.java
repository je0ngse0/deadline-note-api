package com.deadlinenote.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="user_jobs",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","job_posting_id"}))
public class UserJob {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="user_id") public UserAccount user;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="job_posting_id") public JobPosting jobPosting;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) public ApplicationStatus applicationStatus=ApplicationStatus.INTERESTED;
    @Column(nullable=false) public boolean deadlineReminder=true;
    @Column(nullable=false) public Instant updatedAt=Instant.now();
}
