<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 17.10.2017
  Time: 6:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chrome">
<c:import url="head.jsp"/>
<body class="login-page ls-closed">
<div class="login-box">
    <div class="logo">
        <a href="javascript:void(0);"><b></b></a>

    </div>
    <div class="card">
        <div class="body">
            <c:url var="loginUrl" value="/login" />
            <form id="sign_in" method="POST" novalidate = "novalidate" action="${loginUrl}" >
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            <p>Invalid username and password.</p>
                        </div>
                    </c:if>
                <div class="msg">Sign in to start your session</div>
                <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">email</i>
                        </span>
                    <div class="form-line">
                        <input type="email" class="form-control" name="email" placeholder="Email Address" required="" aria-required="true" value="${param.email}">
                    </div>
                </div>
                <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="password" placeholder="Password" required="" aria-required="true">
                        <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
                            <div class="error">
                                Email or password you entered is incorrect. Check the entered data.
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-6 p-t-5">
                        <button class="btn btn-block bg-pink waves-effect" type="submit">SIGN IN</button>

                    </div>
                    <div class="col-xs-6 p-t-5">
                        <a href="${pageContext.request.contextPath}/signup">Register Now!</a>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<!-- Jquery Core Js -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Validation Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-validation/jquery.validate.js"></script>

<!-- Custom Js -->
<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/basic-form-elements.js"></script>

</body></html>
