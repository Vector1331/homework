## 🍟version

- springboot : 2.7.6
- java : 11
-  h2 : 1.4.200

## 🥨도메인 모델과 테이블 설계

연관 관계 매핑 시 복잡함이 많아서 fk를 갖는 엔티티로 설계 
<p>
<img src ="https://user-images.githubusercontent.com/89854207/206654236-5d763ce7-200e-4cf2-8f9d-7ae3fcb84210.jpg" width=40%> 
<img src ="https://user-images.githubusercontent.com/89854207/206831807-3462c017-5b84-4985-a06c-75cc29e0b52c.jpg" width=40%> </p>


---

<br> 

 > 데이터베이스 생성 -> `jdbc:h2:~/productOrder (Embedded)`  <br>
 > 데이터베이스 연결 -> `jdbc:h2:tcp://localhost/~/productOrder (Server)`

