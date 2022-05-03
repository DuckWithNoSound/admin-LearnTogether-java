package Admin.LearnTogether.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


/*
  Created by Luvbert
*/
@Controller
public class MainController {
    @GetMapping("/")
    public ModelAndView defaultPage(){
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/home")
    public ModelAndView homePage(){
        return new ModelAndView("home");
    }
}
