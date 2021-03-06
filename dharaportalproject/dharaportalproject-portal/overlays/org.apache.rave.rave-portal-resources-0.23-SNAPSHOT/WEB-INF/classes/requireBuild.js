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

({
    appDir: '/home/harsha/FYP/codings/portal-sameera/rave/rave-portal-resources/src/main/webapp/static/script',
    mainConfigFile: '/home/harsha/FYP/codings/portal-sameera/rave/rave-portal-resources/src/main/webapp/static/script/requireConfig.js',
    baseUrl: '.',
    dir: '/home/harsha/FYP/codings/portal-sameera/rave/rave-portal-resources/target/rave-portal-resources-0.23-SNAPSHOT/static/script-built',
    paths: {
        'clientMessages': 'empty:'
    },
    modules: [
        {
            name: 'rave'
        },
        {
            name: 'ui',
            exclude: ['rave']
        },
        {
            name: 'portal/rave_admin',
            exclude: ['rave']
        },
        {
            name: 'portal/rave_store',
            exclude: ['rave']
        },
        {
            name: 'portal/rave_forms',
            exclude: ['rave']
        }
    ]
})
