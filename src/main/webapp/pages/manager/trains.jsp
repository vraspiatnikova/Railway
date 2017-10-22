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
<html class="chrome">
<c:import url="../head.jsp"/>

<body class="theme-red ls-closed">
<c:import url="navbar_manager.jsp"/>
<section class="content">
    <div class="container-fluid">
        <div class="block-header">
            <h2>TRAINS</h2>
        </div>

        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="../message.jsp"/>
                    <div class="header">
                        <h2>ADD NEW TRAIN</h2>
                    </div>
                    <div class="body">
                        <form:form method="POST" action="addTrain" modelAttribute="train" novalidate = "novalidate">
                            <table class="table">
                                <tr>
                                    <td><form:label path="name">Name</form:label></td>
                                    <td>
                                        <div class="form-group">
                                            <div class="form-line">
                                                <form:input path="name" type="text" value="" class="form-control" required="" aria-required="true" aria-invalid="true" placeholder="Train name"/>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><form:label path="capacity">Number of seats</form:label></td>
                                <td>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <form:input path="capacity" type="text" value = "18" min="18" max="1080" required="" aria-required="true" class="form-control" placeholder="Number of seats"/>
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
                            ALL TRAINS
                        </h2>
                    </div>
                    <div class="body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Capacity</th>
                                <th>Assign the route</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listTrains}" var="train">
                                    <tr>
                                        <td>${train.name}</td>
                                        <td>${train.capacity}</td>
                                        <td>
                                            <a href="<c:url value='/addroute/${train.id}'/>">Add route</a>
                                        </td>
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

<!-- Jquery Validation Plugin Css -->
<script src="../../resources/plugins/jquery-validation/jquery.validate.js"></script>

<!-- JQuery Steps Plugin Js -->
<script src="../../resources/plugins/jquery-steps/jquery.steps.js"></script>

<!-- Sweet Alert Plugin Js -->
<script src="../../resources/plugins/sweetalert/sweetalert.min.js"></script>

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

