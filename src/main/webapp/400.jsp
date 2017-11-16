<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 18.10.2017
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:import url="/pages/head.jsp"/>

<body class="five-zero-zero">
<div class="five-zero-zero-container">
    <div class="error-code">400</div>
    <div class="error-message">Bad Request</div>
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


