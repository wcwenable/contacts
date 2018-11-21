package com.wcw.contacts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcw.contacts.entity.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ContactControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void contactList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/contact/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("abc"));
    }

    @Test
    public void addContact() throws Exception {

        //ObjectMapper 是一个可以重复使用的对象
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\n" +
                "  \"name\":\"王成伍\",\n" +
                "  \"sex\":true,\n" +
                "  \"mobile\":\"18585756515\",\n" +
                "  \"email\":\"wcw515@163.com\",\n" +
                "  \"addr\":\"贵州遵义\"\n" +
                "}";
        //将JSON字符串值转换成 Girl对象里的属性值
        Contact contact = mapper.readValue(jsonString, Contact.class);
        mvc.perform(MockMvcRequestBuilders.post("/contact/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                /* 使用writeValueAsString() 方法来获取对象的JSON字符串表示 */
                .content(mapper.writeValueAsString(contact)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.name").value("王成伍"))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.set").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.mobile").value("18585756515"));
    }
}
