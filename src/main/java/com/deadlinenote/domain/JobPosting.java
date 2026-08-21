package com.deadlinenote.domain;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="job_postings")
public class JobPosting {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @Column(nullable=false,length=120) public String companyName;
    @Column(nullable=false,length=180) public String positionTitle;
    @Column(length=120) public String location;
    @Column(length=60) public String employmentType;
    @Column(nullable=false) public LocalDate deadline;
    @Column(nullable=false,length=500) public String sourceUrl;
    @Column(length=500) public String logoUrl;
    @Column(nullable=false) public boolean published=true;
    @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="source_submission_id") public JobSubmission sourceSubmission;
    @Column(nullable=false) public Instant createdAt=Instant.now();
}
