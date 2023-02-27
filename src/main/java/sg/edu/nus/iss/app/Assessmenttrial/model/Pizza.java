package sg.edu.nus.iss.app.Assessmenttrial.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @NotNull(message="Pizza selection is required")
	private String pizzaSelection;

	@NotNull(message="Pizza size is required")
	private String pizzaSize;

	@Min(value=1, message="You must order at least 1 pizza")
	@Max(value=10, message="You can only purchase a maximum of 10 pizzas")
	private int quantity;

    public String getPizzaSelection() {
        return pizzaSelection;
    }

    public void setPizzaSelection(String pizzaSelection) {
        this.pizzaSelection = pizzaSelection;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
	public String toString() {
		return "Pizza{pizza=%s, size=%s, quantity=%d}".formatted(pizzaSelection, pizzaSize, quantity);
	}

    public static Pizza create(JsonObject json) {
        Pizza pizza = new Pizza();
        pizza.setPizzaSelection(json.getString("pizza"));
		pizza.setPizzaSize(json.getString("size"));
		pizza.setQuantity(json.getInt("quantity"));
		return pizza;
    }
}
