package com.deadlinenote.config;

import com.deadlinenote.domain.JobPosting;
import com.deadlinenote.domain.Role;
import com.deadlinenote.domain.UserAccount;
import com.deadlinenote.repository.JobPostingRepository;
import com.deadlinenote.repository.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DemoDataInitializer {
    @Bean CommandLineRunner demoData(UserAccountRepository users,JobPostingRepository jobs){return args->{
        UserAccount admin=users.findByEmail("admin@local.test").orElseGet(()->users.save(new UserAccount("admin@local.test","로컬 관리자")));
        if(admin.role!=Role.ADMIN){admin.role=Role.ADMIN;users.save(admin);}
        if(jobs.count()==0){
            save(jobs,"모노랩스","Backend Engineer (신입)","서울 강남","정규직",3,"https://example.com/jobs/mono-backend");
            save(jobs,"클라우드웨이브","Cloud Operations Engineer","서울 마포","정규직",6,"https://example.com/jobs/cloud-ops");
            save(jobs,"데이터포레스트","Data Platform Engineer","경기 판교","채용연계형 인턴",9,"https://example.com/jobs/data");
        }
    };}
    private void save(JobPostingRepository repo,String company,String title,String location,String type,int days,String url){JobPosting j=new JobPosting();j.companyName=company;j.positionTitle=title;j.location=location;j.employmentType=type;j.deadline=LocalDate.now().plusDays(days);j.sourceUrl=url;repo.save(j);}
}
