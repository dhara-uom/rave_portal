<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type='text/javascript' src='/portal/static/scripts/jquery.blockUI.js'></script>
<rave:navbar pageTitle="${pagetitle}"/>
<script>

    var openTable="";

    require(["portal/rave_admin", "jquery"], function(raveAdmin, $){
    $(document).ready(function() {
        $('').click(function(){
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

    function showTable(form_id){
        if(document.getElementById(form_id).style.display == "none") {
            if(openTable !="" && document.getElementById(openTable).style.display == "block"){
                document.getElementById(openTable).style.display = "none";
            }
            document.getElementById(form_id).style.display = "block";
            openTable=form_id;
        }
        else
            document.getElementById(form_id).style.display = "none";
    }

    function readInputs(form_name){

        var query = $('#'+form_name).serialize();
        var link = "/portal/app/admin/monitoring?workflowId="+form_name+"&"+query;
        window.open(link);

        return '';

    }
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
                <rave:admin_listheader_dhara/>
                <rave:admin_paging/>

                <table id="workflowList" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="admin.workflow.name"/></th>
                        <th><fmt:message key="admin.workflow.createdBy"/></th>
                        <th><fmt:message key="admin.workflow.createdDate"/></th>
                        <th colspan="2"><fmt:message key="admin.workflow.deploymentOptions"/></th>
                        <th><fmt:message key="admin.workflow.execute"/></th>
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
                            <td>
                                <a name="default" href="/portal/app/admin/workflow/deploy?workflowId=${workflow.name}">Deafult</a>
                            </td>
                            <td>
                                <a name="custom" href="/portal/app/admin/workflow/customdeploy?workflowId=${workflow.name}"> Custom</a>
                            </td>
                            <td>
                                <a href="#" onclick="showTable('${workflow.name}')" >Enter Input</a>
                                <form class="inner_table" id="${workflow.name}" style="display:none" >
                                    <c:forEach var="item" items="${workflow.inputs}" varStatus="outer">

                                        Input        ${item.name} (${item.type}) : <input type="text" name="${item.name}/(${item.type})"><br>

                                    </c:forEach>
                                     <a name="${item.type}" href='javascript:document.location.href=readInputs("${workflow.name}");' >Execute</a>
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

