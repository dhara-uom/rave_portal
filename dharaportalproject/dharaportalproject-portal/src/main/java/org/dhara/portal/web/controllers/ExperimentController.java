package org.dhara.portal.web.controllers;

import org.apache.rave.model.PortalPreference;
import org.apache.rave.portal.service.PortalPreferenceService;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.PortalPreferenceKeys;
import org.apache.rave.rest.model.SearchResult;
import org.dhara.portal.web.helper.ExperimentHelper;
import org.dhara.portal.web.restClientService.RestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    int DEFAULT_PAGE_SIZE=10;

    @Autowired
    private RestServiceImpl restService;

    @Autowired
    private PortalPreferenceService preferenceService;

    @RequestMapping(value = {"/admin/experiments", "/admin/experiments/"}, method = RequestMethod.GET)
    public String handleRequestInternal(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false) final String action,
                                           @RequestParam(required = false) String referringPageId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        model.addAttribute(ModelKeys.REFERRING_PAGE_ID, referringPageId);
        addNavigationMenusToModel(SELECTED_ITEM, model, referringPageId);
        List<ExperimentHelper> experimentHelpers = restService.getExperiments();
        int count=experimentHelpers.size();

        experimentHelpers=getLimitedList(offset,count,experimentHelpers);
        final SearchResult<ExperimentHelper> experiments =new SearchResult<ExperimentHelper>(experimentHelpers,count);
        experiments.setOffset(offset);
        experiments.setPageSize(getPageSize());
        model.addAttribute(ModelKeys.SEARCHRESULT, experiments);
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

    private List<ExperimentHelper> getLimitedList(int offset, int count, List<ExperimentHelper> exp){
        List<ExperimentHelper> limited=new ArrayList<ExperimentHelper>();

        int max=count;
        if(count>DEFAULT_PAGE_SIZE+offset)
            max=DEFAULT_PAGE_SIZE+offset;
        int j=0;
        for(int i=offset;i<max;i++){
           limited.add(j,exp.get(i));
            j++;
        }
        return limited;

    }

    public int getPageSize() {
            final PortalPreference pageSizePref = preferenceService.getPreference(PortalPreferenceKeys.PAGE_SIZE);
            if (pageSizePref == null) {
                return DEFAULT_PAGE_SIZE;
            }
            try {
                return Integer.parseInt(pageSizePref.getValue());
            } catch (NumberFormatException e) {
                return DEFAULT_PAGE_SIZE;
            }
    }
}
