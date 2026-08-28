# 마감노트 API

사용자 웹과 관리자 웹이 함께 사용하는 Spring Boot API입니다. PostgreSQL 테이블은 실행 시 Flyway가 생성합니다.

## Docker로 전체 서비스 실행

세 프로젝트가 `/Users/imjeongseo/workspace` 아래에 나란히 있어야 합니다.

```bash
cd /Users/imjeongseo/workspace/deadline-note-api
cp .env.example .env
```

`.env`에서 사용자와 관리자 이메일을 수정한 후 실행합니다.

```bash
colima start
docker-compose up -d --build
```

- 사용자 웹: `http://localhost:3000`
- 관리자 웹: `http://localhost:5173`
- API: `http://localhost:8080/api/jobs`
- PostgreSQL: `localhost:5432`

상태와 로그:

```bash
docker-compose ps
docker-compose logs -f api
```

종료:

```bash
docker-compose down
colima stop
```

DB 데이터까지 삭제하려는 경우에만 `docker-compose down -v`를 사용합니다.

## GitHub Actions CI/CD

각 저장소의 `.github/workflows/ci-cd.yml`이 다음을 수행합니다.

1. PR과 `main` 푸시에서 테스트 및 빌드
2. Docker 멀티 플랫폼 이미지(`linux/amd64`, `linux/arm64`) 생성
3. `main`과 `v*` 태그 푸시에서 GHCR로 이미지 발행

이미지 주소:

```text
ghcr.io/<GitHub 계정>/<저장소 이름>:latest
ghcr.io/<GitHub 계정>/<저장소 이름>:sha-<커밋>
```

사용자 웹 저장소의 GitHub `Settings → Secrets and variables → Actions → Variables`에 설정:

```text
NEXT_PUBLIC_API_BASE_URL=https://실제-api-주소
NEXT_PUBLIC_USER_EMAIL=개발용-사용자-이메일
```

관리자 웹 저장소에도 설정:

```text
VITE_API_BASE_URL=https://실제-api-주소
VITE_ADMIN_EMAIL=관리자-이메일
```

GHCR 발행에는 별도 토큰이 필요하지 않고 워크플로의 `GITHUB_TOKEN`을 사용합니다. 실제 서버 자동 재배포 단계는 배포 대상이 정해진 뒤 추가합니다.

현재 이메일 헤더 방식은 OAuth 도입 전 로컬 개발용입니다. 운영 배포 전에는 검증된 로그인 토큰으로 교체해야 합니다.
