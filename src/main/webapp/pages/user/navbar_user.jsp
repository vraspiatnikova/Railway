<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 17.10.2017
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Page Loader -->
<div class="page-loader-wrapper" style="display: none;">
    <div class="loader">
        <div class="preloader">
            <div class="spinner-layer pl-red">
                <div class="circle-clipper left">
                    <div class="circle"></div>
                </div>
                <div class="circle-clipper right">
                    <div class="circle"></div>
                </div>
            </div>
        </div>
        <p>Please wait...</p>
    </div>
</div>
<!-- #END# Page Loader -->
<!-- Overlay For Sidebars -->
<div class="overlay" style="display: none;"></div>
<!-- #END# Overlay For Sidebars -->
<!-- Top Bar -->
<nav class="navbar">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
            <a href="javascript:void(0);" class="bars"></a>
            <a class="navbar-brand">RAILWAY</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse">

        </div>
    </div>
</nav>
<!-- #Top Bar -->
<section>
    <!-- Left Sidebar -->
    <aside id="leftsidebar" class="sidebar">
        <!-- Menu -->
        <div class="menu">
            <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 368px;">
                <ul class="list" style="overflow: hidden; width: auto; height: 368px;">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/myInfo" class=" waves-effect waves-block">
                            <i class="material-icons">account_box</i>
                            <span>My info</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/myTickets" class=" waves-effect waves-block">
                            <i class="material-icons">assignment</i>
                            <span>My tickets</span>
                        </a>
                    </li>
                    <%--<li class="active">--%>
                        <%--<a href="${pageContext.request.contextPath}/user_start_page" class="toggled waves-effect waves-block">--%>
                            <%--<i class="material-icons">home</i>--%>
                            <%--<span>Home</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <li>
                        <a href="${pageContext.request.contextPath}/boardByStation" class=" waves-effect waves-block">
                            <i class="material-icons">text_fields</i>
                            <span>Station timetable</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/searchTrip" class=" waves-effect waves-block">
                            <i class="material-icons">layers</i>
                            <span>Buy ticket</span>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/signout" class=" waves-effect waves-block">
                            <i class="material-icons">input</i>
                            <span>Sign out </span>
                        </a>
                    </li>
                </ul>
                <div class="slimScrollBar" style="background: rgba(0, 0, 0, 0.5); width: 4px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 0px; z-index: 99; right: 1px; height: 368px;"></div>
                <div class="slimScrollRail" style="width: 4px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 0px; background: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;"></div>
            </div>
        </div>
        <!-- #Menu -->
    </aside>
    <!-- #END# Left Sidebar -->
</section>
