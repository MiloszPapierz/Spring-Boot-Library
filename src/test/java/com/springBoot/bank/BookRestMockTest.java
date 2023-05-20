package com.springBoot.bank;

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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import com.springBoot.Bibliotheek.SpringBootBibliotheekApplication;
import com.springBoot.Bibliotheek.controller.BookRestController;
import com.springBoot.Bibliotheek.exceptions.AuthorNotFoundException;
import com.springBoot.Bibliotheek.exceptions.BookNotFoundException;
import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.service.BookService;

@SpringBootTest
@ContextConfiguration(classes = SpringBootBibliotheekApplication.class)
public class BookRestMockTest {

	@Mock
	private BookService mock;
	
	private BookRestController controller;
	private MockMvc mockMvc;
	
	private final Long bookId = 15L;
	private final String bookName = "Test Book";
	private final String bookImgUrl = "https://imagedomain.com/image/1";
	private final String bookIsbn = "4561";
	private final BigDecimal bookPrice = new BigDecimal(12.50);
	
	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new BookRestController();
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller, "bookService", mock);
	}
	
	@Test
	public void testBook_ok() throws Exception {
		Mockito.when(mock.getBookByIsbn(bookIsbn)).thenReturn(makeBook());
		performRest(String.format("/api/book/%s",bookIsbn));
		Mockito.verify(mock).getBookByIsbn(bookIsbn);
	}
	
	@Test
	public void testBook_NotFound() throws Exception {
		Mockito.when(mock.getBookByIsbn(bookIsbn)).thenThrow(new BookNotFoundException(bookIsbn));
		
		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/api/book/"+ bookIsbn)).andReturn();
		});
		
		assertTrue(exception.getCause() instanceof BookNotFoundException);
		Mockito.verify(mock).getBookByIsbn(bookIsbn);
	}
	
	private Book makeBook() throws Exception{
		return new Book(bookId, bookName,bookImgUrl, new HashSet<>(List.of(new Author(),new Author())), bookIsbn, bookPrice);
	}
	
	private void performRest(String uri) throws Exception {
		mockMvc.perform(get(uri))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(bookId))
		.andExpect(jsonPath("$.name").value(bookName))
		.andExpect(jsonPath("$.authors").isArray())
		.andExpect(jsonPath("$.isbn").value(bookIsbn))
		.andExpect(jsonPath("$.price").value(bookPrice))
		.andExpect(jsonPath("$.img_url").value(bookImgUrl));
	}
}
