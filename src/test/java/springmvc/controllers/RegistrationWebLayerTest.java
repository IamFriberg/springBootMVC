package springmvc.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springmvc.dao.UserDao;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationWebLayerTest {

    @MockBean
    private UserDao userDao;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldContainRegistrationDescription() throws Exception {
        this.mockMvc.perform(get("/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Create user")));
    }
}
