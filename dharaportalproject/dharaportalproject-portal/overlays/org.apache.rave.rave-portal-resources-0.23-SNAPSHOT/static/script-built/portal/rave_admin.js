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

define("portal/rave_admin",["jquery"],function(e){function t(){e(".datatable tr").bind("click",function(){var t=e(this).attr("data-detaillink");t!=undefined&&t!=""&&(window.location=t)})}function n(){e("#thumbnailModal, #screenshotModal").on("shown",function(){if(!e(this).hasClass("sized")){var t=e(this).find("img").width(),n=e(this).find("img").height(),r=e(this).find(".modal-footer").outerHeight(),i=e(this).find(".modal-header").outerHeight(),s=n+r+i;e(this).css({width:t+"px","margin-top":"-"+Math.round(s/2)+"px","margin-left":"-"+Math.round(t/2)+"px"}).addClass("sized")}})}function r(){t(),n()}return{init:r}});