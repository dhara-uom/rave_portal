package org.dhara.portal.web.restAPI;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: nipuni
 * Date: 11/17/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RestInputAPI implements Observer {

        private static int[] inputs = null;

        //TODO this url does not work
        @RequestMapping(value = {"admin/rest", "admin/rest/"}, method = RequestMethod.GET)
        @ResponseBody
        public int[] handleRequestInternal(Model model, HttpServletRequest httpServletRequest) throws Exception {

            return inputs;
        }

    @Override
    public void update(Observable o, Object arg) {
        this.inputs = (int[])arg;
    }
}



