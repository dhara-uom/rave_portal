<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<script type='text/javascript' src='/portal/static/scripts/jquery.blockUI.js'></script>
<rave:navbar pageTitle="${pagetitle}"/>
<script>
    require(["portal/rave_admin", "jquery"], function(raveAdmin, $){
    $(document).ready(function() {
        $('a').click(function(){
            $.blockUI({ css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            } });
            setTimeout($.unblockUI, 20000);
        });
    });
    })
</script>

<div id="workflowdetails"  class="container-fluid admin-ui">
    <div class="row-fluid">
        <div class="span2">
            <div class="tabs-respond">
                <rave:admin_tabsheader/>
            </div>
        </div>
        <div class="span10">
            <article>
                <h2><fmt:message key="admin.workflows.shortTitle"/></h2>

                <table id="workflowList" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="admin.workflow.name"/></th>
                        <th><fmt:message key="admin.workflow.createdBy"/></th>
                        <th><fmt:message key="admin.workflow.createdDate"/></th>
                        <th><fmt:message key="admin.workflow.deploymentOptions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${message}" var="workflow">
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
                            <td>
                                <a name="default" href="/portal/app/admin/workflow/deploy?workflowId=${workflow.name}">Deafult</a>
                                <a name="custom" href="/portal/app/admin/workflow/customdeploy?workflowId=${workflow.name}"> Custom</a>
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

