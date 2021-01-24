package de.othr.sw.benjamineder.barmanagement.application.web.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

  @GetMapping
  public String login(@RequestParam(name = "error", defaultValue = "false") boolean error, Model model) {
    model.addAttribute("error", error);
    return "auth/login";
  }
}
