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

define("portal/rave_templates",["handlebars","jquery","clientMessages"],function(e,t,n){var r={};return t("[data-template-for]").each(function(){var n=t(this).data("template-for"),i=t(this).html();r[n]=e.compile(i)}),e.registerHelper("getClientMessage",function(e){return n[e]}),{templates:r}}),define("portal/rave_portal",["underscore","jquery","rave","portal/rave_templates","clientMessages"],function(e,t,n,r,i){function o(e){return i[e]}function u(e,t){return i[e]=t}function a(e,n){t("#widget-"+e+"-body").html(n)}function f(e){s=e}function l(){return s}function c(e){var t=e!=null?"/"+e:"";window.location.href=n.getContext()+"page/view"+t}function h(e,t,r){r?r="#"+r:r="",window.location.href=n.getContext()+"store/widget/"+e+"?referringPageId="+t+r}function p(){return e.isEmpty(n.getWidgets())}function d(){return n.getViewer().editor}function v(e){var t=e.split("-");return t.length>2&&t[0]=="widget"||t[0]=="region"?t[1]:null}function m(e){var n=r.templates["info-message"]({message:e});t(n).hide().prependTo("body").css({position:"fixed",top:0,left:0,width:"auto","max-width":"60%","font-size":"1.25em",padding:".6em 1em","z-index":9999,"border-radius":"0 0 4px 0"}).fadeIn("fast").delay(8e3).fadeOut(function(){t(this).remove()})}var s=0;return{showInfoMessage:m,getObjectIdFromDomId:v,errorWidget:a,getJavaScriptDebugMode:l,setJavaScriptDebugMode:f,viewPage:c,viewWidgetDetail:h,isPageEmpty:p,getClientMessage:o,addClientMessage:u,isPageEditor:d}}),define("portal/rave_store",["jquery","rave","portal/rave_portal"],function(e,t,n){function r(){e(".widgetRating").each(function(){var t=e(this).find(".widgetLikeButton"),n=e(this).find(".widgetLikeCount"),r=e(this).find(".widgetDislikeButton"),i=e(this).find(".widgetDislikeCount");t.outerWidth()>=n.outerWidth()?n.css("width",t.outerWidth()+"px"):t.css("width",n.outerWidth()+"px"),r.outerWidth()>=i.outerWidth()?i.css("width",r.outerWidth()+"px"):r.css("width",i.outerWidth()+"px")}),e(".widgetLikeButton").click(function(){if(!e(this).hasClass("active")){var n=this.id.substring("like-".length);t.api.rest.updateWidgetRating({widgetId:n,score:10});var r={widgetId:n,widgetLikeButton:this,widgetDislikeButton:e("#dislike-"+n),isLike:!0};l(r),e(this).addClass("btn-success"),e(this).siblings(".btn").removeClass("btn-danger")}}),e(".widgetDislikeButton").click(function(){if(!e(this).hasClass("active")){var n=this.id.substring("dislike-".length);t.api.rest.updateWidgetRating({widgetId:n,score:0});var r={widgetId:n,widgetLikeButton:e("#like-"+n),widgetDislikeButton:this,isLike:!1};l(r),e(this).addClass("btn-danger"),e(this).siblings(".btn").removeClass("btn-success")}})}function i(){e(".commentNewButton").button({icons:{primary:"ui-icon-disk"},text:!1}).click(function(){var n=this.id.substring("comment-new-".length);t.api.rest.createWidgetComment({widgetId:n,text:e("#newComment-"+n).get(0).value,successCallback:function(){window.location.reload()}})}),e(".commentDeleteButton").button({icons:{primary:"ui-icon-close"},text:!1}).click(function(){var e=this.id.substring("comment-delete-".length),n=this.getAttribute("data-widgetid");t.api.rest.deleteWidgetComment({widgetId:n,commentId:e,successCallback:function(){window.location.reload()}})}),e(".commentEditButton").click(function(){var r=this.id.substring("comment-edit-".length),i=this.getAttribute("data-widgetid"),s=e(this).parents(".comment").find(".commentText").text();e("#editComment").html(s),e("#editComment-dialog #updateComment").click(function(){t.api.rest.updateWidgetComment({widgetId:i,commentId:r,text:e("#editComment").get(0).value,successCallback:function(){window.location.reload()}})}).html(n.getClientMessage("common.update"))})}function s(n){e('*[data-toggle="basic-slide"]').click(function(){var t;e(this).attr("data-target")?t=e(this).attr("data-target"):t=e(this).attr("href");if(e(this).attr("data-toggle-text")){var n=e(this).html();e(this).html(e(this).attr("data-toggle-text")),e(this).attr("data-toggle-text",n)}e(t).slideToggle()}),e(".tagNewButton").click(function(){var n=this.id.substring("tag-new-".length);t.api.rest.createWidgetTag({widgetId:n,text:e("#tags").get(0).value,successCallback:function(){window.location.reload()}})}),t.api.rest.getTags({widgetId:n,successCallback:function(t){var n=e.map(t,function(e){return{label:e.keyword,value:e.keyword}});e("#tags").typeahead({source:n})}})}function o(n){n!=null&&e("#tagList").change(function(){var r=e("#tagList option:selected").text();r=e.trim(r),r.length>1&&(document.location.href=t.getContext()+"store/tag?keyword="+r+"&referringPageId="+n)})}function u(n){n!=null&&e("#categoryList").change(function(){var r=e("#categoryList option:selected").val();r=parseInt(r),!isNaN(r)&&r!="0"?document.location.href=t.getContext()+"store/category?categoryId="+r+"&referringPageId="+n:document.location.href=t.getContext()+"store?referringPageId="+n})}function a(n){n!=null&&e("#marketplaceCategoryList").change(function(){var r=e("#marketplaceCategoryList option:selected").val();document.location.href=t.getContext()+"marketplace/category/"+r+"?referringPageId="+n})}function f(e,r){var i=confirm(n.getClientMessage("confirm.add.from.marketplace"));i&&t.api.rpc.addWidgetFromMarketplace({url:e,providerType:r,successCallback:function(e){e.result==null?alert(n.getClientMessage("failed.add.from.marketplace")):alert("("+e.result.title+") "+n.getClientMessage("success.add.from.marketplace"))}})}function l(e){var t=document.getElementById("totalLikes-"+e.widgetId),n=t.getAttribute("data-rave-widget-likes"),r=document.getElementById("totalDislikes-"+e.widgetId),i=r.getAttribute("data-rave-widget-dislikes"),s=-1,o,u=-1,a,f="",l="",c=-1;e.isLike?(s=n,o=t,u=i,a=r,f=e.widgetLikeButton,l=e.widgetDislikeButton,c=0):(s=i,o=r,u=n,a=t,f=e.widgetDislikeButton,l=e.widgetLikeButton,c=10),s=parseInt(s)+1,o==t?(o.setAttribute("data-rave-widget-likes",s),o.innerHTML=s):(o.setAttribute("data-rave-widget-dislikes",s),o.innerHTML=s);var h=document.getElementById("rate-"+e.widgetId),p=h.value;if(l.get(0).getAttribute("checked")=="true"||f.checked==1)l.get(0).setAttribute("checked","false"),parseInt(p)==c&&parseInt(u)-1>-1&&(u=parseInt(u)-1,a==t?(a.setAttribute("data-rave-widget-likes",u),a.innerHTML=u):(a.setAttribute("data-rave-widget-dislikes",u),a.innerHTML=u));f.setAttribute("checked","true"),e.isLike?h.value="10":h.value="0"}function c(e){r(),i(),o(e),u(e),a(e)}return{init:c,initTags:s,confirmAddFromMarketplace:f}});