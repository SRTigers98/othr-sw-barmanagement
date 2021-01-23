package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand.DrinksOnDemandService;
import de.othr.sw.benjamineder.barmanagement.application.util.MapCollectors;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarAdminAccess;
import de.othr.sw.benjamineder.barmanagement.application.web.model.DrinksOnDemandOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/drinks-on-demand")
public class AdminDrinksOnDemandController {

  private final DrinksOnDemandService drinksOnDemandService;

  @Autowired
  public AdminDrinksOnDemandController(DrinksOnDemandService drinksOnDemandService) {
    this.drinksOnDemandService = drinksOnDemandService;
  }

  @BarAdminAccess
  @GetMapping
  public String adminDrinksOnDemandArticles(Model model) {
    var articles = drinksOnDemandService.getArticles();
    model.addAttribute("articles", articles);
    return "admin/admin_drinks-on-demand";
  }

  @BarAdminAccess
  @GetMapping(path = "/order")
  public String adminDrinksOnDemandOrder(Model model) {
    var articles = drinksOnDemandService.getArticles().stream()
                                        .sorted(Comparator.comparing(ArticleDTO::getName))
                                        .collect(MapCollectors.toKeySortedMap(ArticleDTO::getName, art -> 0));
    model.addAttribute("model", new DrinksOnDemandOrderModel(articles))
         .addAttribute("saved", false);
    return "admin/admin_drinks-on-demand_order";
  }

  @BarAdminAccess
  @PostMapping(path = "/order")
  public String adminOrderDrinks(@ModelAttribute DrinksOnDemandOrderModel order, Model model) {
    var orderPositions = order.getArticlePositions().entrySet().stream()
                              .map(entry -> Map.entry(getArticleByName(entry), entry.getValue()))
                              .filter(entry -> entry.getValue() > 0)
                              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    drinksOnDemandService.orderArticles(orderPositions);
    model.addAttribute("model", order)
         .addAttribute("saved", true);
    return "admin/admin_drinks-on-demand_order";
  }

  private ArticleDTO getArticleByName(Map.Entry<String, Integer> entry) {
    return drinksOnDemandService.getArticleByName(entry.getKey())
                                .orElseThrow(() -> new IllegalArgumentException(
                                    String.format("Article with name %s not found!", entry.getKey())));
  }
}
