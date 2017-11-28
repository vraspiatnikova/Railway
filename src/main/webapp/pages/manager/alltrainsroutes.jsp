<%--
  Created by IntelliJ IDEA.
  User: Вика
  Date: 01.10.2017
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                            ALL TRIPS
                        </h2>
                    </div>
                    <div class="body table-responsive">
                        <table id="example" class="table table-bordered table-striped table-hover js-basic-example dataTable">
                            <thead>
                            <tr>
                                <th>Train Number</th>
                                <th>Route</th>
                                <th>Date</th>
                                <th>Registered passengers</th>
                                <th>Edit</th>
                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th>Train Number</th>
                                <th>Route</th>
                                <th>Date</th>
                                <th>Registered passengers</th>
                                <th>Edit</th>
                            </tr>
                            </tfoot>
                            <tbody>
                            <c:forEach items="${listBoards}" var="board">
                                <tr>
                                    <td>${board.trainName}</td>
                                    <td>${board.routeWaypoints}</td>
                                    <td>${board.date}</td>
                                    <td>
                                        <a href="<c:url value='/registredpassengers/${board.boardId}'/>">show passengers</a>
                                    </td>
                                    <td>
                                        <a href ="<c:url value='/editTrip/${board.boardId}'/>">
                                            <button type="button" class="btn btn-primary btn-circle waves-effect waves-circle waves-float">
                                                <i class="material-icons">mode_edit</i>
                                            </button>
                                        </a>
                                        <a href ="<c:url value='/deleteBoard/${board.boardId}'/>">
                                            <button type="button" class="btn btn-danger btn-circle waves-effect waves-circle waves-float">
                                                <i class="material-icons">delete_forever</i>
                                            </button>
                                        </a>
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

<!-- Jquery DataTable Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>

<!-- Autosize Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/autosize/autosize.js"></script>

<!-- Moment Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/momentjs/moment.js"></script>

<!-- Bootstrap Material Datetime Picker Plugin Js -->
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>

<!-- Custom Js -->
<script src="${pageContext.request.contextPath}/resources/js/admin.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/forms/basic-form-elements.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/pages/tables/jquery-datatable.js"></script>

<!-- Demo Js -->
<script src="${pageContext.request.contextPath}/resources/js/demo.js"></script>

</body>
</html>
