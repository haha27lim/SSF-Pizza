package sg.edu.nus.iss.app.Assessmenttrial.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.Assessmenttrial.model.Delivery;
import sg.edu.nus.iss.app.Assessmenttrial.model.Order;
import sg.edu.nus.iss.app.Assessmenttrial.model.Pizza;
import sg.edu.nus.iss.app.Assessmenttrial.repositories.PizzaRepository;


@Qualifier("PizzaService")
@Service
public class PizzaService {
    
    @Autowired 
	private PizzaRepository pizzaRepo;

    public List<String> validateOrder(Pizza pizza) {
        List<String> errors = new ArrayList<>();

        // Check that a pizza selection was made
        if (pizza.getPizzaSelection() == null || pizza.getPizzaSelection().isEmpty()) {
            errors.add("Please select a pizza.");
        } else if (!Arrays.asList("bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio").contains(pizza.getPizzaSelection())) {
            // Check that the pizza selection is valid
            errors.add("Invalid pizza selection.");
        }

        // Check that a pizza size was selected
        if (pizza.getPizzaSize() == null || pizza.getPizzaSize().isEmpty()) {
            errors.add("Please select a pizza size.");
        } else if (!Arrays.asList("sm", "md", "lg").contains(pizza.getPizzaSize())) {
            // Check that the pizza size is valid
            errors.add("Invalid pizza size.");
        }

        // Check that the number of pizzas is between 1 and 10
        if (pizza.getQuantity() < 1 || pizza.getQuantity() > 10) {
            errors.add("Invalid quantity, you can order between 1 and 10 pizzas.");
        }

        return errors;
    }

	public Optional<Order> getOrderByOrderId(String orderId) {
		return pizzaRepo.get(orderId);
	}

	public Order savePizzaOrder(Pizza pizza, Delivery delivery) {
		Order order = createPizzaOrder(pizza, delivery);
		calculateCost(order);
		pizzaRepo.save(order);
		return order;
	}

	public Order createPizzaOrder(Pizza pizza, Delivery delivery) {
		String orderId = UUID.randomUUID().toString().substring(0, 8);
		Order order = new Order(pizza, delivery);
		order.setOrderId(orderId);
		return order;
	}

	public float calculateCost(Order order) {
		float total = 0f;

		switch (order.getPizzaSelection()) {
			case "margherita":
				total += 22;
				break;
			case "trioformaggio":
				total += 25;
				break;
			case "bella", "marinara", "spianatacalabrese":
				total += 30;
				break;
			default:
		}

		switch (order.getPizzaSize()) {
			case "md":
				total *= 1.2;
				break;
			case "lg":
				total *= 1.5;
				break;
			case "sm":
			default:
		}

		total *= order.getQuantity();

		if (order.isRush())
			total += 2;

		order.setTotalCost(total);

		return total;
	}


}
