<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
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

    <div class="span10">

        <div class="slate clearfix">
            <h3>Experiment List</h3>

            <table>
                <thead>
                <tr>
                    <td>Experiment Name</td>
                </tr>
                </thead>

                <c:forEach items="${message}" var="experiment" varStatus="loop">
                    <tr>
                        <td>
                            <c:out value="${experiment.name}"></c:out>
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
