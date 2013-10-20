<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
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
                </tr>
                </thead>

                <c:forEach items="${message}" var="experiment" varStatus="loop">
                    <tr>
                        <td>
                            <c:out value="${experiment.name}"></c:out>
                        </td>
                        <td>
                            <c:out value="${experiment.updatedTime}"></c:out>
                        </td>
                        <td>
                            <c:out value="${experiment.author}"></c:out>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>

    </div>


</div>
</div>


</body>
</html>
