# 마감노트 API

사용자 웹과 관리자 웹이 함께 사용하는 Spring Boot API입니다. PostgreSQL 테이블은 실행 시 Flyway가 생성합니다.

## 로컬 실행

```bash
colima start
cd /Users/imjeongseo/workspace/deadline-note-api
docker-compose up -d
BOOTSTRAP_ADMIN_EMAIL=admin@example.com ./gradlew bootRun
```

`BOOTSTRAP_ADMIN_EMAIL`은 로컬에서 관리자 권한을 부여할 이메일입니다. 관리자 웹의 `VITE_ADMIN_EMAIL`과 같은 값을 사용합니다.

## 프론트엔드 설정

사용자 웹 `/Users/imjeongseo/workspace/deadline-note-user-web/.env.local`:

```dotenv
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080
NEXT_PUBLIC_USER_EMAIL=user@example.com
```

관리자 웹 `/Users/imjeongseo/workspace/deadline-note-admin-web/.env`:

```dotenv
VITE_API_BASE_URL=http://localhost:8080
VITE_ADMIN_EMAIL=admin@example.com
```

현재 이메일 헤더는 OAuth 도입 전 로컬 개발을 위한 임시 인증 방식입니다. 운영 환경에서는 검증된 로그인 토큰으로 교체해야 합니다.

## 주요 API

- `GET /api/jobs`
- `POST /api/submissions`
- `GET /api/submissions/me`
- `PUT /api/jobs/{id}/my-job`
- `GET /api/admin/submissions`
- `POST /api/admin/submissions/{id}/approve`
- `POST /api/admin/submissions/{id}/reject`
- `POST /api/admin/jobs`
