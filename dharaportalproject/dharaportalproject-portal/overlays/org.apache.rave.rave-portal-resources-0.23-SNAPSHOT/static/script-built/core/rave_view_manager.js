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

define(["underscore"],function(e){var t={},n={},r={};return r.registerView=function(e,n){t[e.toLowerCase()]=n},r.getView=function(e){return t[e.toLowerCase()]},r.renderView=function(t){var i=e.toArray(arguments).slice(1),s=r.getView(t);if(!s)throw new Error("Attempted to render undefined view: "+t);return e.isFunction(s)&&(s=new s(i[0])),s.render.apply(s,i),s._uid=e.uniqueId("rave_view_"),n[s._uid]=s,s},r.getRenderedView=function(e){return n[e]},r.destroyView=function(t){var i=e.toArray(arguments).slice(1);e.isString(t)&&(t=r.getRenderedView(t)),delete n[t._uid],t.destroy(i)},r.reset=function(){t={}},r});