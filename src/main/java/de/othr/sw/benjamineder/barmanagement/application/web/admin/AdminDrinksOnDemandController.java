package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand.DrinksOnDemandService;
import de.othr.sw.benjamineder.barmanagement.application.web.model.DrinksOnDemandOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;

import java.util.stream.Collectors;

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

  @GetMapping(path = "/order")
  public String adminDrinksOnDemandOrder(Model model) {
    var articles = drinksOnDemandService.getArticles().stream()
                                        .collect(Collectors.toMap(ArticleDTO::getName, art -> 0));
    model.addAttribute("model", new DrinksOnDemandOrderModel(articles))
         .addAttribute("saved", false);
    return "admin_drinks-on-demand_order";
  }
}
