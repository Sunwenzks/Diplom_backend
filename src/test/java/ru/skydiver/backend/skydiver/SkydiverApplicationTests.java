package ru.skydiver.backend.skydiver;

import org.junit.jupiter.api.Test;
import config.FunctionalTest;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skydiver.backend.skydiver.services.CategoryService;
import static org.assertj.core.api.Assertions.*;

class SkydiverApplicationTests extends FunctionalTest{

	@Autowired
	private CategoryService categoryService;

	@Test
	void testContextAndDb() {
		assertThat(categoryService).isNotNull();
		var result = categoryService.getAllCategories();
		assertThat(result).isNotNull().isEmpty();
	}

}
