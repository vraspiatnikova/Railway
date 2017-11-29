<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 16.10.2017
  Time: 12:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="message.jsp"/>
                    <div class="header">
                        <h2>FIND THE TRIP</h2>
                    </div>
                    <div class="body">
                        <form:form id="find-trip" novalidate="novalidate" method="POST" action="findTrip" modelAttribute="searchTripDto">
                            <table class="table">
                                <tr>
                                    <td><form:label path="stationFrom">Select the station of departure :</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:select class="form-control show-tick" path="stationFrom" data-live-search="true" name="stationFrom" >
                                                <form:option value="">station of departure</form:option>
                                                    <c:forEach items="${allStations}" var="station">
                                                    <form:option value="${station.name}">${station.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="stationTo">Select the station of arrival :</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:select class="form-control show-tick" path="stationTo" data-live-search="true" name="stationTo" >
                                                <form:option value="">station of arrival</form:option>
                                                    <c:forEach items="${allStations}" var="station">
                                                    <form:option value="${station.name}">${station.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="dateTimeFrom">Select start date</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:input path="dateTimeFrom" type="text" class="datetimepicker form-control" placeholder="Please choose date & time" name="dateTimeFrom" />
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="dateTimeTo">Select end date</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:input path="dateTimeTo" type="text" class="datetimepicker form-control" placeholder="Please choose date & time" name="dateTimeTo"/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tr>
                                    <td><input class="btn bg-red waves-effect" type="submit" value="Submit"/></td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
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

<!-- Sweet Alert Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Autosize Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/momentjs/moment.js"></script>

<!-- Bootstrap Material Datetime Picker Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

<!-- Custom Js -->
<sec:authorize var="loggedIn" access="isAuthenticated()"/>
<c:choose>
    <c:when test="${loggedIn}">
        <script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
    </c:when>
    <c:otherwise>
        <script src="${pageContext.request.contextPath}/resources/js/test.js"></script>
    </c:otherwise>
</c:choose>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/basic-form-elements.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/form-validation.js"></script>

<!-- Demo Js -->
<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>


</body></html>

