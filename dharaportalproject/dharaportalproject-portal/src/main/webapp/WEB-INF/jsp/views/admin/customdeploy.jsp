<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<html>
<head>

    <style type="text/css">

        tr:hover {
            background: #F8DBB0;
        }


        th {
            background-color:#F0AF37 ;
            font-size: 100%;
            color: #804000;

        }
        .diplay_table {
            display: table;
            margin: auto;
            text-align: center;
        }

        .table_column th{
            display: table-column;
            color: #804000;
            /*font-family: sans-serif;*/
            height: 10px;
            padding: 15;
        }

        .diplay_table table td {
            border-bottom-color: #000000;
            border-bottom: 1pt solid #000000;
            /*font-family: sans-serif;*/
            font-size: 77%;
            color: #000000;
            padding: 15;
            text-align: center;
        }

        .diplay_table table th {
            /*font-family: "DejaVu Sans Mono";*/
            padding: 15;
        }


    </style>
</head>
<body>

<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<rave:navbar pageTitle="${pagetitle}"/>
<div class="container-fluid adminUI" id="preference_detail">
    <div class="span2">
        <div class="tabs-respond">
            <rave:admin_tabsheader/>
        </div>
    </div>
        <div class="row">
            <h2>${workflowId}</h2>
            <div class="span10">
                <div class="slate clearfix" style="margin-left: 250px">
                    <form:form method="post" action="/admin/workflow/custom">
                        <h3>Custom Properties</h3>

                        <table style="text-align:center; vertical-align:middle;">
                            <tr>
                                <td style="width: 140px">Extending Class</td>
                                <td style="width: 140px">
                                    <select name="extendingAlgo" id="extendingAlgo">
                                        <option value="AbstractSelfDescribingAlgorithm">AbstractSelfDescribingAlgorithm</option>
                                        <option value="ConvexHullAlgorithm">ConvexHullAlgorithm</option>
                                        <option value="CoordinateTransformAlgorithm">CoordinateTransformAlgorithm</option>
                                        <option value="DifferenceAlgorithm">DifferenceAlgorithm</option>
                                        <option value="IntersectionAlgorithm">DouglasPeuckerAlgorithm</option>
                                        <option value="TopologyPreservingSimplificationAlgorithm">TopologyPreservingSimplificationAlgorithm</option>
                                        <option value="ContainsAlgorithm">ContainsAlgorithm</option>
                                        <option value="CrossesAlgorithm">CrossesAlgorithm</option>
                                        <option value="DisjointAlgorithm">DisjointAlgorithm</option>
                                        <option value="DistanceAlgorithm">DistanceAlgorithm</option>
                                        <option value="EqualsAlgorithm">EqualsAlgorithm</option>
                                        <option value="IntersectsAlgorithm">IntersectsAlgorithm</option>
                                        <option value="OverlapsAlgorithm">OverlapsAlgorithm</option>
                                        <option value="WithinAlgorithm">WithinAlgorithm</option>
                                        <option value="TouchesAlgorithm">TouchesAlgorithm</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <br/>
                        <h3>Workflow Inputs</h3>
                        <table id="customDeploymentInput" style="text-align:center; vertical-align:middle;">
                            <thead>
                            <tr>
                                <th style="width: 140px">
                                    Input Name
                                </th>
                                <th style="width: 140px">
                                    Existing Mapping
                                </th>
                                <th style="width: 140px">
                                    New Mapping Type
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${inputNodes}" var="input" varStatus="loop">
                                <tr>
                                    <td>
                                        <c:out value="${input.nodeName}"></c:out>
                                    </td>
                                    <td>
                                        <c:out value="${input.existingMapping}"></c:out>
                                    </td>
                                    <td>
                                        <select style="width: 80px" name="<c:out value="${input.nodeName}"></c:out>" id="<c:out value="${input.nodeName}"></c:out>">
                                            <option value="Integer">Integer</option>
                                            <option value="Boolean">Boolean</option>
                                            <option value="Short">Short</option>
                                            <option value="Double">Double</option>
                                            <option value="Float">Float</option>
                                            <option value="String">String</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br/>
                        <h3>Workflow Outputs</h3>
                        <table id="customDeploymentOutput" style="text-align:center;vertical-align:middle;">
                            <thead>
                            <tr>
                                <th style="width: 140px">
                                    Output Name
                                </th>
                                <th style="width: 140px">
                                    Existing Mapping
                                </th>
                                <th style="width: 140px">
                                    New Mapping Type
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${outputNodes}" var="output" varStatus="loop">
                                <tr>
                                    <td>
                                        <c:out value="${output.nodeName}"></c:out>
                                    </td>
                                    <td>
                                        <c:out value="${output.existingMapping}"></c:out>
                                    </td>
                                    <td>
                                        <select style="width: 80px" name="<c:out value="${output.nodeName}"></c:out>" id="<c:out value="${output.nodeName}"></c:out>">
                                            <option value="Integer">Integer</option>
                                            <option value="Boolean">Boolean</option>
                                            <option value="Short">Short</option>
                                            <option value="Double">Double</option>
                                            <option value="Float">Float</option>
                                            <option value="String">String</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <table style="text-align:right">
                            <tbody>
                            <tr>
                                <td style="width:440px; text-align:right">
                                    <input type="submit"></td>
                            </tr>
                            </tbody>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </div> <!-- end span10 -->

</div> <!-- end row -->

</div> <!-- end container -->


<%--<h3>Experiment List</h3>--%>

<%--<table>--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<td>Experiment Name</td>--%>
<%--<td>Last updated</td>--%>
<%--<td>Author</td>--%>
<%--</tr>--%>
<%--</thead>--%>

<%--<c:forEach items="${message}" var="experiment" varStatus="loop">--%>
<%--<tr>--%>
<%--<td>--%>
<%--<c:out value="${experiment.name}"></c:out>--%>
<%--</td>--%>
<%--<td>--%>
<%--<c:out value="${experiment.updatedTime}"></c:out>--%>
<%--</td>--%>
<%--<td>--%>
<%--<c:out value="${experiment.author}"></c:out>--%>
<%--</td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="assets/js/bootstrap.js"></script>


</body>
</html>