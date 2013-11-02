<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>

<rave:navbar pageTitle="${pagetitle}"/>
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
                    <c:forEach items="${message}" var="experiment">
                        <tr>
                            <td>
                                <c:out value="${experiment.name}"/></a>
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
                                    <a href="#" onclick="showTable('${experiment.nodehelperList}')" >More</a>
                                    <table class="inner_table" id="${experiment.nodehelperList}" style="display:none">
                                        <tr>
                                            <th>Node Type</th>
                                            <th>Workflow Instance Node Id</th>
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
            </article>
        </div>
    </div>
</div>
</div>

