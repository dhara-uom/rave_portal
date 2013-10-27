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

define(["jquery","underscore","rave","portal/rave_portal","portal/rave_backbone","portal/rave_models","portal/rave_templates","jqueryUi"],function(e,t,n,r,i,s,o){function l(){var n=i.View.extend({template:o.templates["user-search-view"],modalDiv:e("#sharePageDialog"),container:e("#sharePageDialogContent"),models:{page:s.currentPage,users:s.users},initialize:function(){var e=this.models.page,n=this.models.users;this.constructor.__super__.initialize.apply(this),e.on("share",this.flash,this),e.on("error",this.handleError,this),this.modalDiv.on("show",function(){n.fetchPage(1)}),this.models.users.toViewModel=t.wrap(this.models.users.toViewModel,function(n){var r=n.apply(this);return t.each(r.users,function(t){t.isOwner=e.isUserOwner(t.id),t.hasShare=e.isUserMember(t.id),t.hasEdit=e.isUserEditor(t.id)}),r}),this.container.html(this.$el)},events:{"click #shareSearchButton":"search","keypress #searchTerm":"search","click #clearSearchButton":"clearSearch","click #pagingul a":"page","click .searchResultRecord a":"shareAction"},search:function(n){if(n.which==13||t.isUndefined(n.which)){var r=e("#searchTerm",this.$el).val();this.models.users.filter(r)}},clearSearch:function(e){this.models.users.filter(null)},page:function(t){var n=e(t.target).data("pagenumber");this.models.users.fetchPage(n)},shareAction:function(t){var n=e(t.target).data("userid"),r=e(t.target).data("action");this.models.page[r](n)},flash:function(e,t){var n={"member:add":"create.share","member:remove":"revoke.share","editor:add":"","editor:remove":"",clone:"success.clone.page"},i=n[e];if(i){var s=r.getClientMessage(i),o=this.models.users.get(t);r.showInfoMessage("("+o.get("username")+") "+s)}},handleError:function(t,n){var i=this;if(t=="DUPLICATE_ITEM"){this.modalDiv.modal("hide"),e("#pageMenuDialogHeader").html(r.getClientMessage("page.update")),e("#pageFormErrors").html(r.getClientMessage("page.duplicate_name")),e("#pageLayoutGroup").hide();var s=e("#pageMenuUpdateButton");s.html(r.getClientMessage("common.save")),s.unbind("click"),s.click(function(){var t=e("#pageForm"),r=e("#tab_title");t.valid()&&(i.models.page.cloneForUser(n,r.val()),e("#pageMenuDialog").modal("hide"))}),e("#pageMenuDialog").on("shown",function(){e("#tab_title").first().focus()}),e("#pageMenuDialog").modal("show")}else e("#pageMenuDialog").modal("hide"),alert(r.getClientMessage("api.rpc.error.internal"))}});a.pageSharingModal=new n}function c(){e("#emptyPageMessageWrapper").removeClass("hidden")}function h(){t.isEmpty(n.getWidgets())&&c()}function p(){return e(".region:not(.region-locked)")}function d(){function i(i,s){a();var o=p();o.addClass("regionDragging"),o.removeClass("regionNonDragging");var u=s.item.children(".widget").get(0);t.widget=n.getWidget(r.getObjectIdFromDomId(u.id)),t.currentRegion=r.getObjectIdFromDomId(s.item.parent().get(0).id),e(".widget").each(function(t,n){v(e(n))})}function s(n,i){var s=p();if(e(".widgetRow").length){var u=s.find(".widgetRow");u.each(h)}s.removeClass("regionDragging"),s.addClass("regionNonDragging"),e(".dnd-overlay").remove(),i.item.attr("style",""),t.targetRegion=r.getObjectIdFromDomId(i.item.parent().get(0).id),t.targetIndex=i.item.index(),t.widget.moveToRegion(t.currentRegion,t.targetRegion,t.targetIndex),o()}function o(){t.currentRegion=null,t.targetRegion=null,t.targetIndex=null,t.widget=null}function u(e,t){a()}function a(){if(e(".upperRow").length){var t=e(".regions").find(".upperRow");t.each(l)}if(e(".bottomRow").length){var n=e(".regions").find(".bottomRow");f(n)}}function f(t){h(t);var n=e("body").outerHeight(),r=e(window).height();if(r>=n){var i=e("#pageContent").outerHeight(),s=n-i,o=0;if(e(".upperRow").length){var u=e(".regions").find(".upperRow");for(var a=0;a<u.length;a++){var f=c(u.get(a));o+=f}}var v=r-50-(o+s);d(t,v)}else l(t);p().sortable("refreshPositions")}function l(t){var t=typeof t=="number"?e(this):t;h(t),d(t,c(t)),p().sortable("refreshPositions")}function c(t){var n=e(t).children(),r=100;for(var i=0;i<n.length;i++)e(n.get(i)).outerHeight()>r&&(r=e(n.get(i)).outerHeight());return r}function h(t){var t=typeof t=="number"?e(this):t,n=e(t).children();for(var r=0;r<n.length;r++)e(n.get(r)).css("padding-bottom",5)}function d(t,n){var r=e(t).children();for(var i=0;i<r.length;i++)if(e(r.get(i)).outerHeight()!=n){var s=parseInt(e(r.get(i)).css("padding-bottom").replace("px",""));e(r.get(i)).css("padding-bottom",s+n-e(r.get(i)).outerHeight())}}function v(t){var n=e("<div></div>"),r={position:"absolute",height:t.height(),width:t.width(),"z-index":10,opacity:.7,background:"#FFFFFF"};e(n).css(r),e(n).addClass("dnd-overlay"),t.prepend(n[0])}var t={widget:null,currentRegion:null,targetRegion:null,targetIndex:null};p().sortable({connectWith:".region",scroll:!0,opacity:.5,revert:!0,cursor:"move",handle:".widget-title-bar-draggable",forcePlaceholderSize:!0,tolerance:"pointer",start:i,stop:s,over:u})}function v(i){function f(e){return"widget-"+e+"-prefs-content"}function m(t){var n=i,s=e("#"+f(n.regionWidgetId)),o={},a=!1;s.find("*").filter(":input").each(function(t,n){switch(n.type){case"text":y(n)?(o[n.name]=e(n).val(),e(n).removeClass(u)):(a=!0,e(n).addClass(u));break;case"select-one":case"hidden":o[n.name]=e(n).val();break;case"checkbox":o[n.name]=e(n).is(":checked").toString();break;case"textarea":if(!y(n))a=!0,e(n).addClass(u);else{var r=[],i=e(n).val().split("\n");for(var s=0;s<i.length;s++){var f=e.trim(i[s]);f.length>0&&r.push(f)}o[n.name]=r.join("|"),e(n).removeClass(u)}}}),a?s.find("."+u).first().focus():(r.isPageEditor()&&(i.savePreferences(o),i.render(i._el)),s.html(""),s.hide())}function g(t){var n=e("#"+f(t.data.id));n.html(""),n.hide()}function y(t){var n=!0,r=e(t);return r.hasClass(s)&&(n=e.trim(r.val()).length>0),n}var s="widget-prefs-input-required",u="widget-prefs-input-failed-validation",a=/\|/g,l=i,c=l.metadata.userPrefs,h=!1;if(l.metadata.views.preferences)i._view=n.renderView("preferences",l);else{t.each(c,function(e){e.value=l.userPrefs[e.name]||e.defaultValue,e.required&&(h=!0);if(e.dataType=="ENUM"){var n=t.find(e.orderedEnumValues,function(t){return t.value==e.value});n.selected=!0}e.dataType=="LIST"&&(e.value=e.value.replace(a,"\n")),e[e.dataType]=!0});var p=o.templates["widget-preferences"]({userPrefs:c,hasRequiredUserPrefs:h}),d=e(p);e(".prefs-save-button",d).click({id:l.regionWidgetId},m),e(".prefs-cancel-button",d).click({id:l.regionWidgetId},g);var v=e("#widget-"+l.regionWidgetId+"-prefs-content");v.html(d),v.show()}}function m(){n.RegionWidget.extend({renderError:function(e,t){e.innerHTML=r.getClientMessage("opensocial.render_error")+"<br /><br />"+t}});var t=function(t){this.widget=t;var n=t.regionWidgetId;this.$chrome=e("#widget-"+n+"-wrapper"),this.$minimizeIcon=e("#widget-"+n+"-min"),this.$toggleCollapseIcon=e("#widget-"+n+"-collapse"),this.$cogIcon=e("#widget-"+n+"-menu-button"),this.$menuItemMove=e("#widget-"+n+"-menu-move-item"),this.$menuItemDelete=e("#widget-"+n+"-menu-delete-item"),this.$menuItemMaximize=e("#widget-"+n+"-menu-maximize-item"),this.$menuItemAbout=e("#widget-"+n+"-menu-about-item"),this.$menuItemComment=e("#widget-"+n+"-menu-comment-item"),this.$menuItemRate=e("#widget-"+n+"-menu-rate-item"),this.$menuItemEditPrefs=e("#widget-"+n+"-menu-editprefs-item"),this.$widgetSite=e("#widget-"+n+"-body"),this.$title=e("#widget-"+n+"-title")};t.prototype.render=function(t){function u(){return e(".dnd-overlay, .canvas-overlay").remove(),p().sortable("option","disabled",!1),e("#widget-"+i+"-wrapper").removeClass("widget-wrapper-canvas").addClass("widget-wrapper"),e("#widget-"+i+"-min").hide(),e("#widget-"+i+"-widget-menu-wrapper").show(),e("#widget-"+i+"-collapse").show(),!1}function a(t){var n=0;e(".widget").each(function(){var t=e(this).outerWidth(),n=e(this).outerHeight(),r=e(this).position().left,i=e(this).position().top;e(this).after('<div class="iframe-overlay" />'),e(this).next(".iframe-overlay").css({width:t,height:n,position:"absolute",top:i,left:r})}),e(".iframe-overlay").click(function(){o.$cogIcon.dropdown("toggle"),e(".iframe-overlay").remove()}),e("*:not(.dropdown-toggle)").on("click",function(){e(".iframe-overlay").remove()})}function f(){o.widget.render("home")}function l(){o.widget.render("canvas",{view:"canvas"})}function c(){var e=o.widget.collapsed;return e?o.widget.show():o.widget.hide(),h(),!1}function h(){var n=!t.collapsed;e(o.$widgetSite).toggle(n),e("i",o.$toggleCollapseIcon).toggleClass("icon-chevron-down",n),e("i",o.$toggleCollapseIcon).toggleClass("icon-chevron-up",!n)}function d(){return e(".dropdown").removeClass("open"),e("#moveWidgetModal").data("regionWidgetId",i),e("#moveWidgetModal").modal("show"),!1}function m(){return o.widget.close(),!1}function g(){return r.viewWidgetDetail(s,n.getPage().id),!1}function y(){return r.viewWidgetDetail(s,n.getPage().id,"widgetComments"),!1}function b(){return r.viewWidgetDetail(s,n.getPage().id,"widgetRatings"),!1}function w(){return t._view.name!=="preferences"&&v(t),!1}function S(e){return function(t){o.$cogIcon.dropdown(),e(t)}}var i=this.widget.regionWidgetId,s=this.widget.widgetId,o=this;h(),u(),e("#widget-"+i+"-toolbar").mousedown(function(e){e.stopPropagation()}),o.$minimizeIcon.click(f),o.$toggleCollapseIcon.click(c),o.$cogIcon.click(a),o.$cogIcon.dropdown(),o.$menuItemMove.hasClass("menu-item-disabled")||o.$menuItemMove.click(S(d)),o.$menuItemDelete.hasClass("menu-item-disabled")||o.$menuItemDelete.click(S(m)),o.$menuItemMaximize.hasClass("menu-item-disabled")||o.$menuItemMaximize.click(S(l)),o.$menuItemAbout.hasClass("menu-item-disabled")||o.$menuItemAbout.click(S(g)),o.$menuItemComment.hasClass("menu-item-disabled")||o.$menuItemComment.click(S(y)),o.$menuItemRate.hasClass("menu-item-disabled")||o.$menuItemRate.click(S(b));var E=o.widget.metadata;E&&(E.hasPrefsToEdit||E.views&&E.views.preferences)&&(o.$menuItemEditPrefs.removeClass("menu-item-disabled"),o.$menuItemEditPrefs.click(S(w)))},t.prototype.getWidgetSite=function(){return this.$widgetSite[0]},t.prototype.destroy=function(e){this.$chrome.remove(),r.isPageEmpty()&&c()},t.prototype.expand=function(){this.$chrome.show()},t.prototype.collapse=function(){this.$chrome.hide()},t.prototype.setTitle=function(e){this.$title.html(e)},n.registerView("home",t)}function g(){var e=function(e){this.widget=e,this.name="preferences"};e.prototype.render=function(){var e={};t.extend(e,this.widget.metadata.views.preferences,{view:"preferences"}),this.view=n.renderView("modal_dialog");var r=this.view.getWidgetSite();this.widget.render(r,e)},e.prototype.getWidgetSite=function(){return this.view.getWidgetSite()},e.prototype.destroy=function(){this.widget._provider.closeWidget(this.widget),n.destroyView(this.view)},n.registerView("preferences",e)}function y(){var t=function(t){this.widget=t,this.$title=e("#widget-"+t.regionWidgetId+"-title")};t.prototype.render=function(t){function r(t){i(e("#pageContent")),p().sortable("option","disabled",!0),e("#widget-"+t+"-wrapper").removeClass("widget-wrapper").addClass("widget-wrapper-canvas"),e("#widget-"+t+"-widget-menu-wrapper").hide(),e("#widget-"+t+"-min").show(),e("#widget-"+t+"-collapse").hide()}function i(t){if(e(".canvas-overlay").length>0)return;var n=e("<div></div>"),r={height:e("body").height()-40};e(n).css(r),e(n).addClass("canvas-overlay"),t.prepend(n[0])}this.widget=t,r(t.regionWidgetId);var n={height:e(".wrapper").height()-e(".navbar").height()-e(".logo-wrapper").height()};e(".canvas-overlay").css(n),t.render(t._el,{view:"canvas"})},t.prototype.getWidgetSite=function(){return e("#widget-"+this.widget.regionWidgetId+"-body")[0]},t.prototype.destroy=function(){return e(".dnd-overlay, .canvas-overlay").remove(),p().sortable("option","disabled",!1),e("#widget-"+widgetId+"-wrapper").removeClass("widget-wrapper-canvas").addClass("widget-wrapper"),e("#widget-"+widgetId+"-min").hide(),e("#widget-"+widgetId+"-widget-menu-wrapper").show(),e("#widget-"+widgetId+"-collapse").show(),!1},t.prototype.setTitle=function(e){this.$title.html(e)},n.registerView("canvas",t)}function b(){var r={sidebar:{name:"sidebar",containerSelector:".popup.slideout",contentSelector:".slideout-content",template:"popup-slideout",initialize:function(t){var n=this,r=t.find(this.contentSelector);r.data("popupType",this.name),t.find(".close").click(function(){n.cleanup(r)}),t.show("slide",{direction:"right"},"fast"),e("body").addClass("modal-open"),e("body").append('<div class="modal-backdrop fade in"></div>'),e("body").addClass("no-scroll")},cleanup:function(t){var n=t.parents(this.containerSelector);n.hide("slide",{direction:"right"},"fast",function(){n.remove(),e("body").removeClass("modal-open"),e(".modal-backdrop").remove(),e("body").removeClass("no-scroll")})},singleton:!0},dialog:{name:"dialog",containerSelector:".popup.dialog",contentSelector:".modal-body",template:"popup-dialog",initialize:function(e){e.find(this.contentSelector).data("popupType",this.name);var t={};e.modal(t),e.on("hidden",function(){e.remove()})},cleanup:function(e){var t=e.parents(this.containerSelector);t.modal("hide")},singleton:!1},modal_dialog:{name:"modal_dialog",containerSelector:".popup.modal_dialog",contentSelector:".modal-body",template:"popup-modal",initialize:function(e){e.find(this.contentSelector).data("popupType",this.name);var t={keyboard:!1,backdrop:"static",show:!0};e.modal(t),e.on("hidden",function(){e.remove()})},cleanup:function(e){var t=e.parents(this.containerSelector);t.modal("hide")},singleton:!0}};t.each(r,function(r){var i,s;n.registerView(r.name,{render:function(n){i=e(o.templates[r.template]()),s=e(r.contentSelector,i);var u=n&&n.preferredHeight,a=n&&n.preferredWidth;return u&&i.height(u),a&&i.width(a),e("#pageContent").prepend(i),t.isFunction(r.initialize)&&r.initialize(i),this},getWidgetSite:function(){return s[0]},destroy:function(){t.isFunction(r.cleanup)?r.cleanup(s):i.remove()}})})}function w(t,n,r){var i;return n?n.indexOf("css")===0?i='<i class="'+n.replace("css:","")+'" ></i>':i='<img src="'+n+'" />':i="<a>"+t+"</a>",e(i).attr("tooltip",r)}function E(t,n,r,i,s,o){var u=e("#widget-"+t+"-wrapper .widget-title-bar"),a=e('<div class="widget-titlebar-action widget-toolbar" ></div>').append(w(n,r,i));return a.attr("id",s).on("click",o),u.append(a),a}function S(){var e={};n.registerActionHandler({create:function(t,r,i,s,o,u,a){if(!e[t]){var f=i.split("/");f.length===2&&f[0]==="widget"&&f[1]==="toolbar"?e[t]=E(s,r,o,u,t,a):n.log("Unsupported action path: '"+i+"'")}},remove:function(t){e[t]&&e[t].remove()}})}function x(){l(),g(),m(),y(),b(),h(),d(),S()}var u={},a=u.views={},f=i.View.extend({models:{page:s.currentPage},initialize:function(){this.models.page.on("acceptShare",r.viewPage),this.models.page.on("declineShare",function(){document.location.href=n.getContext()})}});return a.app=new f,u.displayEmptyPageMessage=c,n.registerOnInitHandler(x),u});