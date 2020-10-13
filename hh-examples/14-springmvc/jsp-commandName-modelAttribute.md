# Command object does not work anymore
  https://jira.spring.io/browse/SPR-16037


Juergen Hoeller added a comment - 03/Oct/17 1:15 PM

  It looks like this is a consequence of our deprecation cleanup:
  The "commandName" property has been deprecated since 4.3
  (which is unfortunately hard to notice in a JSP) and removed in 5.0.

    <%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
    <form:form action="" commandName="commandObject" method="get">
      <form:input path="value"/>
    </form:form>

  Instead, you should be using "modelAttribute"
  which has been around for many years already.

    <%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
    <form:form action="" modelAttribute="commandObject" method="get">
      <form:input path="value"/>
    </form:form>

  I'll add a corresponding note to
  https://github.com/spring-projects/spring-framework/wiki/Migrating-to-Spring-Framework-5.x
