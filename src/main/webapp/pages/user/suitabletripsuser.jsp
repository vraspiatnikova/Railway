<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 01.10.2017
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chrome">
<c:import url="../head.jsp"/>

<body class="theme-red ls-closed">

<c:import url="navbar_user.jsp"/>

<section class="content">
    <div class="container-fluid">
        <div class="card">
            <div class="header">
                <h2>
                    SUITABLE TRIPS
                </h2>
            </div>
            <div class="body table-responsive">
                <table class="table">
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
                    <c:forEach items="${suitableTrips}" var="trip">
                        <tr>
                            <td>${trip.trainName}</td>
                            <td>${trip.route}</td>
                            <td>${trip.stationFrom}</td>
                            <td>${trip.arrivalDateTime}</td>
                            <td>${trip.stationTo}</td>
                            <td>${trip.depatureDateTime}</td>
                            <td>
                                <a href="<c:url value='/buyticket/${trip.boardId}/${trip.waypointFromId}/${trip.waypointToId}'/>">Buy ticket</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</section>

<!-- Jquery Core Js -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script><script src="../../resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="../../resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Select Plugin Js -->
<script src="../../resources/plugins/bootstrap-select/js/bootstrap-select.js"></script>

<!-- Slimscroll Plugin Js -->
<script src="../../resources/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="../../resources/plugins/node-waves/waves.js"></script>

<!-- Autosize Plugin Js -->
<script src="../../resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="../../resources/plugins/momentjs/moment.js"></script>

<!-- Bootstrap Material Datetime Picker Plugin Js -->
<script src="../../resources/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

<!-- Custom Js -->
<script src="../../resources/js/admin.js"></script>
<script src="../../resources/js/pages/forms/basic-form-elements.js"></script>

<!-- Demo Js -->
<script src="../../resources/js/demo.js"></script>
</body></html>



