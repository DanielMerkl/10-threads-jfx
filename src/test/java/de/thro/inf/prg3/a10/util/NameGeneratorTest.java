package de.thro.inf.prg3.a10.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameGeneratorTest {

	private static final Logger logger = LogManager.getLogger(NameGeneratorTest.class);
	private final NameGenerator nameGenerator = new NameGenerator();

	@Test
	void generateName() {
		for(var i = 0; i < 100; i++){
			var generatedName = nameGenerator.generateName();
			assertNotNull(generatedName);
			assertNotEquals(0, generatedName.length());
			logger.debug("Generated name: {}", generatedName);
		}
	}

	@Test
	void getRandomDish() {
		for(var i = 0; i < 1000; i++){
			var randomDish = nameGenerator.getRandomDish();
			assertNotNull(randomDish);
			assertNotEquals(0, randomDish.length());

			logger.debug("Random dish: {}", randomDish);
		}
	}
}
