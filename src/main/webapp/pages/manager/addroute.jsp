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
                            ADD ROUTE
                        </h2>
                    </div>
                    <div class="body">
                        <form method="post" action="${pageContext.request.contextPath}/addTrainRoute">
                            <input type="hidden" value="${train.id}" name="trainId">
                            <label for="train_num">Train number</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="train_num" class="form-control" value="${train.name}" name="trainName" readonly>
                                </div>
                            </div>
                            <label for="capacity">Capacity</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="capacity" class="form-control" value="${train.capacity}" readonly>
                                </div>
                            </div>
                            <label for="route">Select the route</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <select id = "route" class="form-control" name="route" required>
                                        <option value="">Select the route</option>
                                        <c:forEach items="${allRoutes}" var="route">
                                            <option value="${route.number}">${route.number}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row clearfix">
                                <div class="col-sm-4">
                                    <label for="startDate">Select start date</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id="startDate" class="datepicker form-control" placeholder="Please choose a start date" name="startDate" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <label for="endDate">Select end date</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id="endDate" class="datepicker form-control" placeholder="Please choose a end date" name="endDate" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <label for="time">Select time</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id = "time" class="timepicker form-control" placeholder="Please choose a time" name="time" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                <label for="route">Select days of week</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <select id="daysOfWeek" class="form-control show-tick" multiple name="daysOfWeek" required>
                                            <option>Sunday</option>
                                            <option>Monday</option>
                                            <option>Tuesday</option>
                                            <option>Wednesday</option>
                                            <option>Thursday</option>
                                            <option>Friday</option>
                                            <option>Saturday</option>
                                        </select>
                                    </div>
                                </div>
                            <br>
                            <button type="submit" class="btn bg-red waves-effect">Submit</button>
                        </form>
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

<!-- Waves Effect Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/node-waves/waves.js"></script>

<!-- Autosize Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/momentjs/moment.js"></script>

<!-- Bootstrap Material Datetime Picker Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

<!-- Custom Js -->
<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/basic-form-elements.js"></script>

<!-- Demo Js -->
<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>

</body>
</html>
