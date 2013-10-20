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

package org.apache.rave.portal.web.controller.admin;

import org.apache.rave.model.Category;
import org.apache.rave.model.User;
import org.apache.rave.portal.model.impl.CategoryImpl;
import org.apache.rave.portal.service.CategoryService;
import org.apache.rave.portal.service.UserService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

import static org.apache.rave.portal.web.controller.admin.AdminControllerUtil.*;

/**
 * Controller for the admin pages
 */
@Controller

public class GatewayController {

   private static final String SELECTED_ITEM = "gateway";

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/admin/gate", "/admin/gate/"}, method = RequestMethod.GET)
    public String getCategories(@RequestParam(required = false) final String action,
                                @RequestParam(required = false) String referringPageId,Model model){
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);

        List<Category> gateways = categoryService.getAllList();

        model.addAttribute("gateway", gateways);
        // put category object in the model to allow creating categories from view
        model.addAttribute(ModelKeys.CATEGORY, new CategoryImpl());
        // add tokencheck attribute for creating new category
        model.addAttribute(ModelKeys.TOKENCHECK, AdminControllerUtil.generateSessionToken());
        model.addAttribute(ModelKeys.REFERRING_PAGE_ID, referringPageId);

        if (isCreateDeleteOrUpdate(action)) {
            model.addAttribute("actionresult", action);
        }

        return ViewNames.ADMIN_CATEGORIES;
    }

}
