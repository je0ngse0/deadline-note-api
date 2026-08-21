package com.deadlinenote.api;

import com.deadlinenote.domain.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public final class ApiModels {
    private ApiModels() {}
    public record JobResponse(Long id,String companyName,String positionTitle,String location,String employmentType,LocalDate deadline,long daysLeft,String sourceUrl,String logoUrl) {
        public static JobResponse from(JobPosting j){return new JobResponse(j.id,j.companyName,j.positionTitle,j.location,j.employmentType,j.deadline,Math.max(0,ChronoUnit.DAYS.between(LocalDate.now(),j.deadline)),j.sourceUrl,j.logoUrl);}
    }
    public record SubmissionRequest(@NotBlank @Size(max=500) String sourceUrl,@NotBlank @Size(max=120) String companyName,@NotBlank @Size(max=180) String positionTitle,@Size(max=120) String location,@Size(max=60) String employmentType,@NotNull @FutureOrPresent LocalDate deadline,@Size(max=2000) String applicantMemo) {}
    public record SubmissionResponse(Long id,String submitterEmail,String sourceUrl,String companyName,String positionTitle,String location,String employmentType,LocalDate deadline,SubmissionStatus status,String rejectionReason,int submissionVersion,Instant submittedAt,boolean duplicateUrl) {
        public static SubmissionResponse from(JobSubmission s,boolean duplicate){return new SubmissionResponse(s.id,s.submitter.email,s.sourceUrl,s.companyName,s.positionTitle,s.location,s.employmentType,s.deadline,s.status,s.rejectionReason,s.submissionVersion,s.submittedAt,duplicate);}
    }
    public record ReviewRequest(@NotBlank String companyName,@NotBlank String positionTitle,String location,String employmentType,@NotNull @FutureOrPresent LocalDate deadline,@NotBlank String sourceUrl,String logoUrl) {}
    public record RejectRequest(@NotBlank @Size(max=1000) String reason) {}
    public record UserJobRequest(@NotNull ApplicationStatus status,boolean deadlineReminder) {}
    public record UserJobResponse(Long jobId,ApplicationStatus status,boolean deadlineReminder,Instant updatedAt) {}
}
