# 마감노트 (Deadline Note)

취준생이 검수된 채용공고를 찾고 즐겨찾기, 지원 상태, 마감 알림을 관리하는 프로젝트입니다.

## 프로젝트 구성

- `user-web`: 배포용 사용자 웹. 공고 탐색, 등록 신청, 즐겨찾기와 지원 상태 관리
- `admin-web`: 로컬 전용 관리자 웹. 신청 검수, 수정 승인, 반려, 공고 직접 등록
- `api`: Spring Boot API. 사용자·공고·신청·지원 상태와 관리자 권한 처리
- `compose.yaml`: 로컬 PostgreSQL 17

## 로컬 실행

Colima와 PostgreSQL을 시작합니다.

```bash
colima start
docker-compose up -d
```

API:

```bash
cd api
./gradlew bootRun
```

사용자 웹:

```bash
cd user-web
npm install
npm run dev
```

관리자 웹:

```bash
cd admin-web
npm install
npm run dev
```

- 사용자 웹: `http://localhost:3000`
- 관리자 웹: `http://localhost:5173`
- API: `http://localhost:8080`

개발 API는 `X-User-Email` 헤더가 없으면 `demo.user@local.test`를 사용합니다. 관리자 API 테스트에는 `X-User-Email: admin@local.test`를 사용합니다. 운영 배포 전에는 Google OAuth 인증으로 교체해야 합니다.

## 주요 API

- `GET /api/jobs`
- `POST /api/submissions`
- `GET /api/submissions/me`
- `PUT /api/jobs/{id}/my-job`
- `GET /api/admin/submissions`
- `POST /api/admin/submissions/{id}/approve`
- `POST /api/admin/submissions/{id}/reject`
- `POST /api/admin/jobs`

## 데이터베이스

- 제품: PostgreSQL 17
- 로컬 데이터베이스: `job_deadline`
- 사용자: `job_app`
- 스키마 변경: Flyway `api/src/main/resources/db/migration`
