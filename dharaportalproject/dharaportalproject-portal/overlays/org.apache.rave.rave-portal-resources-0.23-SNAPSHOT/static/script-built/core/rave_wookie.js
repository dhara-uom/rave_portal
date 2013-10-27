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

define(["core/rave_openajax_hub","core/rave_state_manager"],function(e,t){function r(e,t){}function i(e){}function s(e){}var n={};return n.initWidget=function(e){},n.renderWidget=function(n,o,u){var a=document.getElementById(["widget-",n.regionWidgetId,"-body"].join(""));window["onWidget"+n.regionWidgetId+"Load"]=function(){window.document.getElementById(n.regionWidgetId).style.visibility="visible"},new OpenAjax.hub.IframeContainer(e,""+n.regionWidgetId,{Container:{onSecurityAlert:r,onConnect:i,onDisconnect:s},IframeContainer:{parent:o,iframeAttrs:{height:n.height||t.getDefaultHeight(),width:n.width||t.getDefaultWidth(),frameborder:0},uri:n.widgetUrl,onGadgetLoad:"onWidget"+n.regionWidgetId+"Load"}})},n.closeWidget=function(e){},n});