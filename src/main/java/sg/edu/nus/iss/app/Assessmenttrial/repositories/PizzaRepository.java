package sg.edu.nus.iss.app.Assessmenttrial.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


import sg.edu.nus.iss.app.Assessmenttrial.model.Order;

@Repository
public class PizzaRepository {
    
    @Autowired 
    @Qualifier("pizza")
	private RedisTemplate<String, String> redisTemplate;

	public void save(Order order) {
		redisTemplate.opsForValue().set(
				order.getOrderId(), order.toJSON().toString()
		);
	}

	public Optional<Order> get(String orderId) {
		String json = redisTemplate.opsForValue().get(orderId);

		if ((null == json) || (json.trim().length() <= 0))
			return Optional.empty();

		return Optional.of(Order.create(json));
	}
}
