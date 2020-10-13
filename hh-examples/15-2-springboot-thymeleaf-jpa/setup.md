
start.spring.io
    Gradle Project
    Spring Boot 2.0.4

    Group: com.oreilly
    Artifact: hh

    Dependencies: Web, Thymeleaf, JPA, H2, Devtools

      Web       : Spring MVC and Tomcat
      Thymeleaf : Thymeleaf web template engine
      JPA       : Spring JPA, Hibernate, and Spring Data
      H2        : The H2 embedded database

unzip ~/Downloads/hh.zip

cd springmvc-thymeleaf

build.gradle:
    dependencies {
      // java 9
      runtimeOnly    'javax.xml.bind:jaxb-api:2.+'
      runtimeOnly    'com.sun.xml.bind:jaxb-core:2.+'
      runtimeOnly    'com.sun.xml.bind:jaxb-impl:2.+'
      runtimeOnly    'javax.activation:activation:1.+'
      implementation 'javax.annotation:javax.annotation-api:1.+'
    }

