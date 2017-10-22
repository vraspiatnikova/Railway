<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 16.10.2017
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chrome">
<c:import url="../head.jsp"/>

<body class="theme-red ls-closed">
<c:import url="navbar_user.jsp"/>
<section class="content">
    <div class="container-fluid">
        <div class="block-header">
        </div>
        <!-- Select -->
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>STATION'S TIMETABLE</h2>
                    </div>
                    <div class="body">
                        <form method="post" action="${pageContext.request.contextPath}/showBoardByStationUser">
                            <div class="row clearfix">
                                <div class="col-sm-6">
                                    <select class="selectpicker"  name="stationName" required>
                                        <option value="0">Select the station</option>
                                        <c:forEach items="${allStations}" var="station">
                                            <option value="${station.name}">${station.name}</option>
                                        </c:forEach>
                                    </select></div>
                            </div>
                            <div class="col-sm-6">
                                <button type="submit" class="btn bg-red waves-effect">Show timetable</button>
                            </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- #END# Select -->
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

<div class="dtp hidden" id="dtp_mIrLq">
    <div class="dtp-content">
        <div class="dtp-date-view">
            <header class="dtp-header">
                <div class="dtp-actual-day">Lundi</div>
                <div class="dtp-close"><a href="javascript:void(0);"><i class="material-icons">clear</i><!--</div--></a></div></header>
            <div class="dtp-date hidden"><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-month-before"><i class="material-icons">chevron_left</i></a></div>
                <div class="dtp-actual-month p80">MAR</div>
                <div class="right center p10"><a href="javascript:void(0);" class="dtp-select-month-after"><i class="material-icons">chevron_right</i></a></div>
                <div class="clearfix"></div></div><div class="dtp-actual-num">13</div><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-year-before"><i class="material-icons">chevron_left</i></a></div>
                <div class="dtp-actual-year p80">2014</div><div class="right center p10"><a href="javascript:void(0);" class="dtp-select-year-after"><i class="material-icons">chevron_right</i></a></div>
                <div class="clearfix"></div></div></div>
            <div class="dtp-time hidden"><div class="dtp-actual-maxtime">23:55</div></div>
            <div class="dtp-picker"><div class="dtp-picker-calendar"></div>
                <div class="dtp-picker-datetime hidden"><div class="dtp-actual-meridien">
                    <div class="left p20"><a class="dtp-meridien-am" href="javascript:void(0);">AM</a></div>
                    <div class="dtp-actual-time p60"></div>
                    <div class="right p20"><a class="dtp-meridien-pm" href="javascript:void(0);">PM</a></div>
                    <div class="clearfix"></div></div>
                    <%--<div id="dtp-svg-clock"></div>--%>
                </div></div></div>
        <div class="dtp-buttons">
            <button class="dtp-btn-now btn btn-flat hidden btn-sm">Now</button>
            <button class="dtp-btn-clear btn btn-flat btn-sm">Clear</button>
            <button class="dtp-btn-cancel btn btn-flat btn-sm">Cancel</button>
            <button class="dtp-btn-ok btn btn-flat btn-sm">OK</button>
            <div class="clearfix"></div></div></div></div>
<div class="dtp hidden" id="dtp_bgubg"><div class="dtp-content">
    <div class="dtp-date-view"><header class="dtp-header">
        <div class="dtp-actual-day">Lundi</div>
        <div class="dtp-close"><a href="javascript:void(0);"><i class="material-icons">clear</i><!--</div--></a></div></header>
        <div class="dtp-date hidden"><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-month-before"><i class="material-icons">chevron_left</i></a></div>
            <div class="dtp-actual-month p80">MAR</div>
            <div class="right center p10"><a href="javascript:void(0);" class="dtp-select-month-after"><i class="material-icons">chevron_right</i></a></div>
            <div class="clearfix"></div></div><div class="dtp-actual-num">13</div><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-year-before"><i class="material-icons">chevron_left</i></a></div>
            <div class="dtp-actual-year p80">2014</div><div class="right center p10"><a href="javascript:void(0);" class="dtp-select-year-after"><i class="material-icons">chevron_right</i></a></div>
            <div class="clearfix"></div></div></div><div class="dtp-time hidden"><div class="dtp-actual-maxtime">23:55</div></div><div class="dtp-picker">
            <div class="dtp-picker-calendar"></div><div class="dtp-picker-datetime hidden"><div class="dtp-actual-meridien">
            <div class="left p20"><a class="dtp-meridien-am" href="javascript:void(0);">AM</a></div><div class="dtp-actual-time p60"></div>
            <div class="right p20"><a class="dtp-meridien-pm" href="javascript:void(0);">PM</a></div><div class="clearfix"></div></div>
            <%--<div id="dtp-svg-clock"></div>--%>
        </div></div></div><div class="dtp-buttons">
    <button class="dtp-btn-now btn btn-flat hidden btn-sm">Now</button>
    <button class="dtp-btn-clear btn btn-flat btn-sm">Clear</button>
    <button class="dtp-btn-cancel btn btn-flat btn-sm">Cancel</button>
    <button class="dtp-btn-ok btn btn-flat btn-sm">OK</button>
    <div class="clearfix"></div></div></div></div>
<div class="dtp hidden" id="dtp_XzOPE"><div class="dtp-content">
    <div class="dtp-date-view"><header class="dtp-header">
        <div class="dtp-actual-day">Lundi</div><div class="dtp-close"><a href="javascript:void(0);"><i class="material-icons">clear</i><!--</div--></a></div></header>
        <div class="dtp-date hidden"><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-month-before"><i class="material-icons">chevron_left</i></a></div>
            <div class="dtp-actual-month p80">MAR</div><div class="right center p10"><a href="javascript:void(0);" class="dtp-select-month-after"><i class="material-icons">chevron_right</i></a></div>
            <div class="clearfix"></div></div><div class="dtp-actual-num">13</div><div><div class="left center p10"><a href="javascript:void(0);" class="dtp-select-year-before"><i class="material-icons">chevron_left</i></a></div>
            <div class="dtp-actual-year p80">2014</div><div class="right center p10"><a href="javascript:void(0);" class="dtp-select-year-after"><i class="material-icons">chevron_right</i></a></div>
            <div class="clearfix"></div></div></div><div class="dtp-time hidden"><div class="dtp-actual-maxtime">23:55</div></div>
        <div class="dtp-picker"><div class="dtp-picker-calendar"></div><div class="dtp-picker-datetime hidden"><div class="dtp-actual-meridien"><div class="left p20"><a class="dtp-meridien-am" href="javascript:void(0);">AM</a></div>
            <div class="dtp-actual-time p60"></div><div class="right p20"><a class="dtp-meridien-pm" href="javascript:void(0);">PM</a></div><div class="clearfix"></div></div>
            <div id="dtp-svg-clock"></div></div></div></div><div class="dtp-buttons">
    <button class="dtp-btn-now btn btn-flat hidden btn-sm">Now</button>
    <button class="dtp-btn-clear btn btn-flat btn-sm">Clear</button>
    <button class="dtp-btn-cancel btn btn-flat btn-sm">Cancel</button>
    <button class="dtp-btn-ok btn btn-flat btn-sm">OK</button>
    <div class="clearfix"></div></div></div></div>
</body></html>