package sg.edu.nus.iss.app.Assessmenttrial.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.app.Assessmenttrial.model.Delivery;
import sg.edu.nus.iss.app.Assessmenttrial.model.Order;
import sg.edu.nus.iss.app.Assessmenttrial.model.Pizza;
import sg.edu.nus.iss.app.Assessmenttrial.service.PizzaService;

@Controller
public class PizzaController {

	@Autowired
	private PizzaService pizzaService;

	private Logger logger = Logger.getLogger(PizzaController.class.getName());

	
    @GetMapping(path={"/", "/index.html"})
	public String getIndex(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("pizza", new Pizza());
		return "index";
	}

	@PostMapping(path="/pizza", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String postPizza(@Valid Pizza pizza, BindingResult bindings, Model model, HttpSession session) {

		logger.info("POST /pizza: %s".formatted(pizza.toString()));

		if (bindings.hasErrors())
			return "index";

		// Perform the checks on the pizza order
        List<String> errors = pizzaService.validateOrder(pizza);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            System.out.println("errors: " + errors);
            return "index";
        }

		session.setAttribute("pizza", pizza);

		model.addAttribute("delivery", new Delivery());

		return "delivery";
	}

	@PostMapping(path="/pizza/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String postPizzaOrder(@Valid Delivery delivery, BindingResult bindings, Model model, HttpSession session) {

		logger.info("POST /pizza/order: %s".formatted(delivery.toString()));

		if (bindings.hasErrors())
			return "delivery";

		Pizza pizza = (Pizza)session.getAttribute("pizza");

		Order order = pizzaService.savePizzaOrder(pizza, delivery);

		logger.info("%s".formatted(order));

		model.addAttribute("order", order);

		return "order";
	}
}

