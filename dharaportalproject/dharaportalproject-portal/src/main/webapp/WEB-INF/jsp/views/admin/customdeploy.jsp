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
            <div>
                <form:form id="customDeployment" cssClass="form-inline" action="deploy" method="POST">
                    <form:errors cssClass="error" element="p"/>
                    <fieldset>
                        <legend>Custom Deployment - ${workflowId} </legend>
                        <input type="hidden" name="workflowId" value="${workflowId}"/>
                        <table class="table table-striped table-bordered table-condensed">
                            <tr>
                                <td>Extending Class</td>
                                <td>
                                    <select name="extendingAlgo" id="extendingAlgo">
                                        <option value="AbstractSelfDescribingAlgorithm">AbstractSelfDescribingAlgorithm</option>
                                        <option value="ConvexHullAlgorithm">ConvexHullAlgorithm</option>
                                        <option value="CoordinateTransformAlgorithm">CoordinateTransformAlgorithm</option>
                                        <option value="DifferenceAlgorithm">DifferenceAlgorithm</option>
                                        <option value="IntersectionAlgorithm">DouglasPeuckerAlgorithm</option>
                                        <option value="TopologyPreservingSimplificationAlgorithm">TopologyPreservingSimplificationAlgorithm</option>
                                        <option value="ContainsAlgorithm">ContainsAlgorithm</option>
                                        <option value="CrossesAlgorithm">CrossesAlgorithm</option>
                                        <option value="DisjointAlgorithm">DisjointAlgorithm</option>
                                        <option value="DistanceAlgorithm">DistanceAlgorithm</option>
                                        <option value="EqualsAlgorithm">EqualsAlgorithm</option>
                                        <option value="IntersectsAlgorithm">IntersectsAlgorithm</option>
                                        <option value="OverlapsAlgorithm">OverlapsAlgorithm</option>
                                        <option value="WithinAlgorithm">WithinAlgorithm</option>
                                        <option value="TouchesAlgorithm">TouchesAlgorithm</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                        <h3>Workflow Inputs</h3>
                        <table id="customDeploymentInput" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th style="width: 33%">
                                    Input Name
                                </th >
                                <th style="width: 33%">
                                    Existing Mapping
                                </th>
                                <th style="width: 33%">
                                    New Mapping Type
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${inputNodes}" var="input" varStatus="loop">
                                <tr>
                                    <td>
                                        <c:out value="${input.nodeName}"></c:out>
                                    </td>
                                    <td>
                                        <c:out value="${input.existingMapping}"></c:out>
                                    </td>
                                    <td>
                                        <select style="width: 80px" name="<c:out value="${input.nodeName}"></c:out>" id="<c:out value="${input.nodeName}"></c:out>">
                                        <option value="Integer">Integer</option>
                                        <option value="Boolean">Boolean</option>
                                        <option value="Short">Short</option>
                                        <option value="Double">Double</option>
                                        <option value="Float">Float</option>
                                        <option value="String">String</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br/>
                        <h3>Workflow Outputs</h3>
                        <table id="customDeploymentOutput" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th style="width: 33%">
                                    Output Name
                                </th>
                                <th style="width: 33%">
                                    Existing Mapping
                                </th>
                                <th style="width: 33%">
                                    New Mapping Type
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${outputNodes}" var="output" varStatus="loop">
                                <tr>
                                    <td>
                                        <c:out value="${output.nodeName}"></c:out>
                                    </td>
                                    <td>
                                        <c:out value="${output.existingMapping}"></c:out>
                                    </td>
                                    <td>
                                        <select style="width: 80px" name="<c:out value="${output.nodeName}"></c:out>" id="<c:out value="${output.nodeName}"></c:out>">
                                        <option value="Integer">Integer</option>
                                        <option value="Boolean">Boolean</option>
                                        <option value="Short">Short</option>
                                        <option value="Double">Double</option>
                                        <option value="Float">Float</option>
                                        <option value="String">String</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <input type="submit" style="width: 100px; float: right;">
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</div>

