package sg.edu.nus.iss.app.Assessmenttrial.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {
    
    private float totalCost;
	private String orderId;

	private final Pizza pizza;
	private final Delivery delivery;

	public Order(Pizza pizza, Delivery delivery) {
		this.pizza = pizza;
		this.delivery = delivery;
	}

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPizzaSelection() {
        return getPizzaSelection();
    }

    public String getPizzaSize() {
        return getPizzaSize();
    }

    public int getQuantity() {
        return getQuantity();
    }

    public String getName() {
        return getName();
    }

    public String getAddress() {
        return getAddress();       
    }

    public String getPhone() {
        return getPhone();
    }

    public boolean isRush() {
        return isRush();
    }

    public boolean getRush() {
        return getRush();
    }

    public String getComments() {
        return getComments();
    }

    @Override
	public String toString() {
		return "Order{orderId=%s, totalCost=%.3f, pizza=%s, delivery=%s}"
				.formatted(orderId, totalCost, pizza, delivery);
	}

    public static JsonObject toJSON(String str) {
		JsonReader reader = Json.createReader(new StringReader(str));
		return reader.readObject();
	}
    
    public static Order create(String str) {
		JsonObject json = toJSON(str);
		Pizza pizza = Pizza.create(json);
		Delivery delivery = Delivery.create(json);
		Order order = new Order(pizza, delivery);
		order.setOrderId(json.getString("orderId"));
		order.setTotalCost((float)json.getJsonNumber("total").doubleValue());
		return order;
	}

    public JsonObject toJSON() {
		return Json.createObjectBuilder()
			.add("orderId", orderId)
			.add("name", getName())
			.add("address", getAddress())
			.add("phone", getPhone())
			.add("rush", isRush())
			.add("comments", getComments())
			.add("pizza", getPizzaSelection())
			.add("size", getPizzaSize())
			.add("quantity", getQuantity())
			.add("totalCost", getTotalCost())
			.build();
	}
}
