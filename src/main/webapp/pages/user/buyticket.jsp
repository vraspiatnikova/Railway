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

        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
            <div class="header">
                <h2>
                    TICKET'S DETAILS
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
                        <th>Price</th>
                    </tr>
                    </thead>
                        <tr>
                            <td>${trip.trainName}</td>
                            <td>${trip.route}</td>
                            <td>${trip.stationFrom}</td>
                            <td>${trip.arrivalDateTime}</td>
                            <td>${trip.stationTo}</td>
                            <td>${trip.depatureDateTime}</td>
                            <td>${trip.price}</td>
                        </tr>
                </table>
            </div>
        </div>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <c:import url="../message.jsp"/>
                    <div class="header">
                        <h2>PASSENGER'S INFO</h2>
                    </div>
                    <div class="body">
                        <form method="POST" novalidate = "novalidate" action="${pageContext.request.contextPath}/addTicket">
                            <input type="hidden" value="${trip.arrivalDateTime}" name="dateTime" >
                            <input type="hidden" value="${board.id}" name="boardId">
                            <input type="hidden" value="${wpFrom.id}" name="wpFromId">
                            <input type="hidden" value="${wpTo.id}" name="wpToId">
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="text" class="form-control" name="firstName" value="${firstName}" required="" aria-required="true">
                                    <label class="form-label">First name</label>
                                </div>
                            </div>
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="text" class="form-control" name="lastName" value="${lastName}" required="" aria-required="true">
                                    <label class="form-label">Last name</label>
                                </div>
                            </div>
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="text" class="form-control" name="birthdate" value="${birthdate}" required="" aria-required="true">
                                    <label class="form-label">Birth date</label>
                                </div>
                                <div class="help-info">DD-MM-YYYY format</div>
                            </div>
                            <div class="form-group form-float">
                                <div class="form-line">
                                    <input type="number" class="form-control" name="passport" value="${passport}" required="" aria-required="true">
                                    <label class="form-label">Passport</label>
                                </div>
                            </div>
                            <button class="btn bg-red waves-effect" type="submit">SUBMIT</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<!-- Jquery Core Js -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script>
<script src="/../../resources/plugins/jquery/jquery.min.js"></script>

<!-- Bootstrap Core Js -->
<script src="/../../resources/plugins/bootstrap/js/bootstrap.js"></script>

<!-- Select Plugin Js -->
<script src="/../../resources/plugins/bootstrap-select/js/bootstrap-select.js"></script>

<!-- Slimscroll Plugin Js -->
<script src="/../../resources/plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

<!-- Jquery Validation Plugin Css -->
<script src="/../../resources/plugins/jquery-validation/jquery.validate.js"></script>

<!-- JQuery Steps Plugin Js -->
<script src="/../../resources/plugins/jquery-steps/jquery.steps.js"></script>

<!-- Sweet Alert Plugin Js -->
<script src="/../../resources/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Waves Effect Plugin Js -->
<script src="/../../resources/plugins/node-waves/waves.js"></script>

<!-- Autosize Plugin Js -->
<script src="/../../resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="/../../resources/plugins/momentjs/moment.js"></script>

<!-- Bootstrap Material Datetime Picker Plugin Js -->
<script src="/../../resources/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

<!-- Custom Js -->
<script src="/../../resources/js/admin.js"></script>
<script src="/../../resources/js/pages/forms/basic-form-elements.js"></script>

<!-- Demo Js -->
<script src="/../../resources/js/demo.js"></script>
</body>
</html>
