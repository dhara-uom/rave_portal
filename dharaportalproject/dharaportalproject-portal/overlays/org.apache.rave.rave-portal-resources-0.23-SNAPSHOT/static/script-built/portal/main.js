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

define("portal/rave_templates",["handlebars","jquery","clientMessages"],function(e,t,n){var r={};return t("[data-template-for]").each(function(){var n=t(this).data("template-for"),i=t(this).html();r[n]=e.compile(i)}),e.registerHelper("getClientMessage",function(e){return n[e]}),{templates:r}}),define("portal/rave_portal",["underscore","jquery","rave","portal/rave_templates","clientMessages"],function(e,t,n,r,i){function o(e){return i[e]}function u(e,t){return i[e]=t}function a(e,n){t("#widget-"+e+"-body").html(n)}function f(e){s=e}function l(){return s}function c(e){var t=e!=null?"/"+e:"";window.location.href=n.getContext()+"page/view"+t}function h(e,t,r){r?r="#"+r:r="",window.location.href=n.getContext()+"store/widget/"+e+"?referringPageId="+t+r}function p(){return e.isEmpty(n.getWidgets())}function d(){return n.getViewer().editor}function v(e){var t=e.split("-");return t.length>2&&t[0]=="widget"||t[0]=="region"?t[1]:null}function m(e){var n=r.templates["info-message"]({message:e});t(n).hide().prependTo("body").css({position:"fixed",top:0,left:0,width:"auto","max-width":"60%","font-size":"1.25em",padding:".6em 1em","z-index":9999,"border-radius":"0 0 4px 0"}).fadeIn("fast").delay(8e3).fadeOut(function(){t(this).remove()})}var s=0;return{showInfoMessage:m,getObjectIdFromDomId:v,errorWidget:a,getJavaScriptDebugMode:l,setJavaScriptDebugMode:f,viewPage:c,viewWidgetDetail:h,isPageEmpty:p,getClientMessage:o,addClientMessage:u,isPageEditor:d}}),define("portal/rave_backbone",["underscore","backbone"],function(e,t){var n=t.Model.extend({get:function(t){return e.clone(this.attributes[t])},toViewModel:function(){return this.toJSON()}}),r=t.Collection.extend({toViewModel:function(){return this.map(function(e){return e.toViewModel()})}}),i=t.View.extend({initialize:function(){var t=this;e.bindAll(this),e.each(this.models,function(e){e.on("change",t.render),e.on("reset",t.render)})},render:function(){var t=this.template,n={};return e.each(this.models,function(e,t){n[t]=e.toViewModel()}),this.$el.html(t(n)),this}});return{Model:n,Collection:r,View:i}}),define("portal/rave_models",["underscore","portal/rave_backbone","portal/rave_portal","rave"],function(e,t,n,r){var i=t.Model.extend({}),s=t.Collection.extend({model:i,pageSize:10,paginationData:{start:0,finish:0,total:0,prevLink:{},nextLink:{},pages:[]},initialize:function(){e.bindAll(this,"parse")},filter:function(e){this.searchTerm=e,this.searchTerm?r.api.rpc.searchUsers({searchTerm:this.searchTerm,offset:0,successCallback:this.parse,alertEmptySearch:function(){alert(n.getClientMessage("api.rpc.empty.search.term"))}}):r.api.rpc.getUsers({offset:0,successCallback:this.parse})},fetchPage:function(e){var t=this,i=e?e-1:0;i*=this.pageSize,this.searchTerm?r.api.rpc.searchUsers({searchTerm:this.searchTerm,offset:i,successCallback:this.parse,alertEmptySearch:function(){alert(n.getClientMessage("api.rpc.empty.search.term"))}}):r.api.rpc.getUsers({offset:i,successCallback:this.parse})},parse:function(t){var n=t.result;this.pageSize=n.pageSize||10,this.paginationData={start:n.offset+1,finish:n.resultSet.length+n.offset,total:n.totalResults,pageSize:n.pageSize,prevLink:{show:n.currentPage>1?!0:!1,pageNumber:n.currentPage-1},nextLink:{show:n.currentPage<n.numberOfPages?!0:!1,pageNumber:n.currentPage+1},pages:e.map(e.range(1,n.numberOfPages+1),function(e){return{pageNumber:e,current:e==n.currentPage}})},this.reset(n.resultSet)},toViewModel:function(){return{searchTerm:this.searchTerm,pagination:this.paginationData,users:this.constructor.__super__.toViewModel.apply(this)}}}),o=t.Model.extend({defaults:{members:{}},addInitData:function(e,t){var n=this.get("members");n[e]={userId:e,editor:t},this.set("members",n,{silent:!0})},isUserOwner:function(e){return e==this.get("ownerId")},isUserView:function(e){return e==this.get("viewerId")},isUserMember:function(e){return this.get("members")[e]?!0:!1},isUserEditor:function(e){var t=this.get("members")[e];return t&&t.editor},addMember:function(e){var t=this;r.api.rpc.addMemberToPage({pageId:t.get("id"),userId:e,successCallback:function(n){var r=t.get("members");r[e]={userId:e,editor:!1},t.set("members",r),t.trigger("share","member:add",e)}})},removeMember:function(e){var t=this;r.api.rpc.removeMemberFromPage({pageId:t.get("id"),userId:e,successCallback:function(n){var r=t.get("members");delete r[e],t.set("members",r),t.trigger("share","member:remove",e)}})},removeForSelf:function(){var e=this;r.api.rpc.removeMemberFromPage({pageId:e.get("id"),userId:e.get("viewerId"),successCallback:function(){e.trigger("declineShare",e.get("id"))}})},addEditor:function(e){var t=this;r.api.rpc.updatePageEditingStatus({pageId:t.get("id"),userId:e,isEditor:!0,successCallback:function(){var n=t.get("members");n[e]={userId:e,editor:!0},t.set("members",n),t.trigger("share","editor:add",e)}})},removeEditor:function(e){var t=this;r.api.rpc.updatePageEditingStatus({pageId:t.get("id"),userId:e,isEditor:!1,successCallback:function(){var n=t.get("members");n[e]={userId:e,editor:!1},t.set("members",n),t.trigger("share","editor:remove",e)}})},cloneForUser:function(e,t){t=t||null;var n=this;r.api.rpc.clonePageForUser({pageId:this.get("id"),userId:e,pageName:t,successCallback:function(t){if(t.error)return n.trigger("error",t.errorCode,e);n.trigger("share","clone",e)}})},acceptShare:function(){var e=this;r.api.rpc.updateSharedPageStatus({pageId:this.get("id"),shareStatus:"accepted",successCallback:function(t){e.trigger("acceptShare",e.get("id"))}})},declineShare:function(){var e=this;r.api.rpc.updateSharedPageStatus({pageId:this.get("id"),shareStatus:"refused",successCallback:function(t){r.api.rpc.removeMemberFromPage({pageId:e.get("id"),userId:e.get("viewerId"),successCallback:function(t){e.trigger("declineShare",e.get("id"))}})}})}});return{currentPage:new o,users:new s}}),define("portal/rave_layout",["jquery","rave","portal/rave_portal","portal/rave_models","bootstrap","jqueryValidate"],function($,rave,ravePortal,raveModels){function moveWidgetToPage(e){var t=$("#moveToPageId").val(),n={toPageId:t,regionWidgetId:e,successCallback:function(){ravePortal.viewPage(t)}};rave.api.rpc.moveWidgetToPage(n)}function addPage(){if(showImportExportUI){if($pageFormTabbed.valid()){var e=$("#tab_titleTabbed1").val(),t=$("#pageLayoutTabbed").val();rave.api.rpc.addPage({pageName:e,pageLayoutCode:t,errorLabel:"pageFormErrorsTabbed1",successCallback:function(e){ravePortal.viewPage(e.result.id)},errorCallback:function(e){$("#"+e).html(ravePortal.getClientMessage("page.duplicate_name"))}})}}else if($pageForm.valid()){var e=$tab_title_input.val(),t=$page_layout_input.val();rave.api.rpc.addPage({pageName:e,pageLayoutCode:t,errorLabel:"pageFormErrors",successCallback:function(e){ravePortal.viewPage(e.result.id)},errorCallback:function(e){$("#"+e).html(ravePortal.getClientMessage("page.duplicate_name"))}})}}function importPage(){$.browser.msie==1?alert(ravePortal.getClientMessage("import.page.not.supported")):$pageFormImport.valid()&&($pageFormImport.get(0).setAttribute("action",rave.getContext()+"api/rpc/page/import/omdl"),document.getElementById("pageFormImport").onsubmit=function(){document.getElementById("pageFormImport").target="file_upload_frame",document.getElementById("file_upload_frame").onload=processFileUploadResult},$pageFormImport.submit())}function processFileUploadResult(){var ret=frames.file_upload_frame.document.getElementsByTagName("body")[0].firstChild.innerHTML,data=eval("("+ret+")");if(data.error)if(data.errorCode=="DUPLICATE_ITEM")$("#pageFormErrorsTabbed2").html(ravePortal.getClientMessage("page.duplicate_name"));else if(data.errorCode=="INTERNAL_ERROR"&&data.errorMessage.indexOf("BadOmdlXmlFormatException")!=-1){var msg=data.errorMessage.substr(data.errorMessage.indexOf("BadOmdlXmlFormatException"),data.errorMessage.length);$("#pageFormErrorsTabbed2").html(msg)}else alert(ravePortal.getClientMessage("api.rpc.error.internal"));else ravePortal.viewPage(data.result.id)}function addOrImportPage(){var e=$("#page-tabs").tabs("option","selected");e==0?addPage():importPage()}function movePage(){var e=$("#moveAfterPageId").val(),t={pageId:$("#currentPageId").val(),successCallback:function(e){ravePortal.viewPage(e.result.id)}};e!=MOVE_PAGE_DEFAULT_POSITION_IDX&&(t.moveAfterPageId=e),rave.api.rpc.movePage(t)}function updatePage(){$pageForm.valid()&&rave.api.rpc.updatePagePrefs({pageId:$tab_id.val(),title:$tab_title_input.val(),layout:$page_layout_input.val(),errorLabel:"pageFormErrors",successCallback:function(e){ravePortal.viewPage(e.result.id)},errorCallback:function(e){$("#"+e).html(ravePortal.getClientMessage("page.duplicate_name"))}})}function closePageDialog(){$pageForm[0].reset(),$tab_id.val(""),$("#pageFormErrors").text(""),$("#pageMenuDialog").modal("hide")}function closePageDialogTabbed(){$pageForm[0].reset(),$("#pageFormErrors").text(""),$("#pageFormErrorsTabbed1").text(""),$("#pageFormErrorsTabbed2").text(""),$pageFormTabbed[0].reset(),$pageFormImport[0].reset(),$("#pageMenuDialogTabbed").modal("hide")}function getCurrentPageId(){return rave.getPage().id}function isWidgetOnHiddenTab(e){var t=decodeURIComponent(location.hash),n=e.subPage;return!(n.id===null||"#"+n.name===t||t===""&&n.isDefault)}function init(){pageMenu.init(rave.getExportEnabled())}var MOVE_PAGE_DEFAULT_POSITION_IDX=-1,$moveWidgetDialog,showImportExportUI=!1,$movePageDialog=$("#movePageDialog"),$pageFormTabbed=$("#pageFormTabbed"),$pageFormImport=$("#pageFormImport"),$omdlFile=$("#omdlFile"),$pageForm=$("#pageForm"),$tab_title_input=$("#tab_title"),$tab_id=$("#tab_id"),$page_layout_input=$("#pageLayout"),pageMenu=function(){function u(i){showImportExportUI=i,$pageForm.validate({errorElement:"span",errorClass:"help-inline",highlight:function(e,t){$(e).parent().parent().addClass("error")},unhighlight:function(e,t){$(e).parent().parent().removeClass("error")}}),$pageFormTabbed.validate({errorElement:"span",errorClass:"help-inline",highlight:function(e,t){$(e).parent().parent().addClass("error")},unhighlight:function(e,t){$(e).parent().parent().removeClass("error")}}),$pageFormImport.validate({errorElement:"span",errorClass:"help-inline",highlight:function(e,t){$(e).parent().parent().addClass("error")},unhighlight:function(e,t){$(e).parent().parent().removeClass("error")}}),$("#pageMenuCloseButton").click(closePageDialog),$("#pageMenuCloseButtonTab").click(closePageDialogTabbed),i&&$("#pageMenuExport").removeClass("hidden"),e.bind("click",function(e){if(i){var t=$("#pageMenuUpdateButtonTab");t.html(ravePortal.getClientMessage("common.add")),$("#page-tabs").tabs(),t.click(addOrImportPage),$("#pageMenuDialogTabbed").on("shown",function(){$("#tab_title").first().focus()}),$("#pageMenuDialogTabbed").modal("show")}else{$("#pageMenuDialogHeader").html(ravePortal.getClientMessage("page.add"));var n=$("#pageMenuUpdateButton");$("#pageLayoutGroup").show(),n.html(ravePortal.getClientMessage("common.add")),n.unbind("click"),n.click(addPage),$("#pageMenuDialog").on("shown",function(){$("#tab_title").first().focus()}),$("#pageMenuDialog").modal("show")}}),t.bind("click",function(e){rave.api.rpc.getPagePrefs({pageId:getCurrentPageId(),successCallback:function(e){$tab_title_input.val(e.result.name),$tab_id.val(e.result.id),$page_layout_input.val(e.result.pageLayout.code),$("#pageMenuDialogHeader").html(ravePortal.getClientMessage("page.update"));var t=$("#pageMenuUpdateButton");$("#pageLayoutGroup").show(),t.html(ravePortal.getClientMessage("common.save")),t.unbind("click"),t.click(updatePage),$("#pageMenuDialog").on("shown",function(){$("#tab_title").first().focus()}),$("#pageMenuDialog").modal("show")}})}),n.hasClass("menu-item-disabled")||n.bind("click",function(e){rave.api.rest.deletePage({pageId:getCurrentPageId(),successCallback:ravePortal.viewPage})}),r.hasClass("menu-item-disabled")||r.bind("click",function(e){$movePageDialog.modal("show")}),o.hasClass("hidden")||o.bind("click",function(e){window.open(rave.getContext()+"api/rest/"+"page/"+getCurrentPageId()+"/omdl")}),s.hasClass("menu-item-disabled")||s.bind("click",function(e){raveModels.currentPage.removeForSelf()})}var e=$("#addPageButton"),t=$("#pageMenuEdit"),n=$("#pageMenuDelete"),r=$("#pageMenuMove"),i=$("#pageMenuShare"),s=$("#pageMenuRevokeShare"),o=$("#pageMenuExport");return{init:u}}();return rave.registerOnInitHandler(init),{addPage:addPage,addOrImportPage:addOrImportPage,updatePage:updatePage,movePage:movePage,importPage:importPage,closePageDialog:closePageDialog,closePageDialogTabbed:closePageDialogTabbed,moveWidgetToPage:moveWidgetToPage,isWidgetOnHiddenTab:isWidgetOnHiddenTab}}),define("portal/rave_ui",["jquery","underscore","rave","portal/rave_portal","portal/rave_backbone","portal/rave_models","portal/rave_templates","jqueryUi"],function(e,t,n,r,i,s,o){function l(){var n=i.View.extend({template:o.templates["user-search-view"],modalDiv:e("#sharePageDialog"),container:e("#sharePageDialogContent"),models:{page:s.currentPage,users:s.users},initialize:function(){var e=this.models.page,n=this.models.users;this.constructor.__super__.initialize.apply(this),e.on("share",this.flash,this),e.on("error",this.handleError,this),this.modalDiv.on("show",function(){n.fetchPage(1)}),this.models.users.toViewModel=t.wrap(this.models.users.toViewModel,function(n){var r=n.apply(this);return t.each(r.users,function(t){t.isOwner=e.isUserOwner(t.id),t.hasShare=e.isUserMember(t.id),t.hasEdit=e.isUserEditor(t.id)}),r}),this.container.html(this.$el)},events:{"click #shareSearchButton":"search","keypress #searchTerm":"search","click #clearSearchButton":"clearSearch","click #pagingul a":"page","click .searchResultRecord a":"shareAction"},search:function(n){if(n.which==13||t.isUndefined(n.which)){var r=e("#searchTerm",this.$el).val();this.models.users.filter(r)}},clearSearch:function(e){this.models.users.filter(null)},page:function(t){var n=e(t.target).data("pagenumber");this.models.users.fetchPage(n)},shareAction:function(t){var n=e(t.target).data("userid"),r=e(t.target).data("action");this.models.page[r](n)},flash:function(e,t){var n={"member:add":"create.share","member:remove":"revoke.share","editor:add":"","editor:remove":"",clone:"success.clone.page"},i=n[e];if(i){var s=r.getClientMessage(i),o=this.models.users.get(t);r.showInfoMessage("("+o.get("username")+") "+s)}},handleError:function(t,n){var i=this;if(t=="DUPLICATE_ITEM"){this.modalDiv.modal("hide"),e("#pageMenuDialogHeader").html(r.getClientMessage("page.update")),e("#pageFormErrors").html(r.getClientMessage("page.duplicate_name")),e("#pageLayoutGroup").hide();var s=e("#pageMenuUpdateButton");s.html(r.getClientMessage("common.save")),s.unbind("click"),s.click(function(){var t=e("#pageForm"),r=e("#tab_title");t.valid()&&(i.models.page.cloneForUser(n,r.val()),e("#pageMenuDialog").modal("hide"))}),e("#pageMenuDialog").on("shown",function(){e("#tab_title").first().focus()}),e("#pageMenuDialog").modal("show")}else e("#pageMenuDialog").modal("hide"),alert(r.getClientMessage("api.rpc.error.internal"))}});a.pageSharingModal=new n}function c(){e("#emptyPageMessageWrapper").removeClass("hidden")}function h(){t.isEmpty(n.getWidgets())&&c()}function p(){return e(".region:not(.region-locked)")}function d(){function i(i,s){a();var o=p();o.addClass("regionDragging"),o.removeClass("regionNonDragging");var u=s.item.children(".widget").get(0);t.widget=n.getWidget(r.getObjectIdFromDomId(u.id)),t.currentRegion=r.getObjectIdFromDomId(s.item.parent().get(0).id),e(".widget").each(function(t,n){v(e(n))})}function s(n,i){var s=p();if(e(".widgetRow").length){var u=s.find(".widgetRow");u.each(h)}s.removeClass("regionDragging"),s.addClass("regionNonDragging"),e(".dnd-overlay").remove(),i.item.attr("style",""),t.targetRegion=r.getObjectIdFromDomId(i.item.parent().get(0).id),t.targetIndex=i.item.index(),t.widget.moveToRegion(t.currentRegion,t.targetRegion,t.targetIndex),o()}function o(){t.currentRegion=null,t.targetRegion=null,t.targetIndex=null,t.widget=null}function u(e,t){a()}function a(){if(e(".upperRow").length){var t=e(".regions").find(".upperRow");t.each(l)}if(e(".bottomRow").length){var n=e(".regions").find(".bottomRow");f(n)}}function f(t){h(t);var n=e("body").outerHeight(),r=e(window).height();if(r>=n){var i=e("#pageContent").outerHeight(),s=n-i,o=0;if(e(".upperRow").length){var u=e(".regions").find(".upperRow");for(var a=0;a<u.length;a++){var f=c(u.get(a));o+=f}}var v=r-50-(o+s);d(t,v)}else l(t);p().sortable("refreshPositions")}function l(t){var t=typeof t=="number"?e(this):t;h(t),d(t,c(t)),p().sortable("refreshPositions")}function c(t){var n=e(t).children(),r=100;for(var i=0;i<n.length;i++)e(n.get(i)).outerHeight()>r&&(r=e(n.get(i)).outerHeight());return r}function h(t){var t=typeof t=="number"?e(this):t,n=e(t).children();for(var r=0;r<n.length;r++)e(n.get(r)).css("padding-bottom",5)}function d(t,n){var r=e(t).children();for(var i=0;i<r.length;i++)if(e(r.get(i)).outerHeight()!=n){var s=parseInt(e(r.get(i)).css("padding-bottom").replace("px",""));e(r.get(i)).css("padding-bottom",s+n-e(r.get(i)).outerHeight())}}function v(t){var n=e("<div></div>"),r={position:"absolute",height:t.height(),width:t.width(),"z-index":10,opacity:.7,background:"#FFFFFF"};e(n).css(r),e(n).addClass("dnd-overlay"),t.prepend(n[0])}var t={widget:null,currentRegion:null,targetRegion:null,targetIndex:null};p().sortable({connectWith:".region",scroll:!0,opacity:.5,revert:!0,cursor:"move",handle:".widget-title-bar-draggable",forcePlaceholderSize:!0,tolerance:"pointer",start:i,stop:s,over:u})}function v(i){function f(e){return"widget-"+e+"-prefs-content"}function m(t){var n=i,s=e("#"+f(n.regionWidgetId)),o={},a=!1;s.find("*").filter(":input").each(function(t,n){switch(n.type){case"text":y(n)?(o[n.name]=e(n).val(),e(n).removeClass(u)):(a=!0,e(n).addClass(u));break;case"select-one":case"hidden":o[n.name]=e(n).val();break;case"checkbox":o[n.name]=e(n).is(":checked").toString();break;case"textarea":if(!y(n))a=!0,e(n).addClass(u);else{var r=[],i=e(n).val().split("\n");for(var s=0;s<i.length;s++){var f=e.trim(i[s]);f.length>0&&r.push(f)}o[n.name]=r.join("|"),e(n).removeClass(u)}}}),a?s.find("."+u).first().focus():(r.isPageEditor()&&(i.savePreferences(o),i.render(i._el)),s.html(""),s.hide())}function g(t){var n=e("#"+f(t.data.id));n.html(""),n.hide()}function y(t){var n=!0,r=e(t);return r.hasClass(s)&&(n=e.trim(r.val()).length>0),n}var s="widget-prefs-input-required",u="widget-prefs-input-failed-validation",a=/\|/g,l=i,c=l.metadata.userPrefs,h=!1;if(l.metadata.views.preferences)i._view=n.renderView("preferences",l);else{t.each(c,function(e){e.value=l.userPrefs[e.name]||e.defaultValue,e.required&&(h=!0);if(e.dataType=="ENUM"){var n=t.find(e.orderedEnumValues,function(t){return t.value==e.value});n.selected=!0}e.dataType=="LIST"&&(e.value=e.value.replace(a,"\n")),e[e.dataType]=!0});var p=o.templates["widget-preferences"]({userPrefs:c,hasRequiredUserPrefs:h}),d=e(p);e(".prefs-save-button",d).click({id:l.regionWidgetId},m),e(".prefs-cancel-button",d).click({id:l.regionWidgetId},g);var v=e("#widget-"+l.regionWidgetId+"-prefs-content");v.html(d),v.show()}}function m(){n.RegionWidget.extend({renderError:function(e,t){e.innerHTML=r.getClientMessage("opensocial.render_error")+"<br /><br />"+t}});var t=function(t){this.widget=t;var n=t.regionWidgetId;this.$chrome=e("#widget-"+n+"-wrapper"),this.$minimizeIcon=e("#widget-"+n+"-min"),this.$toggleCollapseIcon=e("#widget-"+n+"-collapse"),this.$cogIcon=e("#widget-"+n+"-menu-button"),this.$menuItemMove=e("#widget-"+n+"-menu-move-item"),this.$menuItemDelete=e("#widget-"+n+"-menu-delete-item"),this.$menuItemMaximize=e("#widget-"+n+"-menu-maximize-item"),this.$menuItemAbout=e("#widget-"+n+"-menu-about-item"),this.$menuItemComment=e("#widget-"+n+"-menu-comment-item"),this.$menuItemRate=e("#widget-"+n+"-menu-rate-item"),this.$menuItemEditPrefs=e("#widget-"+n+"-menu-editprefs-item"),this.$widgetSite=e("#widget-"+n+"-body"),this.$title=e("#widget-"+n+"-title")};t.prototype.render=function(t){function u(){return e(".dnd-overlay, .canvas-overlay").remove(),p().sortable("option","disabled",!1),e("#widget-"+i+"-wrapper").removeClass("widget-wrapper-canvas").addClass("widget-wrapper"),e("#widget-"+i+"-min").hide(),e("#widget-"+i+"-widget-menu-wrapper").show(),e("#widget-"+i+"-collapse").show(),!1}function a(t){var n=0;e(".widget").each(function(){var t=e(this).outerWidth(),n=e(this).outerHeight(),r=e(this).position().left,i=e(this).position().top;e(this).after('<div class="iframe-overlay" />'),e(this).next(".iframe-overlay").css({width:t,height:n,position:"absolute",top:i,left:r})}),e(".iframe-overlay").click(function(){o.$cogIcon.dropdown("toggle"),e(".iframe-overlay").remove()}),e("*:not(.dropdown-toggle)").on("click",function(){e(".iframe-overlay").remove()})}function f(){o.widget.render("home")}function l(){o.widget.render("canvas",{view:"canvas"})}function c(){var e=o.widget.collapsed;return e?o.widget.show():o.widget.hide(),h(),!1}function h(){var n=!t.collapsed;e(o.$widgetSite).toggle(n),e("i",o.$toggleCollapseIcon).toggleClass("icon-chevron-down",n),e("i",o.$toggleCollapseIcon).toggleClass("icon-chevron-up",!n)}function d(){return e(".dropdown").removeClass("open"),e("#moveWidgetModal").data("regionWidgetId",i),e("#moveWidgetModal").modal("show"),!1}function m(){return o.widget.close(),!1}function g(){return r.viewWidgetDetail(s,n.getPage().id),!1}function y(){return r.viewWidgetDetail(s,n.getPage().id,"widgetComments"),!1}function b(){return r.viewWidgetDetail(s,n.getPage().id,"widgetRatings"),!1}function w(){return t._view.name!=="preferences"&&v(t),!1}function S(e){return function(t){o.$cogIcon.dropdown(),e(t)}}var i=this.widget.regionWidgetId,s=this.widget.widgetId,o=this;h(),u(),e("#widget-"+i+"-toolbar").mousedown(function(e){e.stopPropagation()}),o.$minimizeIcon.click(f),o.$toggleCollapseIcon.click(c),o.$cogIcon.click(a),o.$cogIcon.dropdown(),o.$menuItemMove.hasClass("menu-item-disabled")||o.$menuItemMove.click(S(d)),o.$menuItemDelete.hasClass("menu-item-disabled")||o.$menuItemDelete.click(S(m)),o.$menuItemMaximize.hasClass("menu-item-disabled")||o.$menuItemMaximize.click(S(l)),o.$menuItemAbout.hasClass("menu-item-disabled")||o.$menuItemAbout.click(S(g)),o.$menuItemComment.hasClass("menu-item-disabled")||o.$menuItemComment.click(S(y)),o.$menuItemRate.hasClass("menu-item-disabled")||o.$menuItemRate.click(S(b));var E=o.widget.metadata;E&&(E.hasPrefsToEdit||E.views&&E.views.preferences)&&(o.$menuItemEditPrefs.removeClass("menu-item-disabled"),o.$menuItemEditPrefs.click(S(w)))},t.prototype.getWidgetSite=function(){return this.$widgetSite[0]},t.prototype.destroy=function(e){this.$chrome.remove(),r.isPageEmpty()&&c()},t.prototype.expand=function(){this.$chrome.show()},t.prototype.collapse=function(){this.$chrome.hide()},t.prototype.setTitle=function(e){this.$title.html(e)},n.registerView("home",t)}function g(){var e=function(e){this.widget=e,this.name="preferences"};e.prototype.render=function(){var e={};t.extend(e,this.widget.metadata.views.preferences,{view:"preferences"}),this.view=n.renderView("modal_dialog");var r=this.view.getWidgetSite();this.widget.render(r,e)},e.prototype.getWidgetSite=function(){return this.view.getWidgetSite()},e.prototype.destroy=function(){this.widget._provider.closeWidget(this.widget),n.destroyView(this.view)},n.registerView("preferences",e)}function y(){var t=function(t){this.widget=t,this.$title=e("#widget-"+t.regionWidgetId+"-title")};t.prototype.render=function(t){function r(t){i(e("#pageContent")),p().sortable("option","disabled",!0),e("#widget-"+t+"-wrapper").removeClass("widget-wrapper").addClass("widget-wrapper-canvas"),e("#widget-"+t+"-widget-menu-wrapper").hide(),e("#widget-"+t+"-min").show(),e("#widget-"+t+"-collapse").hide()}function i(t){if(e(".canvas-overlay").length>0)return;var n=e("<div></div>"),r={height:e("body").height()-40};e(n).css(r),e(n).addClass("canvas-overlay"),t.prepend(n[0])}this.widget=t,r(t.regionWidgetId);var n={height:e(".wrapper").height()-e(".navbar").height()-e(".logo-wrapper").height()};e(".canvas-overlay").css(n),t.render(t._el,{view:"canvas"})},t.prototype.getWidgetSite=function(){return e("#widget-"+this.widget.regionWidgetId+"-body")[0]},t.prototype.destroy=function(){return e(".dnd-overlay, .canvas-overlay").remove(),p().sortable("option","disabled",!1),e("#widget-"+widgetId+"-wrapper").removeClass("widget-wrapper-canvas").addClass("widget-wrapper"),e("#widget-"+widgetId+"-min").hide(),e("#widget-"+widgetId+"-widget-menu-wrapper").show(),e("#widget-"+widgetId+"-collapse").show(),!1},t.prototype.setTitle=function(e){this.$title.html(e)},n.registerView("canvas",t)}function b(){var r={sidebar:{name:"sidebar",containerSelector:".popup.slideout",contentSelector:".slideout-content",template:"popup-slideout",initialize:function(t){var n=this,r=t.find(this.contentSelector);r.data("popupType",this.name),t.find(".close").click(function(){n.cleanup(r)}),t.show("slide",{direction:"right"},"fast"),e("body").addClass("modal-open"),e("body").append('<div class="modal-backdrop fade in"></div>'),e("body").addClass("no-scroll")},cleanup:function(t){var n=t.parents(this.containerSelector);n.hide("slide",{direction:"right"},"fast",function(){n.remove(),e("body").removeClass("modal-open"),e(".modal-backdrop").remove(),e("body").removeClass("no-scroll")})},singleton:!0},dialog:{name:"dialog",containerSelector:".popup.dialog",contentSelector:".modal-body",template:"popup-dialog",initialize:function(e){e.find(this.contentSelector).data("popupType",this.name);var t={};e.modal(t),e.on("hidden",function(){e.remove()})},cleanup:function(e){var t=e.parents(this.containerSelector);t.modal("hide")},singleton:!1},modal_dialog:{name:"modal_dialog",containerSelector:".popup.modal_dialog",contentSelector:".modal-body",template:"popup-modal",initialize:function(e){e.find(this.contentSelector).data("popupType",this.name);var t={keyboard:!1,backdrop:"static",show:!0};e.modal(t),e.on("hidden",function(){e.remove()})},cleanup:function(e){var t=e.parents(this.containerSelector);t.modal("hide")},singleton:!0}};t.each(r,function(r){var i,s;n.registerView(r.name,{render:function(n){i=e(o.templates[r.template]()),s=e(r.contentSelector,i);var u=n&&n.preferredHeight,a=n&&n.preferredWidth;return u&&i.height(u),a&&i.width(a),e("#pageContent").prepend(i),t.isFunction(r.initialize)&&r.initialize(i),this},getWidgetSite:function(){return s[0]},destroy:function(){t.isFunction(r.cleanup)?r.cleanup(s):i.remove()}})})}function w(t,n,r){var i;return n?n.indexOf("css")===0?i='<i class="'+n.replace("css:","")+'" ></i>':i='<img src="'+n+'" />':i="<a>"+t+"</a>",e(i).attr("tooltip",r)}function E(t,n,r,i,s,o){var u=e("#widget-"+t+"-wrapper .widget-title-bar"),a=e('<div class="widget-titlebar-action widget-toolbar" ></div>').append(w(n,r,i));return a.attr("id",s).on("click",o),u.append(a),a}function S(){var e={};n.registerActionHandler({create:function(t,r,i,s,o,u,a){if(!e[t]){var f=i.split("/");f.length===2&&f[0]==="widget"&&f[1]==="toolbar"?e[t]=E(s,r,o,u,t,a):n.log("Unsupported action path: '"+i+"'")}},remove:function(t){e[t]&&e[t].remove()}})}function x(){l(),g(),m(),y(),b(),h(),d(),S()}var u={},a=u.views={},f=i.View.extend({models:{page:s.currentPage},initialize:function(){this.models.page.on("acceptShare",r.viewPage),this.models.page.on("declineShare",function(){document.location.href=n.getContext()})}});return a.app=new f,u.displayEmptyPageMessage=c,n.registerOnInitHandler(x),u}),define("portal/rave_forms",["jquery","portal/rave_portal","jqueryValidate"],function(e,t){function n(){e("#newAccountForm").validate({rules:{username:{required:!0,minlength:2},password:{required:!0,minlength:4},confirmPassword:{required:!0,minlength:4,equalTo:"#passwordField"},email:{required:!0,email:!0}},messages:{confirmPassword:{equalTo:t.getClientMessage("form.password.invalid_match")}}})}function r(){e("#userProfileForm").validate({rules:{password:{required:!0,minlength:4},passwordConfirm:{required:!0,minlength:4,equalTo:"#passwordField"}},messages:{passwordConfirm:{equalTo:t.getClientMessage("form.password.invalid_match")}}})}function i(){e("#editAccountForm").validate({rules:{email:{required:!0,email:!0}}})}function s(){e("#pageForm").validate({rules:{tab_title:{required:!0}}})}return{validateNewAccountForm:n,validateUserProfileForm:r,validateEditAccountForm:i,validatePageForm:s}}),define("portal/rave_display",["rave","portal/rave_templates","jqueryUi"],function(e,t){function n(n){e.api.rest.getUsersForWidget({widgetId:n,successCallback:function(e){_.each(e,function(e){e.name=e.displayName||e.preferredName||e.givenName+" "+e.familyName});var r=t.templates["users-of-widget"]({users:e,widgetName:$("#widget-"+n+"-title").text().trim()});$(r).dialog({modal:!0,buttons:[{text:"Close",click:function(){$(this).dialog("close")}}]})}})}return{displayUsersOfWidget:n}}),define("ui",["underscore","portal/rave_portal","portal/rave_models","portal/rave_layout","portal/rave_ui","portal/rave_forms","portal/rave_display"],function(e,t,n,r,i,s,o){return t.models=n,t.layout=r,e.extend(t,i),t.forms=s,e.extend(t,o),t});