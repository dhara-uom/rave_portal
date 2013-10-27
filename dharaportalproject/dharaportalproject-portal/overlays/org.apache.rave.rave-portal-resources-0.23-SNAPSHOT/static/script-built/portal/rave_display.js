/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

define(["rave","portal/rave_templates","jqueryUi"],function(e,t){function n(n){e.api.rest.getUsersForWidget({widgetId:n,successCallback:function(e){_.each(e,function(e){e.name=e.displayName||e.preferredName||e.givenName+" "+e.familyName});var r=t.templates["users-of-widget"]({users:e,widgetName:$("#widget-"+n+"-title").text().trim()});$(r).dialog({modal:!0,buttons:[{text:"Close",click:function(){$(this).dialog("close")}}]})}})}return{displayUsersOfWidget:n}});