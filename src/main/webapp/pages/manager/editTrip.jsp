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
                    <div class="header">
                        <h2>
                            TRIP'S INFO
                        </h2>
                    </div>
                    <div class="body">
                        <form method="post" action="${pageContext.request.contextPath}/updateBoard/${id}">
                            <label for="date">Date and time</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="date" name="date" class="datetimepicker form-control" value="${dateTime}" required>
                                </div>
                            </div>
                            <label for="train">Train Number</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="train" class="form-control" value="${board.trainName}" readonly>
                                </div>
                            </div>
                            <label for="route">Route</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="route" class="form-control" value="${board.routeWaypoints}" readonly>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="button-demo">
                                    <button type="submit" class="btn btn-primary waves-effect">Edit</button>
                                    <%--<a class="btn btn-danger waves-effect" href ="<c:url value='/deleteBoard/${board.boardId}'/>">Delete</a>--%>
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
