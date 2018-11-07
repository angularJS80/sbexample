 Back-End 
 0.로컬 구동 환경 설정 	
   tomcat argument 추가
   -Dspring.datasource.username="root" -Dspring.datasource.password="mysqldbuser"
 
 1. 개발환경  
    1) IDE : Eclipse Oxyzen + Sts Plugin
    2) was : Tomcat8.5
    3) database : Oracle 11
    4) framework : spring boot 1.5.7
    
 2. 배포환경
    1) Aws Linux2 Docker - Jenkins
    2) Aws Linux2 Docker - Tomcat
    
    
    
    
 4.Docker yml
 ------------------------------------------------------------------------------------ 
    tomcat 인스턴스 작업내역 
	#docker 설치 
	sudo su -;
	sudo yum update -y;
	sudo yum install -y docker;
	sudo service docker start;
	exit;
	exit;
	다시접속
	sudo usermod -a -G docker $USER;
	sudo service docker restart;
	
	#docker compose 설치
	sudo su -;
	curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose;
	chmod +x /usr/local/bin/docker-compose ;
	exit;
	
	1). 젠킨스 yml 작성
	cd ~;
	mkdir docker;
	cd docker;
	vi docker-compose.yml
	version: '2'
	services:
	  jenkins:
	    image: 'jenkins/jenkins:lts'
	    restart: always
	    user: root
	    hostname: 'localhost'
	    container_name: jenkins
	    ports:
	    - '29080:8080'
	    - '10150:50000'
	    volumes:
	    - ./jenkins_home:/var/jenkins_home
	    
	2)톰켓 
	 #도커 컨테이너 대 호스트 간  경로
		cd ~
		mkdir docker;
		cd  docker;
		mkdir conf;
		cd conf;
		vi  tomcat-users.xml;
	
		<?xml version="1.0" encoding="UTF-8"?>
		<tomcat-users xmlns="http://tomcat.apache.org/xml"
		              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
		              version="1.0">
		
		<role rolename="manager-script"/>
		<role rolename="manager-gui"/>
		<user username="계정명" password="비번" roles="tomcat,manager-gui,manager-script"/>
		</tomcat-users>
	
	
	#톰켓 yml 작성
		cd ~;
		cd docker;
		vi docker-compose.yml
		version: '2'
		services:
		  tomcat:
		    image: 'docker.io/tomcat:8.5'
		    container_name: tomcat
		    ports:
		    - '48080:8080'
		    volumes:
		    - ./conf/tomcat-users.xml:/usr/local/tomcat/conf/tomcat-users.xml
		    environment:
		    - >
		      CATALINA_OPTS=
		        -Dspring.datasource.username="root"
		        -Dspring.datasource.password="mysqldbuser"    

	3) 실행
	cd ~/docker
	docker-compose up -d;
		
------------------------------------------------------------------------------------	