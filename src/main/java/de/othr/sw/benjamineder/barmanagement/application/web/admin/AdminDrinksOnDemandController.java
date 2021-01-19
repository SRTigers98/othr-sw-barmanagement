package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand.DrinksOnDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/drinks-on-demand")
public class AdminDrinksOnDemandController {

  private final DrinksOnDemandService drinksOnDemandService;

  @Autowired
  public AdminDrinksOnDemandController(DrinksOnDemandService drinksOnDemandService) {
    this.drinksOnDemandService = drinksOnDemandService;
  }

  @GetMapping
  public String adminDrinksOnDemandArticles(Model model) {
    var articles = drinksOnDemandService.getArticles();
    model.addAttribute("articles", articles);
    return "admin_drinks-on-demand";
  }
}
