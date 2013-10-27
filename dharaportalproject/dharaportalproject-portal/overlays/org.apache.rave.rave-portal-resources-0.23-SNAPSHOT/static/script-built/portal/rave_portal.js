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

define(["underscore","jquery","rave","portal/rave_templates","clientMessages"],function(e,t,n,r,i){function o(e){return i[e]}function u(e,t){return i[e]=t}function a(e,n){t("#widget-"+e+"-body").html(n)}function f(e){s=e}function l(){return s}function c(e){var t=e!=null?"/"+e:"";window.location.href=n.getContext()+"page/view"+t}function h(e,t,r){r?r="#"+r:r="",window.location.href=n.getContext()+"store/widget/"+e+"?referringPageId="+t+r}function p(){return e.isEmpty(n.getWidgets())}function d(){return n.getViewer().editor}function v(e){var t=e.split("-");return t.length>2&&t[0]=="widget"||t[0]=="region"?t[1]:null}function m(e){var n=r.templates["info-message"]({message:e});t(n).hide().prependTo("body").css({position:"fixed",top:0,left:0,width:"auto","max-width":"60%","font-size":"1.25em",padding:".6em 1em","z-index":9999,"border-radius":"0 0 4px 0"}).fadeIn("fast").delay(8e3).fadeOut(function(){t(this).remove()})}var s=0;return{showInfoMessage:m,getObjectIdFromDomId:v,errorWidget:a,getJavaScriptDebugMode:l,setJavaScriptDebugMode:f,viewPage:c,viewWidgetDetail:h,isPageEmpty:p,getClientMessage:o,addClientMessage:u,isPageEditor:d}});