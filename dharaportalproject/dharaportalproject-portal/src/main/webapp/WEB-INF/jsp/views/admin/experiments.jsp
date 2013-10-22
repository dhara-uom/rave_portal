<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%--<script type="text/javascript">--%>
        <%--function showTable(table_id){--%>
            <%--if(document.getElementById(table_id).style.display == "none")--%>
                <%--document.getElementById(table_id).style.display = "block";--%>
            <%--else--%>
                <%--document.getElementById(table_id).style.display = "none";--%>
        <%--}--%>
    <%--</script>--%>
    <style type="text/css">

        tr:hover {
            background: #F8DBB0;
        }

        th {
            background-color: #F0AF37;
            font-size: 100%;
            color: #804000;

        }


    </style>

</head>
<body>


<div class="row">

    <div class="span10">

        <div class="slate clearfix">

        <h3>Experiment List</h3>

        <table>
            <thead>
            <tr>
                <td>Experiment Name</td>
                <td>Last updated</td>
                <td>Author</td>
                <td>State</td>
            </tr>
            </thead>

            <c:forEach items="${message}" var="experiment" varStatus="loop">
                <tr>
                    <td>
                        <c:out value="${experiment.name}"></c:out>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<c:out value="${experiment.updatedTime}"></c:out>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<c:out value="${experiment.author}"></c:out>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<c:out value="${experiment.state}"></c:out>--%>
                    </td>
                    <%--<td>--%>
                        <%--<c:out value="${experiment.state}"></c:out>--%>
                        <%--<a href="#" onclick="showTable('${user.nodehelperList}')">More...</a>--%>
                        <%--<table class="inner_table" id="${user.nodehelperList}" style="display:none">--%>
                            <%--<tr>--%>
                                <%--<th>Node Type</th>--%>
                                <%--<th>Workflow Instance Node Id</th>--%>
                                <%--<th>Input</th>--%>
                                <%--<th>Output</th>--%>
                            <%--</tr>--%>
                            <%--<c:forEach var="item" items="${user.nodehelperList}">--%>

                                <%--<tr>--%>
                                    <%--<td>  ${item.type} </td>--%>
                                    <%--<td> ${item.workflowInstanceNodeId} </td>--%>
                                    <%--<td> ${item.input} </td>--%>
                                    <%--<td> ${item.output} </td>--%>

                                <%--</tr>--%>

                            <%--</c:forEach>--%>
                        <%--</table>--%>
                    <%--</td>--%>
                </tr>
            </c:forEach>
        </table>

    </div>

</div>
    </div>


</body>
</html>