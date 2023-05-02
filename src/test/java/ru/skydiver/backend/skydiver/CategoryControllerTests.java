package ru.skydiver.backend.skydiver;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import config.FunctionalTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@DatabaseSetup("CategoryData.xml")
public class CategoryControllerTests extends FunctionalTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllCategories() throws Exception {
        var expected = "[" +
                "{\"id\":1,\"name\":\"First\",\"imageURL\":\"somePicUrl\",\"mainPage\":true}," +
                "{\"id\":2,\"name\":\"Second\",\"imageURL\":\"somePicUrl2\",\"mainPage\":false}" +
                "]";
        this.mockMvc.perform(get("/category/all")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(expected));
    }

    @Test
    public void getMainPageCategories() throws Exception {
        var expected = "[" +
                "{\"id\":1,\"name\":\"First\",\"imageURL\":\"somePicUrl\",\"mainPage\":true}" +
                "]";
        this.mockMvc.perform(get("/category/main")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(expected));
    }
}
