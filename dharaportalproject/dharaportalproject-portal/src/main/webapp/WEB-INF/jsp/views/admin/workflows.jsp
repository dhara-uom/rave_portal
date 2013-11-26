<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type='text/javascript' src='/portal/static/scripts/jquery.blockUI.js'></script>
<style type="text/css" src='/portal/static/css/dhara.css'></style>
<rave:navbar pageTitle="${pagetitle}"/>
<script>

    var openTable = "";

    require(["portal/rave_admin", "jquery"], function (raveAdmin, $) {
        $(document).ready(function () {
            $(".defaultDeploy").click(function () {
                $.blockUI({ css: {
                    border: 'none',
                    padding: '15px',
                    backgroundColor: '#000',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .5,
                    color: '#fff'
                } });
                setTimeout($.unblockUI, 40000);
            });
        });
    })

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

    function readInputs(form_name) {

        var query = $('#' + form_name).serialize();
        var link = "/portal/app/admin/monitoring?workflowId=" + form_name + "&" + query;
        window.open(link);

        return '';

    }
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
                <c:if test="${not empty successMessage}">
                    <div id="successMeassage" class="successMessage alert alert-info">
                        <p>${successMessage}</p>
                    </div>
                </c:if>
                <h2><fmt:message key="admin.workflows.shortTitle"/></h2>
                <rave:admin_listheader_dhara/>
                <rave:admin_paging/>

                <table id="workflowList" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th style="text-align: center"><fmt:message key="admin.workflow.name"/></th>
                        <th style="text-align: center"><fmt:message key="admin.workflow.createdBy"/></th>
                        <th style="text-align: center"><fmt:message key="admin.workflow.createdDate"/></th>
                        <th style="text-align: center" colspan="2"><fmt:message
                                key="admin.workflow.deploymentOptions"/></th>
                        <th style="text-align: center"><fmt:message key="admin.workflow.execute"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${searchResult.resultSet}" var="workflow">
                        <tr>
                            <td>
                                <c:out value="${workflow.name}"/>
                            </td>
                            <td>
                                <c:out value="admin"/>
                            </td>
                            <td>
                                <c:out value="10/10/2013"/>
                            </td>
                            <td style="text-align: center">
                                <form class="form-inline form-custom"
                                      action="<c:url value="/app/admin/workflow/deploy"/>" method="get">
                                    <fieldset>
                                        <fmt:message key="admin.workflow.default.button" var="viewDefaultButtonText"/>
                                        <input type="hidden" name="workflowId"
                                               value="<c:out value="${workflow.name}"/>"/>
                                        <button class="btn btn-primary-2 view-btn" id="viewDefault" type="submit"
                                                value="${viewDefaultButtonText}">${viewDefaultButtonText}</button>
                                    </fieldset>
                                </form>
                                    <%--<a name="default" href="/portal/app/admin/workflow/deploy?workflowId=${workflow.name}">Deafult</a>--%>
                            </td>
                            <td style="text-align: center">
                                <form class="form-inline form-custom"
                                      action="<c:url value="/app/admin/workflow/customdeploy"/>" method="get">
                                    <fieldset>
                                        <fmt:message key="admin.workflow.custom.button" var="viewCustomButtonText"/>
                                        <input type="hidden" name="workflowId"
                                               value="<c:out value="${workflow.name}"/>"/>
                                        <button class="btn btn-primary-2 view-btn" id="viewCustom" type="submit"
                                                value="${viewCustomButtonText}">${viewCustomButtonText}</button>
                                    </fieldset>
                                </form>
                                    <%--<a name="custom" href="/portal/app/admin/workflow/customdeploy?workflowId=${workflow.name}"> Custom</a>--%>
                            </td>
                            <td style="text-align: center">
                                <form class="form-inline form-custom"
                                      action="<c:url value="/app/admin/workflow/details"/>" method="get">
                                    <fieldset>
                                        <fmt:message key="admin.workflow.button" var="viewWorkflowButtonText"/>
                                        <input type="hidden" name="workflowId"
                                               value="<c:out value="${workflow.name}"/>"/>
                                        <input type="hidden" name="workflowDesc"
                                               value="<c:out value="${workflow.description}"/>"/>
                                        <button class="btn btn-primary-2 view-btn" id="viewWorkflowButton" type="submit"
                                                value="${viewWorkflowButtonText}">${viewWorkflowButtonText}</button>
                                    </fieldset>
                                </form>
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

