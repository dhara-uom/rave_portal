<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type='text/javascript' src='/portal/static/scripts/jquery.blockUI.js'></script>
<style type="text/css" src='/portal/static/css/dhara.css'></style>
<rave:navbar pageTitle="${pagetitle}"/>
<script>

    <%--var workflowName;--%>
    <%--var expId = "${id}";--%>
    <%--var Monitordata = " " ;--%>
    <%--$(document).ready(function(){--%>
    <%--workflowName = (document.URL.split("?")[1].split("&")[0]).split("=")[1];--%>
    <%--setTimeout(get_events, 2000);--%>
    <%--});--%>

    <%--function get_events(){--%>
    <%--$.ajax({--%>
    <%--url: '/portal/app/admin/monitorData/'+expId,--%>
    <%--success: function(data) {--%>
    <%--Monitordata = "\""+data+"\"";--%>
    <%--$('#monitoringData').append(data);--%>
    <%--}--%>
    <%--});--%>
    <%--if(Monitordata.indexOf("workflowTerminated")!=-1){}--%>
    <%--else{--%>
    <%--setTimeout(get_events, 1000);--%>
    <%--}--%>

    <%--}--%>


    <%--function showTable(table_id){--%>
    <%--if(expId!=null && expId!=""){--%>
    <%--document.getElementById(table_id).style.display = "block";--%>
    <%--}--%>
    <%--}--%>

</script>
<div id="workflowdetails" class="container-fluid admin-ui">
    <div class="row-fluid">
        <div class="span2">
            <div class="tabs-respond">
                <rave:admin_tabsheader/>
            </div>
        </div>
        <div class="span10">
            <article>
                <a href="<spring:url value="/app/admin/workflows"/>"><fmt:message key="admin.workflow.goback"/></a></li>
                <h2><c:out value="${workflowId}"/></h2>

                <div>
                    <section>

                        <form class="form-horizontal" id="updateUserProfile" action="<c:url value="/app/admin/monitoring"/>"
                              method="get">
                            <form:errors cssClass="error" element="p"/>
                            <fieldset>
                                <legend><fmt:message key="admin.workflow.details"/></legend>
                                <br/>

                                <p>Description:${workflowDesc}</p>
                                <br/>
                                <c:forEach items="${inputNodes}" var="input" varStatus="outer">
                                    <div class="control-group">
                                        <label class="control-label">Input ${input.nodeName} (${input.existingMapping}) :</label>
                                        <div class="controls">
                                            <input type="text" name="${input.nodeName}/(${input.existingMapping})"> /><br>
                                        </div>
                                    </div>
                                </c:forEach>
                                <input type="hidden" name="workflowId" value="<c:out value="${workflowId}"/>"/>
                                <button type="submit"  class="btn btn-primary" id="execute" value="Execute">Execute</button>
                            </fieldset>
                        </form>
                    </section>
                </div>
                <%--<div>--%>
                <%--<section id="monitoring" style="display: none">--%>
                <%--<legend><fmt:message key="admin.monitoring.shortTitle"/></legend>--%>

                <%--<div id="test">--%>
                <%--<table id=monitoringTable class="table table-striped table-bordered table-condensed">--%>
                <%--<thead>--%>
                <%--<th><fmt:message key="admin.monitoring.timestamp"/></th>--%>
                <%--<th><fmt:message key="admin.monitoring.component"/></th>--%>
                <%--<th><fmt:message key="admin.monitoring.status"/></th>--%>
                <%--<th><fmt:message key="admin.monitoring.msg"/></th>--%>
                <%--</thead>--%>
                <%--<tbody id="monitoringData">--%>

                <%--</tbody>--%>
                <%--</table>--%>
                <%--</div>--%>
                <%--</section>--%>
                <%--</div>--%>
            </article>
        </div>
    </div>
</div>