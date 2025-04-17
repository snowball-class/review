 <div align="center">

### [온라인 강의 수강 사이트 Snowball]
<img src="https://github.com/user-attachments/assets/3a6b5eee-dc1f-4977-b8c8-ea5d97158b07" width="200" height="200"/>

</div>

## 목차
- [프로젝트 소개](#-프로젝트-소개)    
    - [Review Service](#-Review-Service)    
- [주요 기능](#-주요-기능)    
- [프로젝트 구조 요약](#-프로젝트-구조-요약)
- [프로젝트 전체 구조](#-프로젝트-전체-구조)    
- [팀원 및 역할](#-팀원-및-역할)    

<br/>

## 프로젝트 소개
온라인 강의를 들을 수 있는 사이트를 구현한 프로젝트입니다.   
본 프로젝트는 기능별로 서비스가 분리된 MSA 지향 구조로, 각 서비스는 독립적인 개발과 통신이 가능하도록 설계되었습니다.   
서비스 간 통신은 FeignClient를 통해 처리하였습니다.   

> ### Review Service
>     강의 리뷰 등록 및 조회를 담당하는 마이크로서비스입니다.  
>     사용자는 강의에 대한 리뷰를 작성하고, 자신의 리뷰를 조회, 수정, 삭제할 수 있습니다.

<br/>
   
## 주요 기능
- 리뷰 생성, 수정 삭제
- 조건별 리뷰 조회

<br/>

## 기술 스택
| 분류       | 사용 기술                     |
|------------|-------------------------------|
| Language   | Java 17                       |
| Framework  | Spring Boot 3                 |
| DB         | MySQL                        |
| Deploy     | Docker, GCP                  |

<br/>

## 프로젝트 구조 요약

```
review/
  ├── client  # FeignClient를 통한 외부 서비스 호출
  ├── config  # Cors/Feign 설정, 인증 필터 등 
  ├── controller  # REST API 컨트롤러 
  ├── dto  # 요청/응답 DTO
  ├── exception  # 예외 처리 핸들러
  ├── review  # JPA 엔티티
  ├── repository  # 리뷰 저장
  ├── service  # 핵심 비즈니스 로직
  └── Application.java
```

<br/>

## 프로젝트 전체 구조
이 서비스는 Snowball 프로젝트의 일부입니다.
전체 프로젝트는 다음 링크에서 확인하실 수 있습니다.   
🔗[GitHub 전체 레포 보기](https://github.com/snowball-class)

<br/>

## 팀원 및 역할

| 이름     | 역할                |
|----------|-----------------------|
| [김남주](https://github.com/anjoo-k)  | Backend(member, **review**, view)|
| [안병현](https://github.com/bhyunnie)  | Backend(payment, cart, view)|
| [전찬의](https://github.com/orgs/snowball-class/people/jerry0339)  | Infra, Deploy, Backend(lesson, admin, view) |
