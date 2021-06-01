package ohm.softa.a10.kitchen;

import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

import java.util.ArrayDeque;
import java.util.Deque;

public class KitchenHatchImpl implements KitchenHatch {
	private final int maxMeals;
	private final Deque<Order> orders;
	private final Deque<Dish> dishes = new ArrayDeque<>();

	public KitchenHatchImpl(int maxMeals, Deque<Order> orders) {
		this.maxMeals = maxMeals;
		this.orders = orders;
	}

	@Override
	public int getMaxDishes() {
		return maxMeals;
	}

	@Override
	public Order dequeueOrder(long timeout) {
		synchronized (orders) {
			return orders.poll();
		}
	}

	@Override
	public int getOrderCount() {
		synchronized (orders) {
			return orders.size();
		}
	}

	@Override
	public Dish dequeueDish(long timeout) {
		long currentTimeStamp = System.nanoTime();
		synchronized (dishes) {
			while (dishes.isEmpty()) {
				try {
					dishes.wait(timeout);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (timeout > 0 && dishes.size() == 0 && System.nanoTime() - currentTimeStamp > timeout * 1000) {
					dishes.notifyAll();
					return null;
				}
			}
			Dish dish = dishes.pop();
			dishes.notifyAll();
			return dish;
		}
	}

	@Override
	public void enqueueDish(Dish dish) {
		synchronized (dishes) {
			while (dishes.size() >= maxMeals) {
				try {
					dishes.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			dishes.push(dish);
			dishes.notifyAll();
		}
	}

	@Override
	public int getDishesCount() {
		synchronized (dishes) {
			return dishes.size();
		}
	}
}
