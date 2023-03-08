![image](https://user-images.githubusercontent.com/48465481/223025551-99070f96-0ac5-4863-a409-34b5e0013394.png)

# 예제 - Movie Reservation

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [예제 - 영화예약](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현](#구현)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오

영화예매 커버하기 - https://blog.naver.com/2725kya/222612662750

기능적 요구사항
1. 영화관이 상영할 영화를 등록/수정/삭제한다.
2. 고객이 영화를 선택하여 예약한다.
3. 예약과 동시에 결제가 진행된다.
4. 예약이 완료되면 예약 내역(Message)이 전달된다.
5. 고객이 예약을 취소할 수 있다.
6. 예약 사항이 취소될 경우 취소 내역(Message)이 전달된다.
7. 영화관에 후기(review)를 남길 수 있다.
8. 전체적인 영화에 대한 정보를 한 화면에서 확인 할 수 있다.(viewpage)

비기능적 요구사항
1. 트랜잭션
- 결제가 되지 않은 예약 건은 성립되지 않아야 한다. (Sync 호출)
2. 장애격리
- 영화 등록 및 메시지 전송 기능이 수행되지 않더라도 예약은 365일 24시간 받을 수 있어야 한다 Async (event-driven), Eventual Consistency
- 예약 시스템이 과중되면 사용자를 잠시동안 받지 않고 잠시 후에 하도록 유도한다 Circuit breaker, fallback
3. 성능
- 모든 영화에 대한 정보 및 예약 상태 등을 한번에 확인할 수 있어야 한다 (CQRS)
- 예약의 상태가 바뀔 때마다 메시지로 알림을 줄 수 있어야 한다 (Event driven)


# 체크포인트

- 분석 설계


  - 이벤트스토밍: 
    - 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
    - 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
    - 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?    

  - 서브 도메인, 바운디드 컨텍스트 분리
    - 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른  Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
      - 적어도 3개 이상 서비스 분리
    - 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
    - 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 업무 중요성과  도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
    - Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
    - 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
    - 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
    - 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

  - 헥사고날 아키텍처
    - 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?
    
- 구현
  - [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
  - Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여  장애를 격리시킬 수 있는가?
  - 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key:  각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?

  - 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
  - API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?
- 운영
  - SLA 준수
    - 셀프힐링: Liveness Probe 를 통하여 어떠한 서비스의 health 상태가 지속적으로 저하됨에 따라 어떠한 임계치에서 pod 가 재생되는 것을 증명할 수 있는가?
    - 서킷브레이커, 레이트리밋 등을 통한 장애격리와 성능효율을 높힐 수 있는가?
    - 오토스케일러 (HPA) 를 설정하여 확장적 운영이 가능한가?
    - 모니터링, 앨럿팅: 
  - 무정지 운영 CI/CD (10)
    - Readiness Probe 의 설정과 Rolling update을 통하여 신규 버전이 완전히 서비스를 받을 수 있는 상태일때 신규버전의 서비스로 전환됨을 siege 등으로 증명 
    - Contract Test :  자동화된 경계 테스트를 통하여 구현 오류나 API 계약위반를 미리 차단 가능한가?


# 분석/설계


## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  https://labs.msaez.io/#/storming/b8a9220f305f4adddc58aa6e81e80a04


### 이벤트 도출 완료
![부적격이벤트탈락](https://user-images.githubusercontent.com/48465481/223027186-1feeb7fa-7a31-4e67-9684-c3b4fcd75e1a.JPG)

### 최종모델
![최종모델](https://user-images.githubusercontent.com/48465481/223596903-6b6e97e7-e6d5-4d9e-8e4f-8bd57b46465b.JPG)



# 구현

## 1. Saga (Pub-Sub)

 - 각 서비스간 Pub-Sub을 구현하였다.
 - reservation 에서 reservationCreated 이벤트가 발생 할 경우, payment 서비스로 전달된다.
```
    @PostPersist
    public void onPostPersist(){


        ReservationCreated reservationCreated = new ReservationCreated(this);
        reservationCreated.publishAfterCommit();
``` 
```
    public static void approvePayment(ReservationCreated reservationCreated){

        Payment payment = new Payment();
        payment.setRsvId(reservationCreated.getRsvId());
        payment.setMovieId(reservationCreated.getMovieId());
        repository().save(payment);
```
 - payment에서 paymentCancelled 이벤트가 발생 할 경우, reservation 서비스로 전달되어 예약취소가 이루어진다
```
    @PostUpdate
    public void onPostUpdate(){


        PaymentCancelled paymentCancelled = new PaymentCancelled(this);
        paymentCancelled.publishAfterCommit();

    }
```
```
    public static void confirmCancel(PaymentCancelled paymentCancelled){

        repository().findById(paymentCancelled.getRsvId()).ifPresent(reservation->{
            
            repository().delete(reservation);

         });
```

## 2. CQRS : 명령과 쿼리 분리
 - Movie 서비스와 Reservation 서비스의 상세 모델을 참조하여 Query 모델(Materialized View)을 설계하였다.

![CQRS1](https://user-images.githubusercontent.com/48465481/223595842-91f308d3-0863-4092-a87e-96e98fd1a51c.JPG)

 - Read Model CRUD 상세설계

![CQRS2](https://user-images.githubusercontent.com/48465481/223595972-8f65094e-4b3f-41ab-9dcb-eea700a8d1b6.JPG)

![CQRS3](https://user-images.githubusercontent.com/48465481/223596025-74ec4b8b-c60e-4430-bff4-116116790ded.JPG)

![CQRS4](https://user-images.githubusercontent.com/48465481/223596114-7454b81b-5801-462f-a683-404c036469f7.JPG)


## 3. Compensation & Correlation 
 - ReviewCreated라는 이벤트가 발행되면 Movie의 reviewCnt가 증가한다. ReviewDeleted 이벤트가 발생 되면 reviewCnt가 다시 원복되는 Compensation이 수행된다. Review에 대해서는 해당 건의 id를 상관관계 키 (Correlation Key)로 카운트를 감소하는 방법으로 원복이 이루어진다.

 - review 생성 시의 코드
```
    @PostPersist
    public void onPostPersist(){


        ReviewCreated reviewCreated = new ReviewCreated(this);
        reviewCreated.publishAfterCommit();

    }
```
```
    public static void updateReviewCnt(ReviewCreated reviewCreated){

        repository().findById(reviewCreated.getMovieId()).ifPresent(movie->{
            
            movie.setReviewCnt(movie.getReviewCnt() + 1);
            repository().save(movie);

         });
        
    }
```
 - 리뷰 삭제 시 코드(Correlation Key를 통해 원복 수행)
```
    @PreRemove
    public void onPreRemove(){


        ReviewDeleted reviewDeleted = new ReviewDeleted(this);
        reviewDeleted.publishAfterCommit();

    }
```
```
    public static void updateReviewCnt(ReviewDeleted reviewDeleted){

        repository().findById(reviewDeleted.getMovieId()).ifPresent(movie->{
            
            movie.setReviewCnt(movie.getReviewCnt() - 1);
            repository().save(movie);

         });
```

- 리뷰등록 전 영화1 (reviewCnt : 0)

![최초영화1](https://user-images.githubusercontent.com/48465481/223597974-0449add9-e010-490f-9479-66319c6f99cc.JPG)
- 리뷰1등록

![리뷰1등록](https://user-images.githubusercontent.com/48465481/223598023-10c50e9f-48b1-437f-9187-8e15721aa06a.JPG)
- 리뷰2등록

![리뷰2등록](https://user-images.githubusercontent.com/48465481/223598059-b585217b-bb6f-4a9e-8cba-fba7baa43841.JPG)
- 리뷰3등록

![리뷰3등록](https://user-images.githubusercontent.com/48465481/223598102-fbf07be1-329a-4958-b0f9-61ffaed89290.JPG)
- 리뷰등록 후 영화1 (reviewCnt : 3)

![리뷰등록후영화1](https://user-images.githubusercontent.com/48465481/223598150-80f8e3b2-5189-43ec-9a09-c8442682dfba.JPG)
- 리뷰삭제 및 영화1 (reviewCnt : 2)

![리뷰3삭제후영화1](https://user-images.githubusercontent.com/48465481/223598182-4a1b7fe6-25c3-40e4-ac4f-70f0d75b7241.JPG)

## 6. Gateway / Ingress
 - API Gateway를 사용하여 마이크로 서비스들의 엔드포인트를 단일화하였다.
```
kubectl get svc
```
![kubectl_get_svc](https://user-images.githubusercontent.com/48465481/223598699-683600b7-f859-44e0-a87e-bddc63948076.JPG)

## 7. Deploy / Pipeline
 Docker Image 생성하여 Deploy 실행
 
 - 생성된 Docker Image
 ![dockerimages](https://user-images.githubusercontent.com/48465481/223599311-8e9c55d7-acbc-48dd-bc5d-dd50e7590167.JPG)
 
 - Deployment.yaml 수정 (Image지정)
![deply1](https://user-images.githubusercontent.com/100065651/223593654-64633de1-e68c-4a03-a3cd-f9108b4a4af7.png)

- 배포 결과

![deply2](https://user-images.githubusercontent.com/100065651/223593669-b7ab83c5-016a-4a8c-9d80-f944a4dbf92d.png)

## 8. Autoscale (HPA)
 
 - 요청이 적을때는 최소한의 Pod를 유지한 후에 요청이 많아질 경우 Pod를 확장하여 요청을 처리할 수 있다.
 - 서비스에 대한 replica 를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 50프로를 넘어서면 replica 를 3개까지 늘려준다:
```
kubectl autoscale deployment message --cpu-percent=50 --min=1 --max=3
kubectl autoscale deployment movie --cpu-percent=50 --min=1 --max=3
kubectl autoscale deployment payment --cpu-percent=50 --min=1 --max=3
kubectl autoscale deployment reservation --cpu-percent=50 --min=1 --max=3
kubectl autoscale deployment review --cpu-percent=50 --min=1 --max=3
kubectl autoscale deployment viewpage --cpu-percent=50 --min=1 --max=3
```
 - 워크로드를 걸어준다.
```
kubectl exec -it siege -- /bin/bash
siege -c100 -t60S -r10 -v --content-type "application/json" 'http://movie:8080/movies POST {"desc": "영화들"}'
```

디플로이 수정
![디플로이수정](https://user-images.githubusercontent.com/48465481/223614226-a6a26e06-3243-44ec-b56f-b70d3b7badb2.JPG)
기준
![부하전](https://user-images.githubusercontent.com/48465481/223614303-da358639-94f0-407b-b38a-79ec33aad099.JPG)

 - 어느정도 시간이 지나면 임계치에 도달하여 스케일 아웃 되는 것이 확인된다.(부하 후 movie pod 추가 생성)
![부하후(movie추가생성)](https://user-images.githubusercontent.com/48465481/223614404-d133ab81-ef6a-4942-b85a-835f28c6526f.JPG)

## 9. Zero-downtime deploy (Readiness probe)

 - ReadinessProbe 가 없는 상태에서 배포 진행 했을때, Availablity를 확인해 보면 100% 미만으로 떨어진 것이 확인된다.

디플로이_변경전

![디폴리이_변경전](https://user-images.githubusercontent.com/48465481/223614902-d9fb17ac-d8b8-4137-b17b-d87f0bc4eb0d.JPG)

디플로이_변경후

![디폴리이_변경후](https://user-images.githubusercontent.com/48465481/223614953-5fd746a2-65c4-4724-9d33-3ab479570a51.JPG)

부하수행

![부하수행](https://user-images.githubusercontent.com/48465481/223615052-96132988-41e1-488d-86b2-696fbc1b4008.JPG)

디플로이변경

![디플로이변경](https://user-images.githubusercontent.com/48465481/223615105-f846e122-dc44-43ce-9b91-16a45c5c2d99.JPG)

Pod변화

![디블로이변경_get_pod](https://user-images.githubusercontent.com/48465481/223615221-8c71adcc-85a1-4462-80e2-414302363696.JPG)
 - ReadinessProbe를 설정 후 배포하면 없는 Availablity를 확인해 보면 100%로, 무정지 배포가 수행 된 것이 확인된다.

![테스트결과](https://user-images.githubusercontent.com/48465481/223615255-6c6ea619-9ac7-4e0e-9066-01c47dae097b.JPG)


## 10. Persistence Volume/ConfigMap/Secret
 - 파일시스템 (볼륨) 연결과 데이터베이스 설정
 - : 라운지에서 보고 따라 하던가 포기하던가;
## 11. Self-healing (liveness probe)
 - 셀프힐링 실습 (livenessProbe)

 - 디플로이
![셀프힐링_디플로이](https://user-images.githubusercontent.com/48465481/223627620-fcf958f2-67bf-403c-8ea1-c58c74de4f70.JPG)

- 리스타트 확인
![셀프힐링](https://user-images.githubusercontent.com/48465481/223627692-33900933-36e2-48f2-af82-c1dce208a23b.JPG)

## 12. Apply Service Mesh
 - kiali
![kiali](https://user-images.githubusercontent.com/48465481/223629484-67600317-556b-4801-80fa-7cb3bb033fed.JPG)

## 13. Loggregation / Monitoring
 - 마이크로서비스 통합 로깅 with EFK stack
 - MSA 모니터링 with installing Grafana
 - : EFK등 초반에 설치 후 테스트 하면서 그래프 변화되는거 캡쳐

## 여기까지

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트와 파이선으로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)
