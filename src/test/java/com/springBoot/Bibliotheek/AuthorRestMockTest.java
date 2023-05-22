package com.springBoot.Bibliotheek;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.springBoot.Bibliotheek.SpringBootBibliotheekApplication;
import com.springBoot.Bibliotheek.controller.AuthorRestController;
import com.springBoot.Bibliotheek.exceptions.AuthorNotFoundException;
import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.service.AuthorService;

@SpringBootTest
@ContextConfiguration(classes = SpringBootBibliotheekApplication.class)
public class AuthorRestMockTest {
	
	@Mock
	private AuthorService mock;
	
	private AuthorRestController controller;
	private MockMvc mockMvc;
	
	private final long authorID = 1;
	private final String authorFirstname = "Jack";
	private final String authorLastname = "Sparrow";
	private final Set<Book> authorBooks = new HashSet<>(List.of(new Book(), new Book()));
	
	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new AuthorRestController();
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller,"authorService", mock);
	}
	
	@Test
	public void testAuthor_isOk() throws Exception {
		Mockito.when(mock.getAuthorById(authorID)).thenReturn(createAuthor());
		performRest(String.format("/api/author/%d",authorID));
		Mockito.verify(mock).getAuthorById(authorID);
	}
	
	@Test
	public void testAuthor_notFound() throws Exception {
		Mockito.when(mock.getAuthorById(authorID)).thenThrow(new AuthorNotFoundException(authorID));
		
		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/api/author/"+ authorID)).andReturn();
		});
		
		assertTrue(exception.getCause() instanceof AuthorNotFoundException);
		Mockito.verify(mock).getAuthorById(authorID);
	}
	
	private Author createAuthor() throws Exception {
		return new Author(authorID, authorFirstname, authorLastname, authorBooks);
	}
	
	private void performRest(String uri) throws Exception {
		mockMvc.perform(get(uri))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(authorID))
		.andExpect(jsonPath("$.firstname").value(authorFirstname))
		.andExpect(jsonPath("$.lastname").value(authorLastname))
		.andExpect(jsonPath("$.books").isArray());
	}
	
}
