package ru.skydiver.backend.skydiver;

import config.FunctionalTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skydiver.backend.skydiver.services.CategoryService;

import static org.assertj.core.api.Assertions.assertThat;

class SkydiverApplicationTests extends FunctionalTest {

	@Autowired
	private CategoryService categoryService;

	@Test
	@Disabled
	void testContextAndDb() {
		assertThat(categoryService).isNotNull();
		var result = categoryService.getAllCategories();
		assertThat(result).isNotNull().isEmpty();
	}

}
