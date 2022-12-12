
## 🌵 개략적인 프로젝트 구조
기본 디렉터리 구조는 Domain, Service, Repository (+exception)으로 구성되어있다. 

### Domain 
- **Item** : 상품, 고유한 상품번호(id)와 상품명(name), 판매가격(price), 재고수량(stock) 정보를 가짐
- **OrderItem** : 주문 아이템, 한 번의 주문에 주문하는 상품, 상품번호(item_id)와 주문번호(order_id), 주문상품금액(order_item_price), 주문수량(count)를 가짐 
- **Order** : 주문, space + ENTER가 입력되었을 때 주문이 완료, 주문번호와, 주문금액(order_price), 배송료(delivery_fee)를 가짐 
- **Pay** : 결제, 주문이 완료되고 해당 건에 대한 결제,  결제번호와 주문번호(order_id_), 지불금액(total_price)를 가짐 



## 🌴 구현 방향

- HomeworkApplication에서 SpringApplication을 run 시킬 때 service 디렉터리의 ApplicationService 빈을 가져와 동작시키는 방식으로 구현

### 새로운 요구사항 정의 

#### multi thread 
- multi thread 환경에서 재고 차감을 비관적 Lock Type을 이용하여 풀어냄 
#### SoldOutException의 시점 
- 실행 예시에서는 상품번호에 space + ENTER가 기입될 경우 soldoutException을 출력하도록 되어있지만, 
상품을 추가할 때 품절 여부 (재고 부족여부)를 판단하는 것이 마땅하다고 생각해 그렇게 풀어냄 

#### 주문 내역 출력 방향 
- 실행 예시에서는 최근에 주문한 상품이 위로 가게 출력되어있으나 
주문 내역에서는 이전에 주문한 상품이 위로 가게 출력하는 것이 맞다고 생각해서 그렇게 풀어냄 




---

<br> 


### h2 연결 
1. github repository의 H2 > bin > h2.bat 실행 (windows) 
2. 데이터베이스 생성과 연결 진행 
 > 데이터베이스 생성 -> `jdbc:h2:~/productOrder (Embedded)`  <br>
 > 데이터베이스 연결 -> `jdbc:h2:tcp://localhost/~/productOrder (Server)`

<br> 

## 🍟version 정보

- springboot : 2.7.6
- java : 11
-  h2 : 1.4.200

## 🥨도메인 모델과 테이블 설계

연관 관계 매핑 시 복잡함이 많아서 fk를 갖는 엔티티로 설계
<p>
<img src ="https://user-images.githubusercontent.com/89854207/206654236-5d763ce7-200e-4cf2-8f9d-7ae3fcb84210.jpg" width=40%> 
<img src ="https://user-images.githubusercontent.com/89854207/206831807-3462c017-5b84-4985-a06c-75cc29e0b52c.jpg" width=40%> </p>

### 초기 시안 
<img src = "https://user-images.githubusercontent.com/89854207/207074711-a43f65c2-940c-4f7b-bdfe-631be77ab3f3.jpg" width = 50%> 

###### <div style="text-align: right"> 2022.12.12 김세현  </div>
