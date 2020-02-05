package fi.tuni.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* Custom Controller-class
*/

@Controller
public class MyController {

    /**
    * Returns the html-code to http://localhost:8080/index
    */
    @RequestMapping("/index")
    @ResponseBody

    // to http://localhost:8080/index

    public String index() {
        return Ui.html.getHtmlString();
    }
}
