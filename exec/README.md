# 문서
### 기술 스택
<details>
<summary>프론트엔드</summary>
<div markdown="1">

|스택명|버전|비고|
|:---:|:---:|:---:|
|React	|18.2.0	latest||
|React-Router|	6.4	||
|Node.js|	18.17.0	LTS||
|TypeScript|	4.9.5	||
|Redu|	4.2.1	||
|Axios|	1.4.0	||
|openvidu-browser|	2.28.0	||
|React-Redux|	8.1.1	||
|Redux-js-tookit|	1.9.5	||
|mui|||

</div>
</details>

<details>
<summary>백엔드</summary>
<div markdown="1">

|스택명	|버전|	설명|
|:---:|:---:|:---:|
|Spring Boot|	3.0.8	|oAuth2, Security, Validation, Web, Websocket|
|Java	|openjdk 17.0.7 2023-04-18 LTS	||
|spring data jpa|	3.0.8	||
|Mapstruct	|1.4.2.Final	|Mapper 사용을 위한 라이브러리|
|dependency-management|	1.1.0	||
|asciidoctor|	3.3.2	||
|lombok|1.18.22	||
|lombok-mapstruct-binding|	0.2.0|	Lombok - MapStruct Binding|
|mariadb-java-client|	2.7.3	|MariaDB 드라이버|
|spring-restdocs-mockmvc|	3.0.0	||
|jjwt|	0.11.5	|JWT 사용 라이브러리|
|spring-boot-starter-data-redis|	3.1.2	|Redis|
|lettuce-core|	6.2.4.RELEASE	|Redis 사용을 위한 Client|
|spring-cloud-starter-aws|	2.2.6.RELEASE	|AWS S3 사용|
|jaxb-api|	2.3.1	|Java → XML|
|IntelliJ|Ultimate 2023.1||

</div>
</details>

<details>
<summary>인프라</summary>
<div markdown="1">

|스택명|	버전|
|:---:|:---:|
|AWS ec2	|Ubuntu 20.04 LTS|
|AWS RDS (MariaDB)	|10.6.14|
|Docker	|24.0.5|
|Docker compose	|v2.20.2|
|Nginx	|1.18.0|

</div>
</details>

### 배포 자료 실행 시 특이사항
- yml 파일 추가
  - `S09P12A805/BackEnd/src/main/resources` 해당 경로에 exec에 존재하는 yml 파일을 모두 추가
- application-server.yml 설정 주의
  - 현재 ec2에 배포된 상황이 기준이므로 `host`가 `localhost`로 되어있음.
  - 각자 로컬에서 실행할 시에는 주석을 풀고 `host: 3.34.135.178`로 실행하면 됨.

# 외부 서비스
### 외부 서비스 가입에 필요한 정보
- 크게 신경쓰지 않아도 사용 가능

# DB 덤프 파일
### exec에 제공

# 시연 시나리오
### 라이브 시연 흐름도(총 7분)

- 로그인
- 트레이너 등록
- 트레이너 페이지
    - 상세페이지 CSS (미완)
- 방 생성
- 캘린더

- 비로그인 상태의 메인 페이지
    - 비로그인 상태 기능(정렬) 하나 소모
    - 검색
    - 예약하기
    - 로그인 유도
- 로그인 후 예약하던 페이지
- 사이드바로 로그인 확인
- 예약 완료
- 트레이너 즐겨찾기 (+ 마이페이지에서 확인 가능)
- 캘린더

- 라이브 방 둘 다 입장
- 트레이너 종료 (유저 먼저 라이브 퇴장하고, 트레이너 퇴장)
- 라이브 종료 후 운동 일지, 리뷰 쓰기 가능 상태인 거 보여주기
- 유저-리뷰쓰기, 트레이너-일지쓰기
- 트레이너 변경 사항 확인 (낮은 리뷰로 낮아진 점수, 레디스 하나씩으로 변경, 시연 후 변경할 예정)
- 유저 일지 확인
