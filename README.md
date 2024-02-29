### 프로젝트 구조
프로젝트는 총 4개로 이루어진다. 이중 실행 가능한 애플리케이션은 3개이다.

- vacation-core-module   
휴가 서비스에 필요한 도메인, 도메인 서비스, 인프라를 구성한다.
- vacation-api-module   
휴가 서비스에 필요한 API를 구현한다. API는 크게 두 종류로 구분한다.   
*public* : 외부(서비스 사용자)에서 사용할 수 있다.   
*private* : 내부(관리자)에서 사용할 수 있다.
- vacation-messaging-module   
휴가 서비스와 연동된 3rd-party 서버와의 메시지 통신을 구현한다.
- vacation-batch-module
휴가 서비스에 필요한 배치를 구현한다.

### YML 설정
서비스가 정상적으로 작동하기 위해서는 총 4개의 WAS 서버를 구성해야 한다. 이 프로젝트를 통해서는 3개의 WAS를 구동할 수 있다.   
나머지 WAS는 [confirm-server](https://github.com/JxxHxxx/confirm-server) 프로젝트를 참고하면 된다.

Mysql 환경에서 안정적이며 다른 DB를 사용하기 위해서는 다음과 같은 조건이 필요하다.
- json 타입을 지원하는 DB여야 한다.

-  `messaing.yml` messaging.produce.select-sql 값 지정
```
produce 에서 consumer 로 보내기 위한 엔티티 한 줄을 읽은 SELECT Query를 지정한다. 이 값은 사용하는 DB 마다 차이가 있다.
아래는 예시다.

```

*MYSQL* 
```
SELECT * FROM JXX_MESSAGE_Q
WHERE MESSAGE_PROCESS_STATUS = 'SENT'
LIMIT 1;
```
*ORACLE*
```
SELECT * FROM JXX_MESSAGE_Q 
WHERE MESSAGE_PROCESS_STATUS = 'SENT' 
FETCH FIRST 1 ROW ONLY;
```

다음은 프로젝트 실행을 위한 `application.yml` 설정에 필요한 값들이다.

*vacation-api yml*
```
spring:
  datasource:
    url: 
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: 
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

org:
  hibernate:
    envers:
      audit_table_suffix:

server:
  port: 8080
```

*vacation-messaging yml*
```
spring:
  datasource:
    url: 
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

org:
  hibernate:
    envers:
      audit_table_suffix:

poller:
  interval: 50

server:
  port: 8090

3rd-party:
  datasource:
    approval:
      url: 
      username: 
      password: 
      driver-class-name: com.mysql.cj.jdbc.Driver

messaging:
  produce:
    select-sql:
```

### 휴가 신청 API 헬퍼
messaging 프로젝트에서 많은 양의 큐를 처리하는걸 보고 싶을 때, 사용하면 좋다.

사전 준비물 : JXX_MEMBER_LEAVE_MASTER, JXX_ORGANIZATION_MASTER 레코드 , POSTMAN

POSTMAN 간단 설명

아래 Pre-request Script 를 통해 요청에 보낼 변수를 만들 수 있다.   
![image](https://github.com/JxxHxxx/huga-gaza/assets/87173870/8c144791-02aa-4e3e-9227-303b4788ab76)

만든 변수를 활용해 API 를 호출 할 때 마다 requestBody 에 변경된 값이 들어가게 할 수 있다. 추가로 API 콜렉션 기능을 사용하면 지정한 만큼 API를 호출 할 수 있다.   
![image](https://github.com/JxxHxxx/huga-gaza/assets/87173870/3d61a726-0331-4c47-8d50-b4346a4b27c3)

##### 함수 설명

휴가 신청 시 요구되는 사용자ID 를 무작위하게 하기 위한 함수

```
const randomRequestId = function () {
    const minU = 1; // 현재 존재하는 U 사번의 최소 값, 참고로 U사번 체계는 U00001 부터 1씩 증가하는 규칙을 가진다. 
    const maxU = 132; // 현재 존재하는 U 사번의 최대 값
    const minSPY = 1; // 현재 SPY 사번의 최소 값, SPY 사번의 체계도 U사번과 유사하게 1씩 증가한다.
    const maxSPY = 165; // 현재 SPY 사번의 최대 값
    const randomIdU = Math.floor(Math.random() * (maxU - minU + 1)) + minU;
    const randomIdSPY = Math.floor(Math.random() * (maxSPY - minSPY + 1)) + minSPY;
    // SPY, U 사번 랜덤하게 들어가기 위한 작업
    const randomId = Math.random() < 0.5 ? randomIdU : randomIdSPY;
    const prefix = Math.random() < 0.5 ? "U" : "SPY";
    return prefix + randomId.toString().padStart(5, '0');
} 

pm.environment.set("requesterId", randomRequestId());
```

휴가 시작일, 종료일을 설정하기 위한 함수

```
const randomDate = () => {
    const date = new Date();

    const adjustValue = Math.floor(Math.random() * 60); // 휴가 시작일을 무작위하게 하기 위한 조정 값
    date.setDate(date.getDate() + adjustValue);

    const copyNowYear = date.getFullYear();
    const copyNowDay = String(date.getDate()).padStart(2, '0');
    const copyNowDate = String(date.getMonth() + 1).padStart(2, '0');

    const startDate = `${copyNowYear}-${copyNowDate}-${copyNowDay}T00:00:00`;

    const interval = Math.floor(Math.random() * 5); // 휴가 종료일을 무작위하게 하기 위한 조정 값
    date.setDate(date.getDate() + interval);
    const year = date.getFullYear();
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');

    const endDate = `${year}-${month}-${day}T00:00:00`;
    return [startDate, endDate];
}

const [startDate, endDate] =  randomDate();
pm.environment.set("startDate", startDate);
pm.environment.set("endDate", endDate);

```
