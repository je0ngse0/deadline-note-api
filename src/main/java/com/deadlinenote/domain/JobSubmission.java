package com.deadlinenote.domain;

import jakarta.persistence.*;
import java.time.*;

@Entity @Table(name="job_submissions")
public class JobSubmission {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="submitter_id") public UserAccount submitter;
    @Column(nullable=false,length=500) public String sourceUrl;
    @Column(nullable=false,length=120) public String companyName;
    @Column(nullable=false,length=180) public String positionTitle;
    @Column(length=120) public String location;
    @Column(length=60) public String employmentType;
    @Column(nullable=false) public LocalDate deadline;
    @Column(length=500) public String imageObjectKey;
    @Column(columnDefinition="text") public String ocrText;
    @Column(columnDefinition="text") public String applicantMemo;
    @Column(columnDefinition="text") public String rejectionReason;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) public SubmissionStatus status=SubmissionStatus.PENDING;
    @Column(nullable=false) public int submissionVersion=1;
    @Column(nullable=false) public Instant submittedAt=Instant.now();
    public Instant reviewedAt;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="reviewer_id") public UserAccount reviewer;
}
