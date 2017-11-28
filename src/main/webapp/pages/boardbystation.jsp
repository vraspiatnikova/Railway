<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 16.10.2017
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chrome">
<c:import url="head.jsp"/>

<body class="theme-red ls-closed">

<sec:authorize var="loggedIn" access="isAuthenticated()"/>
<c:choose>
    <c:when test="${loggedIn}">
        <c:import url="user/navbar_user.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="navbar.jsp"/>
    </c:otherwise>
</c:choose>

<section class="content">
    <div class="container-fluid">
        <div class="block-header">
        </div>
        <!-- Select -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>STATION'S TIMETABLE</h2>
                    </div>
                    <div class="body">
                        <form id="form_validation" method="post" novalidate="novalidate" action="${pageContext.request.contextPath}/showBoardByStation">
                            <div class="row clearfix">
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <select class="form-control show-tick" name="stationName" data-live-search="true" required>
                                            <option value="">Select the station</option>
                                            <c:forEach items="${allStations}" var="station">
                                                <option value="${station.name}">${station.name}</option>
                                            </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                    <button type="submit" class="btn bg-red waves-effect">Show timetable</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- #END# Select -->
    </div>
</section>

<!-- Jquery Core Js -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Select Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-select/js/bootstrap-select.js"></script>

<!-- Slimscroll Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

<!-- Jquery Validation Plugin Css -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-validation/jquery.validate.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Autosize Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/momentjs/moment.js"></script>

<!-- Custom Js -->
<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/form-validation.js"></script>

<!-- Demo Js -->
<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>

</body></html>