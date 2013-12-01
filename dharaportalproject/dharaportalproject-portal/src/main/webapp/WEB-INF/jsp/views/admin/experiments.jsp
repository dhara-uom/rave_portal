<%--
/***********************************************************************************************************************
 *
 * Dhara- A Geoscience Gateway
 * ==========================================
 *
 * Copyright (C) 2013 by Dhara
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
--%>
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
            y = window.event.clientY + document.documentElement.scrollTop + +document.body.scrollTop;
        }
        else {
            x = event.clientX + window.scrollX;
            y = event.clientY + window.scrollY;
        }
        x -= 2;
        y -= 2;
        y = y + 15;
        el.style.left = x + "px";
        el.style.top = y + "px";
        el.style.display = "block";
    }

    function hideTable(event, table_id) {
        el = document.getElementById(table_id);
        el.style.display = "none";
    }
</script>

<style type="text/css">
    .inner_table {
        font-size: 11px;
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
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${searchResult.resultSet}" var="experiment">
                            <tr>
                                <td>
                                    <display:column>
                                        <a href="#!" onclick="showPos(event,'${experiment.nodehelperList}')"
                                           onmouseout="hideTable(event,'${experiment.nodehelperList}')">${experiment.name}</a>

                                        <DIV id='${experiment.nodehelperList}'
                                             style='display: none; position: absolute; left: 200px; top: 100px; background: #FFFFFF'>
                                       <SPAN id='${experiment.nodehelperList}+22'>
                                                <table class="table-2 table-striped-2 table-bordered-2 table-condensed"
                                                       id="${experiment.nodehelperList}">
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
                                <td>
                                    <c:out value="${experiment.author}"/>
                                </td>
                                <td>
                                    <c:out value="${experiment.updatedTime}"/>
                                </td>
                                <td>
                                    <c:out value="${experiment.state}"/>
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

