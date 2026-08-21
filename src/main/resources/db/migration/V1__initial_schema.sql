create table users (
  id bigserial primary key,
  email varchar(200) not null unique,
  display_name varchar(80) not null,
  role varchar(20) not null,
  created_at timestamptz not null
);

create table job_submissions (
  id bigserial primary key,
  submitter_id bigint not null references users(id),
  source_url varchar(500) not null,
  company_name varchar(120) not null,
  position_title varchar(180) not null,
  location varchar(120),
  employment_type varchar(60),
  deadline date not null,
  image_object_key varchar(500),
  ocr_text text,
  applicant_memo text,
  rejection_reason text,
  status varchar(20) not null,
  submission_version integer not null,
  submitted_at timestamptz not null,
  reviewed_at timestamptz,
  reviewer_id bigint references users(id)
);
create index idx_submission_status on job_submissions(status, submitted_at desc);
create index idx_submission_url on job_submissions(source_url);

create table job_postings (
  id bigserial primary key,
  company_name varchar(120) not null,
  position_title varchar(180) not null,
  location varchar(120),
  employment_type varchar(60),
  deadline date not null,
  source_url varchar(500) not null,
  logo_url varchar(500),
  published boolean not null,
  source_submission_id bigint unique references job_submissions(id),
  created_at timestamptz not null
);
create index idx_job_public_deadline on job_postings(published, deadline);
create index idx_job_source_url on job_postings(source_url);

create table user_jobs (
  id bigserial primary key,
  user_id bigint not null references users(id),
  job_posting_id bigint not null references job_postings(id),
  application_status varchar(30) not null,
  deadline_reminder boolean not null,
  updated_at timestamptz not null,
  constraint uk_user_job unique(user_id, job_posting_id)
);
