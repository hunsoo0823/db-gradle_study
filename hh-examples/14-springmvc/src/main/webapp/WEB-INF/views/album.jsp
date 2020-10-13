<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%--
<%@ page session="false" %>
--%>

<html>

  <head>
    <title>Album Page</title>

    <style type="text/css">
      .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
      .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
      .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
      .tg .tg-4eph{background-color:#f9f9f9}
    </style>

  </head>

  <body>
    <h1>
      Add a Album
    </h1>

    <c:url var="addAction" value="/album/add" ></c:url>

    <%-- SpringMVC version3:
    <form:form action="${addAction}" commandName="album">
    --%>
    <%-- SpringMVC version5: --%>
    <form:form action="${addAction}" modelAttribute="album">
      <table>
        <c:if test="${!empty album.title}">
          <tr>
            <td>
              <form:label path="id">
                <spring:message text="ID"/>
                </form:label>
            </td>
            <td>
              <form:input path="id" readonly="true" size="8" disabled="true" />
              <form:hidden path="id" />
            </td>
          </tr>
        </c:if>

        <tr>
          <td>
            <form:label path="title">
              <spring:message text="Title"/>
            </form:label>
          </td>
          <td>
            <form:input path="title" />
          </td>
        </tr>
        <tr>
          <td>
            <form:label path="numDiscs">
              <spring:message text="NumDiscs"/>
            </form:label>
          </td>
          <td>
            <form:input path="numDiscs" />
          </td>
        </tr>
        <%--
        <tr>
          <td>
            <form:label path="playTime">
              <spring:message text="PlayTime"/>
            </form:label>
          </td>
          <td>
            <form:input path="playTime" />
          </td>
        </tr>
        --%>
        <%--
        <c:if test="${!empty album.title}">
          <tr>
            <td>
              <form:label path="added">
                <spring:message text="Added"/>
              </form:label>
            </td>
            <td>
              <form:input path="added" readonly="true" disabled="true"  />
              <form:hidden path="added" />
            </td>
          </tr>
        </c:if>
        --%>
        <tr>
          <td colspan="2">
            <c:if test="${!empty album.title}">
              <input type="submit"
              value="<spring:message text="Edit Album"/>" />
            </c:if>
            <c:if test="${empty album.title}">
              <input type="submit"
              value="<spring:message text="Add Album"/>" />
            </c:if>
          </td>
        </tr>
      </table>
    </form:form>

    <br>
    <h3>Albums List</h3>

    <c:if test="${!empty listAlbums}">
      <table class="tg">
        <tr>
          <th width="80">Album ID</th>
          <th width="120">Album Title</th>
          <th width="120">Album Discs</th>
          <%--
          <th width="120">Album Added</th>
          --%>
          <th width="60">Edit</th>
          <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listAlbums}" var="album">
          <tr>
            <td>${album.id}</td>
            <td>${album.title}</td>
            <td>${album.numDiscs}</td>
            <%--
            <td>${album.added}</td>
            --%>
            <td><a href="<c:url value='/edit/${album.id}' />" >Edit</a></td>
            <td><a href="<c:url value='/remove/${album.id}' />" >Delete</a></td>
          </tr>
        </c:forEach>
      </table>
    </c:if>

  </body>
</html>
