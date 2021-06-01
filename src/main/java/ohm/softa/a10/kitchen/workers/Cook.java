package ohm.softa.a10.kitchen.workers;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.model.Dish;
import ohm.softa.a10.model.Order;

public class Cook implements Runnable {
	private final String name;
	private final ProgressReporter progressReporter;
	private final KitchenHatch kitchenHatch;

	public Cook(String name, ProgressReporter progressReporter, KitchenHatch kitchenHatch) {
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	@Override
	public void run() {
		Order order;

		do {
			order = kitchenHatch.dequeueOrder(2000);
			if (order != null) {
				Dish dish = new Dish(order.getMealName());
				try {
					Thread.sleep(dish.getCookingTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				kitchenHatch.enqueueDish(dish);
				progressReporter.updateProgress();
			}
		} while (order != null);

		progressReporter.notifyCookLeaving();
	}
}
