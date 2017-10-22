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
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="../message.jsp"/>
                    <div class="header">
                        <h2>FIND THE TRIP</h2>
                    </div>
                    <div class="body">
                        <form:form method="POST" action="findTripUser" modelAttribute="searchTripDto">
                            <table class="table">
                                <tr>
                                    <td><form:label path="stationFrom">Select the station of departure :</form:label></td>
                                    <td><form:select class="form-control" path="stationFrom">
                                        <form:option value="0">station of departure</form:option>
                                        <c:forEach items="${allStations}" var="station">
                                            <form:option value="${station.name}">${station.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="stationFrom">Select the station of arrival :</form:label></td>
                                    <td><form:select class="form-control" path="stationTo">
                                        <form:option value="0">station of arrival</form:option>
                                        <c:forEach items="${allStations}" var="station">
                                            <form:option value="${station.name}">${station.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="dateTimeFrom">Select start date</form:label></td>
                                    <td><form:input path="dateTimeFrom" type="text" value="" class="form-control" placeholder="dd-MM-yyyy HH:mm"/></td>
                                </tr>
                                <tr>
                                    <td><form:label path="dateTimeTo">Select end date</form:label></td>
                                    <td><form:input path="dateTimeTo" type="text" value="" class="form-control" placeholder="dd-MM-yyyy HH:mm"/></td>
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

