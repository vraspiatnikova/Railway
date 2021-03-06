<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 01.10.2017
  Time: 13:42
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
        <div class="card">
            <div class="header">
                <h2>
                    SUITABLE TRIPS
                </h2>
            </div>
            <div class="body table-responsive">
                <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                    <thead>
                    <tr>
                        <th>Train name</th>
                        <th>Route</th>
                        <th>Departure station</th>
                        <th>Departure date</th>
                        <th>Arrival station</th>
                        <th>Arrival date</th>
                        <th>Buy ticket</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr>
                        <th>Train name</th>
                        <th>Route</th>
                        <th>Departure station</th>
                        <th>Departure date</th>
                        <th>Arrival station</th>
                        <th>Arrival date</th>
                        <th>Buy ticket</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <c:forEach items="${suitableTrips}" var="trip">
                        <tr>
                            <td>${trip.trainName}</td>
                            <td>${trip.route}</td>
                            <td>${trip.stationFrom}</td>
                            <td>${trip.arrivalDateTime}</td>
                            <td>${trip.stationTo}</td>
                            <td>${trip.depatureDateTime}</td>
                            <td>
                            <sec:authorize var="loggedIn" access="isAuthenticated()"/>
                            <c:choose>
                                <c:when test="${loggedIn}">
                                    <a href="<c:url value='/buyticket/${trip.boardId}/${trip.waypointFromId}/${trip.waypointToId}'/>">Buy ticket</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/login?board=${trip.boardId}&from=${trip.waypointFromId}&to=${trip.waypointToId}'/>">Buy ticket</a>
                                </c:otherwise>
                            </c:choose>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Jquery DataTable Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

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
<script src="${pageContext.request.contextPath}/resources/js/pages/tables/jquery-datatable.js"></script>

<!-- Demo Js -->
<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>
</body></html>

