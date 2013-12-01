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
<%-- Note: This page has the body definition embedded so we can reference it directly from the security config file. --%>
<tiles:insertDefinition name="templates.base">
<%-- Override the default pageTitleKey and then export it to the request scope for use later on this page --%>
<tiles:putAttribute name="pageTitleKey" value="page.welcome.title"/>
<tiles:importAttribute name="pageTitleKey" scope="request"/>

<tiles:putAttribute name="body">

<rave:login_navbar hideButton="loginButton"/>
<div class="logo-wrapper-login"></div>
<div class="container-fluid">
<c:if test="${not empty message}">
    <div class="alert alert-info">${message}</div>
</c:if>

    <%--<h1><fmt:message key="${pageTitleKey}"/></h1>--%>
<div class="span10">
    <div class="row-fluid" id="pageText">

        <div id="mainBlock" class="clearfix well-2">
            <div class="row-fluid" id="some moreRow">
                <h5 style="padding-below:20px;  font-size: 18px"><fmt:message
                        key="page.overview.title"/></h5>

                <p style="padding-top:20px; color:#383636; padding-bottom:20px; text-align: justify">
                    Dhara portal provides access to Dhara-geoscience gateway, a step towards enabling computational
                    geoscience.
                    Gateway is based on <a href="http://airavata.apache.org/">Apache Airavata</a>,
                    <a href="http://52north.org/communities/geoprocessing/wps/">52North WPS</a> and
                    <a href="http://rave.apache.org/"> Apache Rave</a>. Several technologies have been brought together
                    in building Dhara gateway to address key concerns of computational geoscience domain. Dhara portal
                    is a free tool
                    which caters the geoscientists, students and the general public.
                </p>
            </div>
            <div class="row-fluid" id="mapRow">

                <div class="span11" style="margin-left: 30px">

                    <div class="slate">

                        <div class="page-header">
                            <h2 style="color:#383636"><i class="icon-globe pull-right"></i>Map</h2>
                        </div>
                        <div id="map_element_1" style="height: 312px;"></div>
                        <div class="slate" id="map_description">
                            <p style="color: #383636">The map shows significant earthquakes occurred world-wide
                                within the year of 2013. This is an example for the use of OGC KML data format.
                                <i>Source</i>:<a href="http://earthquake.usgs.gov/earthquakes/">
                                    http://earthquake.usgs.gov/earthquakes/</a></p>
                        </div>
                    </div>

                </div>
                    <%--<div class="span5">--%>

                    <%--<div class="slate">--%>

                    <%--<div class="page-header">--%>
                    <%--<h2 style="color:#383636"><i class="icon-briefcase pull-right"></i>Features</h2>--%>
                    <%--</div>--%>
                    <%--<h5 style="font-size: 18px; font-weight: normal">Workflows</h5>--%>

                    <%--<h5 style="font-size: 18px; font-weight: normal">WPS</h5>--%>
                    <%--</div>--%>
                    <%--</div>--%>
            </div>
            <div class="row-fluid" id="lastRow">
                <h5 style="padding-below:20px;  font-size: 18px">Functionality</h5>

                <p style="padding-top:20px; padding-bottom:20px; color: #383636; text-align: justify">
                    Dhara portal provides its services according to standards introduced by the
                    <a href="http://www.opengeospatial.org/">Open Geospatial Consortium</a>.
                    Currently we cater the use of four OGC standards: WPS, WFS, KML and GML.
                    <br>
                    <br><i><b>WPS Client:</b></i> To load WPS processes available in a remote server
                    <br><i><b>WFS Client:</b></i> To load geospatial data exposed through WFS from a remote server
                    <br><i><b>KML Client:</b></i> To load and visualize geospatial data using local KML files
                    <br><i><b>GML Client:</b></i> To load and visualize geospatial data using local GML files
                </p>
            </div>
        </div>
    </div>
</div>
<div class="span3">
    <div class="row-fluid" id="loginOptions">

        <div id="loginBlock" class="clearfix well">
            <h6 style="font-size: 18px; color: #000; padding-bottom: 13px; margin-left: 80px;"><fmt:message
                    key="page.login.title"/></h6>
                <%--
                    //############################################
                    // LOGIN FORM
                    //############################################
                --%>
            <form class="form-horizontal" id="loginForm" name="loginForm" action="j_spring_security_check"
                  method="post">
                <fieldset>
                    <c:if test="${param['authfail'] eq 'form'}">
                        <div class="alert alert-error"><fmt:message
                                key="page.login.usernamepassword.fail"/></div>
                    </c:if>
                    <div class="control-group">
                        <label class="control-label-1" for="usernameField"><fmt:message
                                key="page.general.username"/></label>

                        <div class="controls">
                            <input class="input-large" id="usernameField" type="text" name="j_username"
                                   autofocus="autofocus" autocapitalize="off" autocorrect="off"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label-1" for="passwordField"><fmt:message
                                key="page.general.password"/></label>

                        <div class="controls">
                            <input class="input-large" id="passwordField" type="password"
                                   name="j_password"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label-2" for="remember_me">
                            <fmt:message key="page.login.rememberme"/>
                        </label>

                        <div class="controls">
                            <input type='checkbox' name='_spring_security_remember_me' id="remember_me"
                                   value="true"
                                   style="margin-left: -60px"/>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <fmt:message key="page.login.usernamepassword.login" var="loginButtonText"/>
                    <div class="controls">
                        <button class="btn-custom btn btn-primary" type="submit"
                                value="${loginButtonText}">${loginButtonText}</button>
                    </div>
                </fieldset>
            </form>
            <form class="form-inline" action="<c:url value="/app/newaccount.jsp"/>" method="get">
                <fieldset>
                    <fmt:message key="page.login.createaccount.button" var="createAccountButtonText"/>
                    <button class="btn btn-link" id="createNewAccountButton" type="submit"
                            value="${createAccountButtonText}">${createAccountButtonText}</button>
                </fieldset>
            </form>
                <%--
                    //############################################
                    // OPENID LOGIN
                    //############################################
                --%>
            <form class="form-horizontal" id="openIdForm" name='oidf'
                  action='j_spring_openid_security_check' method='POST'>
                <fieldset>
                    <h4 style="padding-bottom: 10px"><fmt:message key="page.login.openid"/></h4>
                    <c:if test="${param['authfail'] eq 'openid'}">
                        <div class="alert alert-error"><fmt:message key="page.login.openid.fail"/></div>
                    </c:if>
                    <div class="control-group">
                        <label class="control-label-1" for="openid_identifier"><fmt:message
                                key="page.login.openid.identifier"/></label>

                        <div class="controls">
                            <input class="input-large" type="text" id="openid_identifier"
                                   name="openid_identifier" autocapitalize="off" autocorrect="off"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label-2" for="remember_me">
                            <fmt:message key="page.login.rememberme"/>
                        </label>

                        <div class="controls">
                            <input type='checkbox' name='_spring_security_remember_me'
                                   id="remember_me_openid" value="true"
                                   style="margin-left: -60px"/>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <fmt:message key="page.login.openid.button" var="openidButtonText"/>
                    <div class="controls">
                        <button class="btn-custom-2 btn btn-primary " type="submit"
                                value="${openidButtonText}">${openidButtonText}</button>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="row-fluid" id="newsRow">
        <div id="newsBlock" class="clearfix well">
            <div class="slate">

                <div class="page-header">
                    <h2 style="color:#383636"><i class="icon-rss pull-right"></i>News</h2>
                </div>

                <table class="orders-table table">
                    <tbody>
                    <tr>
                        <td><a class="news" href="">WPS Client 1.00 introduced</a></td>
                    </tr>
                    <tr>
                        <td><a class="news" href="">WFS Client 1.00 added</a></td>
                    </tr>
                    <tr>
                        <td><a class="news" href="">KML Client 1.00 added</a></td>
                    </tr>
                    <tr>
                        <td><a class="news" href="">GML Client 1.00 added</a></td>
                    </tr>
                        <%--<tr>--%>
                        <%--<td colspan="2"><a class="news" href="">Read more news</a></td>--%>
                        <%--</tr>--%>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</div>
</tiles:putAttribute>
</tiles:insertDefinition>

