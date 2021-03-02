package com.fafunda.authms;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@WebMvcTest
@SpringBootTest
public class AppPageControllerTest {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	void testBcrypt() {
		PasswordEncoder bCrypt = new BCryptPasswordEncoder(15);
		System.out.println(bCrypt.encode("password"));
	}

	@Test
	void testGetIndexSlash() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser("anyUser")
	void testGetHome() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}

	@Test
	void testGetHomeWithHttpBasic() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("user", "password"))).andExpect(status().isOk());
	}

	@Test
	void testGetAdminHomeWithHttpBasic() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("admin", "password"))).andExpect(status().isOk());
	}

	@Test
	void testGetAndPostAuthToken() throws Exception {
		String username = "user";
		String password = "password";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/token").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(response);
		 response = response.replace("{\"token\":\"", "");
		    String token = response.replace("\"}", "");
		    System.out.println(token);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/hello")
		        .header("Authorization", "Bearer " + token))
		        .andExpect(status().isOk());
	}
	
	
	@Test
	void testPostSignUp() throws Exception {
		String username = "demo";
		String password = "password";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/signup").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		result=mockMvc.perform(MockMvcRequestBuilders.post("/v1/token").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
		.andExpect(status().isOk()).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		 response = response.replace("{\"token\":\"", "");
		    String token = response.replace("\"}", "");
		    System.out.println(token);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/hello")
		        .header("Authorization", "Bearer " + token))
		        .andExpect(status().isOk());
	}
	
	@Test
	void testGetUsers() throws Exception {
		String username = "spring";
		String password = "guru";

		String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/token").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		System.out.println(response);
		 response = response.replace("{\"token\":\"", "");
		    String token = response.replace("\"}", "");
		    System.out.println(token);
		
		    result=mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
		        .header("Authorization", "Bearer " + token))
		        .andExpect(status().isOk()).andReturn();
		    response = result.getResponse().getContentAsString();
		    System.out.println(response);
	}
}
