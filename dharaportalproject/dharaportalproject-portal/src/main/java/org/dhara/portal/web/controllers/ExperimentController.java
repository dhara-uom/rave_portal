package org.dhara.portal.web.controllers;

import org.apache.airavata.registry.api.workflow.ExperimentData;
import org.apache.airavata.registry.api.workflow.NodeExecutionData;
import org.dhara.portal.web.airavataService.AiravataClientAPIService;
import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.helper.Nodehelper;
import org.dhara.portal.web.restClientService.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.dhara.portal.web.controllers.GatewayControllerUtil.addNavigationMenusToModel;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 8/11/13
 * Time: 6:05 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ExperimentController {

    private static final String SELECTED_ITEM = "experiments";

    @Autowired
    private RestServiceImpl restService;



    @RequestMapping(value = {"/admin/experiments", "/admin/experiments/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false) final String action,
                                           @RequestParam(required = false) String referringPageId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        List<ExperimentHelper> experimentHelpers = restService.getExperiments();
        model.addAttribute("message", experimentHelpers);
        return "templates.admin.experiments";
    }

    public ArrayList<ExperimentHelper> reverseList(List<ExperimentHelper> list) {

        if (list == null || list.isEmpty()) {
            return null;
        }

        ArrayList<ExperimentHelper> reversedlist = new ArrayList<ExperimentHelper>(list);

        Collections.reverse(reversedlist);
        return reversedlist;
    }
}
