<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>

<rave:navbar pageTitle="${pagetitle}"/>
<script>

    var workflowName;
    $(document).ready(function(){
        workflowName = document.URL.split("?")[1].split("&")[0];
        setTimeout(get_events, 4000);
    });

    function get_events(){
        $.ajax({
            url: '/portal/app/admin/monitorData/'+workflowName,
            success: function(data) {
                $('#test').append(data);
            }
        });
        setTimeout(get_events, 5000);

    }
</script>

<style type="text/css">

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
                <h2>Monitoring data</h2>
                <rave:admin_listheader_dhara/>
                <rave:admin_paging/>

                <div id="test"></div>

            </article>
        </div>
    </div>
</div>
</div>




