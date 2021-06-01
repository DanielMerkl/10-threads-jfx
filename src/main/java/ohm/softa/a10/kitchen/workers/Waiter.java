package ohm.softa.a10.kitchen.workers;

import ohm.softa.a10.internals.displaying.ProgressReporter;
import ohm.softa.a10.kitchen.KitchenHatch;
import ohm.softa.a10.model.Dish;

import java.util.Random;

public class Waiter implements Runnable {
	private final String name;
	private final ProgressReporter progressReporter;
	private final KitchenHatch kitchenHatch;

	public Waiter(String name, ProgressReporter progressReporter, KitchenHatch kitchenHatch) {
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	@Override
	public void run() {
		Dish dish;

		do {
			dish = kitchenHatch.dequeueDish();
			if (dish != null) {
				try {
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			progressReporter.updateProgress();
		} while (dish != null);

		progressReporter.notifyWaiterLeaving();
	}
}
