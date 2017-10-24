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
<c:import url="navbar_manager.jsp"/>
<section class="content">
    <div class="container-fluid">

        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="../message.jsp"/>
                    <div class="header">
                        <h2>
                            CREATE NEW ROUTE
                        </h2>
                    </div>
                    <div class="body">
                        <form role="form" id="routeForm">
                            <label for="route_num">Route number</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="route_num" class="form-control" value="" name="routeNumber" required>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="row waypoint-row">
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group">
                                            <select name="stations" class="form-control show-tick" data-live-search="true" required>
                                                <option value="">Select the station</option>
                                                <c:forEach items="${allStations}" var="station">
                                                    <option value="${station.name}">${station.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group form-float">
                                            <div class="form-line">
                                                <input type="text" name="travelTime" value="0" readonly class="form-control" required>
                                                <label class="form-label">Travel time (min)</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group form-float">
                                            <div class="form-line">
                                                <input type="text" name="stopTime" value="0" readonly class="form-control" required>
                                                <label class="form-label">Stop time (min)</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="row waypoint-row">
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group">
                                            <select name="stations" class="form-control show-tick" data-live-search="true" required>
                                                <option value="">Select the station</option>
                                                <c:forEach items="${allStations}" var="station">
                                                    <option value="${station.name}">${station.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group form-float">
                                            <div class="form-line">
                                                <input type="text" name="travelTime" class="form-control" required>
                                                <label class="form-label">Travel time (min)</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                        <div class="form-group form-float">
                                            <div class="form-line">
                                                <input type="text" name="stopTime" class="form-control" required>
                                                <label class="form-label">Stop time (min)</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                <button type="button" class="btn btn-success" name="addWaypoint">Add</button>
                                <button type="button" class="btn btn-danger disabled" name="delWaypoint">Delete</button>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
                                <button type="button" class="btn bg-red waves-effect" name="saveRoute">
                                    <i class="material-icons">save</i>
                                    <span>SAVE</span>
                                </button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<!-- Jquery Core Js -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script>
<script src="../../resources/plugins/jquery/jquery.min.js"></script>

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

<!-- saveroute Js -->
<script src="../../resources/js/saveroute.js"></script>

</body>
</html>
