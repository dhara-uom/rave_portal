<%@ page language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<fmt:setBundle basename="messages"/>

<fmt:message key="${pageTitleKey}" var="pagetitle"/>
<%--<script type='text/javascript' src='/portal/static/scripts/jquery.blockUI.js'></script>--%>
<rave:navbar pageTitle="${pagetitle}"/>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        setTimeout(get_events, 4000);
    });


    function get_events() {
        $.ajax({
            url: 'http://localhost:8080/portal/app/admin/monitorData',
            success: function (data) {
                $('#monitoringTable').append(data);
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
                <h2><fmt:message key="admin.monitoring.shortTitle"/></h2>
                <div id="test">
                    <table id=monitoringTable class="table table-striped table-bordered table-condensed">

                    </table>
                </div>
            </article>
        </div>
    </div>
</div>




