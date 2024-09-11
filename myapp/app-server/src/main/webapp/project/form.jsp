<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 등록</h1>
<form action='/project/add' method="post">
    프로젝트명: <input name='title' type='text'><br>
    설명: <textarea name='description'></textarea><br>
    기간: <input name='startDate' type='date'> ~
    <input name='endDate' type='date'><br>
    팀원:<br>
    <ul>
        <c:forEach items="${users}" var="user">
            <li><input name='member' value='${user.no}' type='checkbox'> ${user.name}</li>
        </c:forEach>
    </ul>
    <input type='submit' value='등록'>
</form>

</body>
</html>
