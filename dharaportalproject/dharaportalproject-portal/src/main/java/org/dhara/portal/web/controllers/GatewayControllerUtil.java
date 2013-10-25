package org.dhara.portal.web.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: sameera
 * Date: 10/22/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rave.portal.web.model.NavigationItem;
import org.apache.rave.portal.web.model.NavigationMenu;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

public class GatewayControllerUtil {
    public static final int DEFAULT_PAGE_SIZE = 10;
    private static final int TOKEN_LENGTH = 256;

    private GatewayControllerUtil() {
    }

    static String generateSessionToken() {
        return RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
    }

    public static void checkTokens(String sessionToken, String token, SessionStatus status) {
        if (StringUtils.length(sessionToken) != TOKEN_LENGTH || !(sessionToken.equals(token))) {
            status.setComplete();
            throw new SecurityException("Token does not match");
        }
    }

    static boolean isDeleteOrUpdate(String action) {
        return "update".equals(action) || "delete".equals(action);
    }

    static boolean isCreateDeleteOrUpdate(String action){
        return "create".equals(action) || isDeleteOrUpdate(action);
    }

    static void addNavigationMenusToModel(String selectedItem, Model model,  String referringPageId) {
        final NavigationMenu topMenu = getTopMenu(referringPageId);
        model.addAttribute(topMenu.getName(), topMenu);
        final NavigationMenu tabMenu = getTabMenu(selectedItem, referringPageId);
        model.addAttribute(tabMenu.getName(), tabMenu);
    }

    // For the time being hard coded NavigationMenu's
    private static NavigationMenu getTopMenu(String referringPageId) {
        NavigationMenu menu = new NavigationMenu("topnav");

        NavigationItem logout = new NavigationItem("page.general.logout", null, "/j_spring_security_logout");
        menu.addNavigationItem(logout);

        NavigationItem raveHome = new NavigationItem();
        raveHome.setName("page.general.back");

        if (referringPageId != null && !referringPageId.isEmpty()) {
            raveHome.setUrl("/app/page/view/" + referringPageId);
        } else {
            raveHome.setUrl("/");
        }
        menu.addNavigationItem(raveHome);

        return menu;
    }

    private static NavigationMenu getTabMenu(String selectedItem, String referringPageId) {
        NavigationMenu menu = new NavigationMenu("tabs");

        NavigationItem home = new NavigationItem("admin.home.shorttitle", null, null);
        home.setSelected("home".equals(selectedItem));


        NavigationItem users = new NavigationItem("admin.users.shorttitle", null, null);
        users.setSelected("users".equals(selectedItem));


        NavigationItem widgets = new NavigationItem("admin.widgets.shorttitle", null, null);
        widgets.setSelected("widgets".equals(selectedItem));


        NavigationItem preferences = new NavigationItem("admin.preferences.shorttitle", null, null);
        preferences.setSelected("preferences".equals(selectedItem));


        NavigationItem categories = new NavigationItem("admin.category.shortTitle", null, null);
        categories.setSelected("categories".equals(selectedItem));

        //modified for dhara
        NavigationItem workflows = new NavigationItem("admin.workflows.shortTitle", null, null);
        workflows.setSelected("workflows".equals(selectedItem));

        //modified for dhara
        NavigationItem experiments = new NavigationItem("admin.experiments.shortTitle", null, null);
        experiments.setSelected("experiments".equals(selectedItem));

        // set url of nav items with or without the referring page id
        if (referringPageId != null && !referringPageId.isEmpty()) {
            home.setUrl("/app/admin?referringPageId=" + referringPageId);
            users.setUrl("/app/admin/users?referringPageId=" + referringPageId);
            widgets.setUrl("/app/admin/widgets?referringPageId=" + referringPageId);
            preferences.setUrl("/app/admin/preferences?referringPageId=" + referringPageId);
            categories.setUrl("/app/admin/categories?referringPageId=" + referringPageId);
            workflows.setUrl("/app/admin/workflows?referringPageId=" + referringPageId);		//modified for dhara
            experiments.setUrl("/app/admin/experiments?referringPageId=" + referringPageId);		//modified for dhara
        } else {
            home.setUrl("/app/admin");
            users.setUrl("/app/admin/users");
            widgets.setUrl("/app/admin/widgets");
            preferences.setUrl("/app/admin/preferences");
            categories.setUrl("/app/admin/categories");
            workflows.setUrl("/app/admin/workflows");						//modified for dhara
            experiments.setUrl("/app/admin/experiments");						//modified for dhara
        }

        // add nav items to menu
        menu.addNavigationItem(home);
        menu.addNavigationItem(users);
        menu.addNavigationItem(widgets);
        menu.addNavigationItem(preferences);
        menu.addNavigationItem(categories);
        menu.addNavigationItem(workflows);							//modified for dhara
        menu.addNavigationItem(experiments);							//modified for dhara


        return menu;
    }
}
