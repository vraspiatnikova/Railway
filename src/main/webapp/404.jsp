<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 18.10.2017
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:import url="/pages/head.jsp"/>

<body class="four-zero-four">
<div class="four-zero-four-container">
    <div class="error-code">404</div>
    <div class="error-message">This page doesn't exist</div>
    <div class="button-place">
        <a href="${pageContext.request.contextPath}/boardByStation" class="btn btn-default btn-lg waves-effect">GO TO HOMEPAGE</a>
    </div>
</div>

<!-- Jquery Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

</body></html>
