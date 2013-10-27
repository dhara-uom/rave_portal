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

define(["underscore","core/rave_log","osapi"],function(e,t){if(e.isUndefined(OpenAjax))throw new Error("No implementation of OpenAjax found.  Please ensure that an implementation has been included in the page.");var n=new OpenAjax.hub.ManagedHub({onSubscribe:function(e,n){return t((n==null?"Container":n.getClientID())+" subscribes to this topic '"+e+"'"),!0},onUnsubscribe:function(e,n){return t((n==null?"Container":n.getClientID())+" unsubscribes from this topic '"+e+"'"),!0},onPublish:function(e,n,r,i){return t((r==null?"Container":r.getClientID())+" publishes '"+n+"' to topic '"+e+"' subscribed by "+(i==null?"Container":i.getClientID())),!0}});return n});