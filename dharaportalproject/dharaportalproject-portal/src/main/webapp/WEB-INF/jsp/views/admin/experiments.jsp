<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>

<rave:navbar pageTitle="${pagetitle}"/>
<script>

    var openTable = "";

    function showTable(form_id) {
        if (document.getElementById(form_id).style.display == "none") {
            if (openTable != "" && document.getElementById(openTable).style.display == "block") {
                document.getElementById(openTable).style.display = "none";
            }
            document.getElementById(form_id).style.display = "block";
            openTable = form_id;
        }
        else
            document.getElementById(form_id).style.display = "none";
    }
</script>

<style type="text/css">
    .inner_table {
        font-family:"Helvetica Neue", Helvetica, Arial, sans-serif;;
        font-size: 11px;
        color: #333;
        /*border-width: 1px;*/
        border-color: #d2d2d2;
        border-collapse: collapse;
        width: 100%;
        height: 100%;
    }

    .inner_table th {
        border-width: 1px;
        padding: 2px;
        border-style: inset;
        /*border-color: #666666;*/
        background-color: #FFFFFF;
    }

    .inner_table td {
        border-width: 1px;
        padding: 2px;
        border-style: inset;
        /*border-color: #666666;*/
        background-color: #FFFFFF;
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
                <rave:admin_listheader_dhara/>
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
                                        <a href="#!" onclick="showTable('${experiment.nodehelperList}')">More...</a>
                                        <table class="inner_table" id="${experiment.nodehelperList}" style="display:none">
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

