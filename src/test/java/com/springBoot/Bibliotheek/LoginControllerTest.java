package com.springBoot.Bibliotheek;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.springBoot.Bibliotheek.config.SecurityConfig;

@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void loginGet() throws Exception {
		mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("login"));
	}
	
	@Test 
	public void testWrongPassword() throws Exception {
		mockMvc.perform(formLogin("/login").user("username", "admin").password("password","wrongPassword"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/login?error"));
	}
	
	@Test
    void testCorrectPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
            .user("username", "admin")
            .password("password", "admin"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/books"));
    }
	
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    public void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(view().name("books"));
    }
	
	@WithMockUser(username = "admin", roles = { "ADMIN" })
    @Test
    public void testAccessWithUserRoleToAdminPage() throws Exception {
        mockMvc.perform(get("/books/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("addBook"));
    }
	
	@WithMockUser(username = "user", roles = { "USER" })
    @Test
    public void testNoAccessWithUserRoleToAdminPage() throws Exception {
        mockMvc.perform(get("/books/add"))
        .andExpect(status().isForbidden());
    }
	
	@WithMockUser(username = "user", roles = { "NOT_USER" })
    @Test
    public void testNoAccessWithWrongUserRole() throws Exception {
        mockMvc.perform(get("/books"))
        .andExpect(status().isForbidden());
    }
	
	@Test
	public void accessDeniedPageGet() throws Exception {
    	mockMvc.perform(get("/403"))
        .andExpect(status().isOk())
    	.andExpect(view().name("403"));
	}
	
	
}
