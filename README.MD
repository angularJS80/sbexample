DB컨넥션에 필요한 Server 설정 파일중 context.xml 을 수정합니다. 

<Resource name="jdbc/postgres" auth="Container"
          type="javax.sql.DataSource" driverClassName="net.sf.log4jdbc.DriverSpy"
          url="jdbc:log4jdbc:postgresql://아이피:5432/디비명"
          username="접속아이디" password="페스워드" maxActive="20" maxIdle="10"
maxWait="-1"/>

WAS 에서 디비로 컨넥션하기위한 리눅스의 방화벽등을 설정합니다. 
방화벽은 centos 기본설치 방화벽 부분 참고