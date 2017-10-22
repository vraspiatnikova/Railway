<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 01.10.2017
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html class="chrome">
<c:import url="../head.jsp"/>
<body class="theme-red ls-closed">
<c:import url="navbar_manager.jsp"/>
<section class="content">
    <div class="container-fluid">
        <div class="block-header">
            <h2>STATIONS</h2>
        </div>
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="../message.jsp"/>
                    <div class="header">
                        <h2>ADD NEW STATION</h2>
                    </div>
                    <div class="body">
                        <form:form action="addStation" method="post" modelAttribute="station">
                            <table class="table">
                                <tr>
                                    <td><form:label path="name">Station name</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:input path="name" type="text" class="form-control" placeholder="Name" />
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><input class="btn bg-red waves-effect" type="submit" value="Submit"/></td>
                                </tr>
                            </table>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>
                            ALL STATIONS
                        </h2>
                    </div>
                    <div class="body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Station name</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${listStations}" var="station">
                                <tr>
                                    <td>${station.name}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
</body>
</html>
