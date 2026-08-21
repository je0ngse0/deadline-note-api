package com.deadlinenote.service;

import com.deadlinenote.api.ApiModels.*;
import com.deadlinenote.domain.*;
import com.deadlinenote.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service @Transactional
public class JobService {
    private final JobPostingRepository jobs; private final JobSubmissionRepository submissions; private final UserJobRepository userJobs;
    public JobService(JobPostingRepository jobs,JobSubmissionRepository submissions,UserJobRepository userJobs){this.jobs=jobs;this.submissions=submissions;this.userJobs=userJobs;}
    @Transactional(readOnly=true) public List<JobResponse> publicJobs(){return jobs.findByPublishedTrueAndDeadlineGreaterThanEqualOrderByDeadlineAsc(LocalDate.now()).stream().map(JobResponse::from).toList();}
    public SubmissionResponse submit(UserAccount user,SubmissionRequest r){JobSubmission s=new JobSubmission();apply(s,r);s.submitter=user;s.status=SubmissionStatus.PENDING;s.submittedAt=Instant.now();return response(submissions.save(s));}
    public SubmissionResponse updateRejected(UserAccount user,Long id,SubmissionRequest r){JobSubmission s=owned(user,id);if(s.status!=SubmissionStatus.REJECTED)throw new IllegalStateException("반려된 신청만 수정할 수 있습니다.");apply(s,r);return response(s);}
    public SubmissionResponse resubmit(UserAccount user,Long id){JobSubmission s=owned(user,id);if(s.status!=SubmissionStatus.REJECTED)throw new IllegalStateException("반려된 신청만 재신청할 수 있습니다.");s.status=SubmissionStatus.PENDING;s.rejectionReason=null;s.submissionVersion++;s.submittedAt=Instant.now();return response(s);}
    @Transactional(readOnly=true) public List<SubmissionResponse> mine(UserAccount user){return submissions.findBySubmitterOrderBySubmittedAtDesc(user).stream().map(this::response).toList();}
    @Transactional(readOnly=true) public List<SubmissionResponse> allSubmissions(){return submissions.findAllByOrderBySubmittedAtDesc().stream().map(this::response).toList();}
    public SubmissionResponse reject(UserAccount admin,Long id,String reason){JobSubmission s=findSubmission(id);requirePending(s);s.status=SubmissionStatus.REJECTED;s.rejectionReason=reason;s.reviewer=admin;s.reviewedAt=Instant.now();return response(s);}
    public JobResponse approve(UserAccount admin,Long id,ReviewRequest r){JobSubmission s=findSubmission(id);requirePending(s);JobPosting j=new JobPosting();j.companyName=r.companyName();j.positionTitle=r.positionTitle();j.location=r.location();j.employmentType=r.employmentType();j.deadline=r.deadline();j.sourceUrl=r.sourceUrl();j.logoUrl=r.logoUrl();j.sourceSubmission=s;s.status=SubmissionStatus.APPROVED;s.reviewer=admin;s.reviewedAt=Instant.now();return JobResponse.from(jobs.save(j));}
    public JobResponse createDirect(ReviewRequest r){JobPosting j=new JobPosting();j.companyName=r.companyName();j.positionTitle=r.positionTitle();j.location=r.location();j.employmentType=r.employmentType();j.deadline=r.deadline();j.sourceUrl=r.sourceUrl();j.logoUrl=r.logoUrl();return JobResponse.from(jobs.save(j));}
    public UserJobResponse saveUserJob(UserAccount user,Long jobId,UserJobRequest r){JobPosting job=jobs.findById(jobId).orElseThrow();UserJob uj=userJobs.findByUserAndJobPosting(user,job).orElseGet(()->{UserJob n=new UserJob();n.user=user;n.jobPosting=job;return n;});uj.applicationStatus=r.status();uj.deadlineReminder=r.deadlineReminder();uj.updatedAt=Instant.now();userJobs.save(uj);return new UserJobResponse(jobId,uj.applicationStatus,uj.deadlineReminder,uj.updatedAt);}
    private void apply(JobSubmission s,SubmissionRequest r){s.sourceUrl=r.sourceUrl().trim();s.companyName=r.companyName().trim();s.positionTitle=r.positionTitle().trim();s.location=r.location();s.employmentType=r.employmentType();s.deadline=r.deadline();s.applicantMemo=r.applicantMemo();}
    private JobSubmission owned(UserAccount u,Long id){JobSubmission s=findSubmission(id);if(!s.submitter.id.equals(u.id))throw new SecurityException("본인의 신청만 수정할 수 있습니다.");return s;}
    private JobSubmission findSubmission(Long id){return submissions.findById(id).orElseThrow();}
    private void requirePending(JobSubmission s){if(s.status!=SubmissionStatus.PENDING)throw new IllegalStateException("심사 대기 신청만 처리할 수 있습니다.");}
    private SubmissionResponse response(JobSubmission s){boolean duplicate=jobs.existsBySourceUrlAndPublishedTrue(s.sourceUrl)||submissions.findAll().stream().anyMatch(other->!other.id.equals(s.id)&&other.sourceUrl.equalsIgnoreCase(s.sourceUrl)&&other.status==SubmissionStatus.PENDING);return SubmissionResponse.from(s,duplicate);}
}
