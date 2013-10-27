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

define(["underscore","core/rave_api","core/rave_view_manager","core/rave_providers"],function(e,t,n,r){function i(e){return r[e.toLowerCase()]}var s=function(t){var n=t.type;e.extend(this,t),this._provider=i(n);if(!this._provider)throw new Error("Cannot render widget "+t.widgetUrl+". "+"Provider "+n+" is not registered.");this._provider.initWidget(this)};return isHTMLElement=function(){return window.HTMLElement?function(e){return e instanceof HTMLElement}:function(e){return e.nodeType&&e.nodeType==1}}(),s.extend=function(t){e.extend(this.prototype,t)},s.prototype.render=function(t,r){!r&&!e.isString(t)&&!isHTMLElement(t)&&(r=t,t=this._el);if(e.isString(t)){var i=n.renderView(t,this);t=i.getWidgetSite(),this._view=i}if(!isHTMLElement(t))throw new Error("Cannot render widget. You must provide an el to render the view into");return this._el=t,this._provider.renderWidget(this,t,r),this},s.prototype.renderError=function(e,t){e.innerHTML="Error rendering widget.<br /><br />"+t},s.prototype.hide=function(){this.collapsed=!0,t.rest.saveWidgetCollapsedState({regionWidgetId:this.regionWidgetId,collapsed:this.collapsed})},s.prototype.show=function(){this.collapsed=!1,t.rest.saveWidgetCollapsedState({regionWidgetId:this.regionWidgetId,collapsed:this.collapsed})},s.prototype.close=function(e){this._provider.closeWidget(this,e),this._view&&n.destroyView(this._view),t.rpc.removeWidget({regionWidgetId:this.regionWidgetId})},s.prototype.moveToPage=function(e,n){t.rpc.moveWidgetToPage({toPageId:e,regionWidgetId:this.regionWidgetId,successCallback:n})},s.prototype.moveToRegion=function(e,n,r){t.rpc.moveWidgetToRegion({regionWidgetId:this.regionWidgetId,fromRegionId:e,toRegionId:n,toIndex:r})},s.prototype.savePreference=function(e,n){this.userPrefs[e]=n,t.rest.saveWidgetPreference({regionWidgetId:this.regionWidgetId,prefName:e,prefValue:n})},s.prototype.savePreferences=function(e){this.userPrefs=e,t.rest.saveWidgetPreferences({regionWidgetId:this.regionWidgetId,userPrefs:e})},s});