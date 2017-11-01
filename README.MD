 - SiliconVally Study 를 위한Api 설정을 위해 Try 해본 소스입니다. -
 1. 구현환경 
    1) OS Windows
    1) IDE Eclipse Oxyzen + Sts Plugin
    2) Tomcat8.0
    3) PostGreSql Client (PGAdmin4.0)
    
 2. 배포환경
    1) Ncloud micro Server 
    2) http Apache2
    3) iptables
    4) postgresql    
   
 3. 추가적으로 수정해야 되는 사항입니다. 
    1) DB컨넥션에 필요한 Server 설정 파일중 context.xml <Context>와 </Context> 사이에 추가합니다. 

 <Resource name="jdbc/postgres" auth="Container"
          type="javax.sql.DataSource" driverClassName="net.sf.log4jdbc.DriverSpy"
          url="jdbc:log4jdbc:postgresql://아이피:5432/디비명"
          username="접속아이디" password="페스워드" maxActive="20" maxIdle="10"
 maxWait="-1"/>

 WAS 에서 디비로 컨넥션하기위한 리눅스의 방화벽등을 설정합니다. 
 방화벽은 centos 기본설치 방화벽 부분 참고