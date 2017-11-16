<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 17.10.2017
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chrome">
<c:import url="head.jsp"/>

<body class="signup-page ls-closed">
<div class="signup-box">

    <div class="card">
        <div class="body">
            <form id="sign_up" method="POST" novalidate = "novalidate" action="${pageContext.request.contextPath}/signup">
                <c:if test="${confirmError == null && emailError == null && passwordError == null}">
                    <c:import url="message.jsp"/>
                </c:if>
                <div class="msg">Register a new user</div>

                <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">email</i>
                        </span>
                    <div class="form-line">
                        <input type="email" class="form-control" name="email" value="${email}" placeholder="Email Address" required="" aria-required="true">
                        <c:if test="${emailError != null}"><span class="error">${emailError}</span></c:if>
                    </div>
                </div>
                <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="password" minlength="4" maxlength="20" placeholder="Password" required="" aria-required="true">
                        <c:if test="${passwordError != null}"><span class="error">${passwordError}</span></c:if>
                    </div>
                </div>
                <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                    <div class="form-line">
                        <input type="password" class="form-control" name="confirm" placeholder="Confirm Password" required="" aria-required="true">
                        <c:if test="${confirmError != null}"><span class="error">${confirmError}</span></c:if>
                    </div>
                </div>


                <button class="btn btn-block btn-lg bg-pink waves-effect" type="submit">SIGN UP</button>

                <div class="m-t-25 m-b--5 align-center">
                    <a href="${pageContext.request.contextPath}/login">Have you already been registered?</a>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Jquery Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Validation Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-validation/jquery.validate.js"></script>

<!-- Custom Js -->
<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/examples/sign-up.js"></script>

</body></html>
