<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>

<rave:navbar pageTitle="${pagetitle}"/>
<script>
    function showPos(event, table_id) {
        var el, x, y;

        el = document.getElementById(table_id);
        if (window.event) {
            x = window.event.clientX + document.documentElement.scrollLeft
                    + document.body.scrollLeft;
            y = window.event.clientY + document.documentElement.scrollTop +
                    + document.body.scrollTop;
        }
        else {
            x = event.clientX + window.scrollX;
            y = event.clientY + window.scrollY;
        }
        x -= 2; y -= 2;
        y = y+15 ;
        el.style.left = x + "px";
        el.style.top = y + "px";
        el.style.display = "block";
    }

    function hideTable(event, table_id){
        el = document.getElementById(table_id);
        el.style.display = "none";
    }
</script>

<style type="text/css">
    .inner_table {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #666666;
        border-collapse: collapse;
    }
    .inner_table th {
        border-width: 1px;
        padding: 2px;
        border-style: solid;
        border-color: #666666;
        background-color: #dedede;
    }
    .inner_table td {
        border-width: 1px;
        padding: 2px;
        border-style: solid;
        border-color: #666666;
        background-color: #ffffff;
    }
</style>

<div class="container-fluid admin-ui">
    <div class="row-fluid">
        <div class="span2">
            <div class="tabs-respond">
                <rave:admin_tabsheader/>
            </div>
        </div>
        <div class="span10">
            <article>
                <h2>Experiments List</h2>
                <rave:admin_listheader/>
                    <rave:admin_paging/>

    <c:if test="${searchResult.totalResults > 0}">
                <table id="experimentsList" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="admin.experiment.uid"/></th>
                        <th><fmt:message key="admin.experiment.author"/></th>
                        <th><fmt:message key="admin.experiment.lastUpdated"/></th>
                        <th><fmt:message key="admin.experiment.state"/></th>
                        <th><fmt:message key="admin.experiment.nodedata"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${searchResult.resultSet}" var="experiment">
                        <tr>
                            <td>
                                <c:out value="${experiment.name}"/>
                            </td>
                            <td>
                                <c:out value="${experiment.author}"/>
                            </td>
                            <td>
                                <c:out value="${experiment.updatedTime}"/>
                            </td>
                            <td>
                                <c:out value="${experiment.state}"/>
                            </td>
                            <td>
                                <display:column>
                                    <a href="#!" onclick="showPos(event,'${experiment.nodehelperList}')" onmouseout="hideTable(event,'${experiment.nodehelperList}')">More...</a>
                                    <DIV id='${experiment.nodehelperList}' style='display: none; position: absolute; left: 200px; top: 100px; border: solid black 1px; padding: 10px; background-color: rgb(200,100,100); text-align: justify; font-size: 12px;'>
                                       <SPAN id='${experiment.nodehelperList}+22'>
                                                <table class="inner_table" id="${experiment.nodehelperList}">
                                                    <tr>
                                                        <th>Node Type</th>
                                                        <th>Node Id</th>
                                                        <th>Input</th>
                                                        <th>Output</th>
                                                    </tr>
                                                    <c:forEach var="item" items="${experiment.nodehelperList}">

                                                        <tr>
                                                            <td> ${item.type} </td>
                                                            <td> ${item.workflowInstanceNodeId} </td>
                                                            <td> ${item.input} </td>
                                                            <td> ${item.output} </td>

                                                        </tr>

                                                    </c:forEach>
                                                </table>
                                       </SPAN>
                                    </DIV>
                                </display:column>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </c:if>

            </article>
        </div>
    </div>
</div>
</div>

