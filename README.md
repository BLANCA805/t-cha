# 1. TEAM
### 팀 소개
<table>
  <tr>
    <td align="center"><b>Name</b></td>
    <td align="center"><b>하정호</b></td>
    <td align="center"><b>변정원</b></td>
    <td align="center"><b>윤민영</b></td>
    <td align="center"><b>진익근</b></td>
    <td align="center"><b>이채림</b></td>
    <td align="center"><b>최해미</b></td>
  </tr>
  <tr>
    <td align="center" vertical-align="middle"><b>BLANCA</b></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/241ee0f5-a09d-4a68-a6e1-267f9e96a40d" width="100px;" alt=""/></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/cd3d4ad3-9568-46f0-b7d5-7bd27155b7a9" width="100px;" alt=""/></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/e52d2465-eb3e-4885-9ef1-1f2452c0e2a7" width="100px;" alt=""/><br /></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/076edfd8-4735-4c4b-bdd0-f4f198aeae76" width="100px;" alt=""/><br /></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/9a52270e-c314-41a7-9c1a-f436b6e53040" width="100px;" alt=""/><br /></td>
    <td align="center"><img src="https://github.com/hso8706/Useful/assets/103169947/52e486ee-1129-4262-9ce8-d018be08c23d" width="100px;" alt=""/><br /></td>
  </tr>
  <tr>
    <td align="center"><b>Position</b></td>
    <td align="center"><b>👑 Backend</b></td>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Frontend</b></td>
    <td align="center"><b>Frontend</b></td>
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Backend</b></td>
  </tr>
</table>

### 팀 컨벤션

<details>
<summary>커밋 컨벤션</summary>
<div markdown="1">

# Git Commit Convention
|  | Category | Information | Details |
| --- | --- | --- | --- |
| ✨ | feat | 새로운 기능 추가, 수정 | 모든 기능 구현 |
| 🐛 | fix | 버그 픽스, 에러 핸들링 | 기능 구현 후 실행 시 발견된 에러 핸들링 |
| 💚 | build | 빌드 & 배포 | main에 merge 시 사용 |
| 📦️ | chore | 패키지 외 기타 수정 | 패키지 구조(틀), Resouces, .gradle 등 |
| 💄 | style | 코드 스타일, 포맷팅 변경 | 특히 명칭 변경 시 |
| 🔀 | save | 임시 저장 | 로컬의 변경이 있어야 할 경우 사용 |
| 📝 | test | 테스트 코드 작성 | 테스트를 진행 및 완성에 작성 |
||||

</div>
</details>

<details>
<summary>PR 컨벤션</summary>
<div markdown="1">

# PR Convention

```
## 제목
[ {Section} / {Feature} ] {제목} 
- Section : `Back`, `Front`
- Feature : `User`, `Trainer`, ...
- 제목 : 내용을 암시할 수 있는 제목

## 개요
- 해당 PR의 전반적인 흐름 및 설명

## 세부 내용
- 해당 PR에서 진행한 사항들

## 이슈
- 고민과 질문
- 발생한 문제 및 해결 과정

## 제안
- 팀에게 제안할 사항
```
</div>
</details>

<details>
<summary>branch 전략</summary>
<div markdown="1">

# Git Flow

![image](https://github.com/hso8706/Useful/assets/103169947/0be41e0e-9312-4266-9400-057c1d7e4992)

- main 브랜치에는 프로젝트 마지막에 merge (배포할 때)
- develop 브랜치에 개발한 feature 브랜치를 merge
- feature 브랜치는 각각 기능 하나씩 개발하기
    - 기능별로!!!
    - feature 브랜치명 : `{be,fe}/feat/기능명` ex) `feat/member`, `feat/product`
    - feature 브랜치는 merge 후 삭제
- 배포 후 버그나 오류 발생 시에 main(master) 브랜치에서 hotfix 브랜치를 따와서 수정하기

</div>
</details>

<details>
<summary>Jira 전략</summary>
<div markdown="1">

### 에픽 이슈

- 큰 일 개념
- 상세 이슈를 포함하는 상위 이슈
- 총 프로젝트를 만들기 위해 필요한 일들
- 에픽을 할당하며 분업할 계획

### Task 이슈

- 본인이 할당받은 에픽을 완성하기 위해 세우는 상세 계획

### 스프린트

- 총 프로젝트를 완성하기위해 나누는 주기
- 월 ~ 일
- 우리는 23.07.17이 스프린트 1의 시작일
- 지금 우선 순위 1에 대한 작업은 스프린트2 종료까지를 기준으로 완성할 계획

### 스토리 포인트

- 이슈에 매기는 포인트
- 해당 이슈가 얼마나 걸릴지에 대한 포인트
- 에픽에 스토리 포인트를 정하고, 해당 에픽 스토리 포인트를 배분하여 task 이슈에 스토리 포인트를 지정할 것
- 1시간 = 1포인트
    - 1주 (싸피 상주 시간) = 40시간 = 40포인트
    - 이슈마다 포인트 적절히 분배

</div>
</details>

### 그라운드 룰
<details>
<summary>데일리 스크럼 & 위클리 미팅</summary>
<div markdown="1">

```markdown
**위클리 회의** → 매주 일요일 오후 9시  

**데일리 회의** → 매일 싸피 공식 일정(라이브) 종료 후
```
[회의록](https://www.notion.so/0ea37eaa97f24b61a37161a38626d6b2?pvs=21)

</div>
</details>

<details>
<summary>버그 리포트 & 이슈 공유</summary>
<div markdown="1">

[버그 및 이슈](https://www.notion.so/e18aa97251624c51ab2edd7a9a374635)

</div>
</details>

# 2. SERVICE
### 서비스 개요

| 팀명 | BLANCA |
|:---:|:---:|
| 프로젝트명 | t'cha (읏!차) |
| 프로젝트 진행기간 | 2023.07.10 - 2023.08.18 |
| 서비스 특징 | - 웹 RTC를 이용한 비대면 화상 PT 서비스 <br> - 유저, 트레이너 역할에 자율성이 부여된 서비스 |
| 주요 고객층 | - 비대면 화상 PT를 원하는 사람 <br> - 트레이너로서 PT를 제공하고 싶은 사람 |

### 서비스 기획 배경

<details>
<summary>화상 서비스 선택 배경</summary>
<div markdown="1">
 
    비대면 및 무인 시스템에 대한 수요가 증가하고 있다.
    그 원인은 다양하겠지만 우리가 파악한 원인은 크게 세 가지다.
    첫째는 코로나 이슈, 둘째는 디바이스 성능의 발달, 그리고 마지막으로 기술의 발달이다.
    이 세 가지 이슈로 오프라인 서비스들이 쇠퇴하고, 대다수가 온라인 서비스로 이전할 것을 예상했다.
    
    1. 코로나 이슈
    코로나 팬데믹 초기에는 사람들이 코로나에 걸리지 않기 위해, 혹은 코로나로 인한 격리로 인해 사람들은 물리적으로 `밖`보다는 `안`에 있게 되었다. 그리고 이 기간이 길어짐과 함께 `안`을 추구하던 니즈가 시너지 효과를 발휘해 시장 트렌드 자체를 변화시켰다.
    
    2. 디바이스 성능의 발달
    코로나로 인해 시장 트렌드가 바뀌었다. 예를 들면 기업들은 재택 근무 환경을 고려하게 되었고, 오프라인보다는 온라인 소비자를 붙잡아야 했다.
    이렇게 온라인에 대한 관심이 많아지자 자연스레 온라인 환경을 위한 디바이스(카메라, 통신 등)가 발전했다.

    3. 기술의 발달
    기술의 발달도 디바이스의 발달과 비슷한 이치이다.
    원래도 빠른 속도로 인공지능과 사물 인터넷과 같은 4차 산업 기술이 발달하고 있었는데, 코로나로 인한 변화로 관심이 많아지자 발전 속도에 가속도가 붙었다.

    4. 예시
      - 무인 시스템
        - 각종 무인 매장, 키오스크, 배달 로봇 등
      - 비대면 시스템
        - 엔구(화상 영어), 비대면 은행 서비스 등

</div>
</details>

<details>
<summary>PT 서비스 애플리케이션 선택 배경</summary>
<div markdown="1">

    PT 서비스에 대한 애플리케이션을 선택한 이유는 크게 세 가지다.

    1. 근력 운동 수요의 증가
    지식의 발전에 따라 사회적 흐름도 바뀌기 마련이다.
    이전 시대에는 근력 운동은 보디빌더를 위한 운동, 건강을 위해서는 유산소 운동만이 좋다는 식의 사회적 풍조가 있었다.
    하지만 이는 잘못된 해석으로인한 결과였다는게 밝혀지고, 더불어 현재에는 장수와 노화 방지에 있어서 근력 운동에 대한 중요성이 대두되면서 근력 운동이 주를 이루고 있다.

    2. PT에 대한 필요성
    공부도 학원가서 공부하는 법과 노하우를 배우듯, 운동도 운동하는 법과 노하우를 배우는 것은 당연히 좋다.
    근력 운동에 대한 수요가 증가하면서 PT에 대한 수요도 자연스레 증가하는 추세이다.

    3. 성장 가능성
    운동에 대한 수요, 그리고 PT에 대한 수요가 증가하고 있는 상황이다.
    그런데 PT는 오프라인이 100%라 해도 과언이 아닐 정도로 오프라인 형식의 서비스이다.
    트렌드에 맞게 PT도 온라인 및 무인 시스템이 갖춰질 것이 예측되고있다.
    하지만 아직 이에 대한 시장이 작기 때문에 성장 가능성이 크다고 판단된다.

</div>
</details>

### 서비스 특장점
- 장소와 시간에 큰 제약없이 PT 서비스를 제공받을 수 있음.
- 일정 비용만 지불하면 자유롭게 트레이너로 활동할 수 있음.
- 능력과 인지도가 경쟁력으로 작용하는 시스템으로 인해 합리적인 가격으로 양질의 서비스를 제공받을 수 있음.
- PT 후에도 도움되는 자료를 남길 수 있음

### 주요 기능
- 라이브 PT 기능
  - 실시간 화상 서비스를 통해 PT를 진행할 수 있습니다.
- 트레이너 기능
  - 일반 유저도 손쉽게 트레이너로 등록할 수 있음.
  - 본인이 원하는 시간에 원하는 곳에서 PT를 진행할 수 있습니다.
  - PT 종료 후, 꼼꼼한 일지를 남기며 본인의 능력을 어필할 수 있습니다.
- 나의 운동 일지 보기 기능
  - 유저는 내가 받은 운동 일지 기록을 볼 수 있습니다.
  - 트레이너는 내가 남긴 운동 일지 기록을 볼 수 있습니다.

### 주요 기술
- 웹 RTC
- JWT Authentication
- REST API
- Redis
- Redux

# 3. PROJECT
### Design(피그마)
<details>
<summary>Design(피그마)</summary>
<div markdown="1">

![피그마](https://github.com/hso8706/Useful/assets/103169947/935a03c9-3cf4-445d-999f-0fca6c9d52b3)

</div>
</details>


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

### API 명세서
<details>
<summary>API 명세서</summary>
<div markdown="1">

[api 명세서 노션 링크](https://www.notion.so/API-5e5c6f1b60fe40b99b7660ab2b037ed2)


</div>
</details>

### ERD
<details>
<summary>ERD</summary>
<div markdown="1">

![진짜 찐 마지막 최종 ERD](https://github.com/hso8706/Useful/assets/103169947/648baa7e-970e-46b5-98cf-2024e953aa48)

</div>
</details>


### 서비스 아키텍쳐
<details>
<summary>서비스 아키텍쳐</summary>
<div markdown="1">

![Web App Reference Architecture](https://github.com/hso8706/Useful/assets/103169947/d0ca0c82-fb17-4165-a72f-f5cfc4162f40)

</div>
</details>
