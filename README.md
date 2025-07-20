<p align="center">
  <img width="200px;" src="Frontend/src/images/logo.png" alt="로고"/>
</p>
<h1 align="center">우리동네영양사</h1>

## 프로젝트 개요

>개발 기간: 2024년 7월 ~ 2024년 8월  
리펙토링 기간: 2025년 6월 ~ 2025년 7월

## 배포 주소

>메인 서비스:https://nimn.store

## 팀 소개

| **프로필** | ![종현](https://avatars.githubusercontent.com/u/127838675?v=4) | ![세민](https://avatars.githubusercontent.com/u/113248843?v=4) | ![동균](https://avatars.githubusercontent.com/u/107734276?v=4) | ![동섭](https://avatars.githubusercontent.com/u/105368619?v=40559e2f4-9356-4df9-b373-a06030bc0abb) | ![경락](https://avatars.githubusercontent.com/u/81566899?v=4) |
| :-: | :-: | :-: | :-: | :-: | :-: |
| **이름** | **[이종현(Lead)](https://github.com/2-jjong)** | **[류세민](https://github.com/ryusemin)** | **[한동균](https://github.com/hdg5639)** | **[서동섭](https://github.com/dongsubnambuk)** | **[손경락](https://github.com/ganglike248)** |
| **역할** | Backend | Backend | Backend | Frontend |Frontend |

## 프로젝트 소개

> 우리 동네 영양사는 바쁜 현대인들을 위한 건강한 도시락 정기 구독 서비스이며, AI 영양사가 추천하는 균형잡힌 영양식단을 받아볼 수 있습니다.

### 서비스 상세 소개

- **시간 절약**: 식단 계획, 쇼핑, 요리 시간을 절약하여 바쁜 일상 속에서도 건강한 식사 가능
- **개인 맞춤화**: AI 기반 영양 분석을 통한 개인별 건강 목표에 최적화된 식단 제공

### 타겟 사용자

- **직장인**: 불규칙한 식사와 외식으로 영양 불균형을 겪는 바쁜 직장인
- **1인 가구**: 혼자 요리하기 어려워 간편식에 의존하는 1인 가구
- **다이어터**: 체계적인 식단 관리가 필요한 다이어트 중인 사람들

### 차별화 포인트

- **AI 기술 활용**: 단순 메뉴 제공이 아닌 고루 균형잡힌 영양 식단 최적화
- **편의성**: 완성된 도시락을 자택으로 배송하여 최대 편의성 제공
- **지속가능성**: 개인 피드백을 통한 지속적인 서비스 개선

## 기술 스택

### Frontend
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)

### Backend
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)

### DevOps & Deployment
![AWS](https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white)
![EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)

## 실행 방법

### 요구사항

- Java 17
- Spring: 3.5.0
- Node.js: v23.9.0
- npm: 10.9.2

### 설치 및 실행

1. **Repository 클론**
```bash
git clone https://github.com/2-jjong/LikeLion-12th-Hackathon.git
```

2. **프론트엔드 실행**
```bash
cd Frontend
npm install
npm start
```

## 주요 기능

### 1. 구독 설정 기능

<p align="center">
  <img width="300" src="https://github.com/user-attachments/assets/d5a31e28-e57d-425a-ba57-0f84cdc21cb3" alt="구독설정1"/>
  <img width="300" src="https://github.com/user-attachments/assets/a92e407e-ac25-4f65-8fa5-70a519090e59" alt="구독설정2"/>
  <img width="300" src="https://github.com/user-attachments/assets/490dbda7-ddfc-43f9-a46e-4c3ac38f2b52" alt="구독설정3"/>
</p>

**핵심 기능:**
- **주 단위 구독**: 한 주 단위로 유연한 구독 설정
-  **식사 선택**: 아침, 점심, 저녁 중 원하는 식사만 선택 가능

---

### 2. 생성형AI 맞춤 식단 생성


<div align="center">
  <img width="320" height="200" alt="AI식단1" src="https://github.com/user-attachments/assets/6376af80-010c-4d1a-997b-6c8a9a386e6d" />
  <img width="320" height="400" alt="AI식단2" src="https://github.com/user-attachments/assets/b7c0e9b0-ac87-4666-918d-d75bb4c35e1b" />
  <img width="320" height="200" alt="AI식단3" src="https://github.com/user-attachments/assets/d8234e5a-6cad-4859-b00d-645e65514f3a" />
</div>


**핵심 기능:**
-  **개인 맞춤 추천**: 사용자의 선호도 기반 식단 반영
-  **영양 균형 관리**: 칼로리, 탄수화물, 단백질, 지방 등 영양성분 표시
-  **3가지 가격대**: 4,000원, 5,500원, 7,000원 메뉴로 구성

---

###  3. 결제 및 알림 시스템

<p align="center">
  <img width="450" src="https://github.com/user-attachments/assets/bd74cf0c-4621-4756-bfe2-d39715456700" alt="결제시스템"/>
  <img width="300" src="https://github.com/user-attachments/assets/5043070a-ec48-49fd-b732-80bb0867a512" alt="알림시스템"/>
</p>

**핵심 기능:**
-  **아임포트 결제**: 안전한 PG사 연동 결제 시스템
-  **실시간 알림**: WebSocket 기반으로 오늘의 메뉴, 리뷰 알림

---

###  4. 리뷰 및 평가 시스템

<p align="center">
  <img width="600" src="https://github.com/user-attachments/assets/fefb3fa5-9cb5-41fc-845b-35b62d904b71" alt="리뷰시스템"/>
</p>

**핵심 기능:**
-  **별점 평가**: 5점 만점 별점 시스템
-  **상세 리뷰**: 음식에 대한 상세한 후기 작성
-  **리뷰 통계**: 음식별 평균 평점 및 리뷰 수 표시

---

<p align="center">
  <strong> 건강한 식단, 편리한 생활을 위한 우리동네영양사를 경험해보세요!</strong>
</p>