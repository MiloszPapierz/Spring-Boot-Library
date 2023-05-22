package com.springBoot.Bibliotheek;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.springBoot.Bibliotheek.config.SecurityConfig;
import com.springBoot.Bibliotheek.controller.FormAuthorsWrapper;
import com.springBoot.Bibliotheek.controller.FormLocationsWrapper;
import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;
import com.springBoot.Bibliotheek.model.Location;
import com.springBoot.Bibliotheek.model.User;
import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.CustomUserDetailsService;

@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@WithMockUser(username = "user", roles = { "USER" })
	@Test
	public void testGetBooksPage() throws Exception {
		mockMvc.perform(get("/books")).andExpect(status().isOk()).andExpect(view().name("books"))
				.andExpect(model().attributeExists("books"));
	}

	@WithMockUser(username = "user", roles = { "USER" })
	@Test
	public void testGetFavoriteBooksPage() throws Exception {
		mockMvc.perform(get("/books/favorites")).andExpect(status().isOk()).andExpect(view().name("books"))
				.andExpect(model().attributeExists("books"));
	}

	@WithMockUser(username = "user", roles = { "USER" })
	@Test
	public void testgetBookDetailsPage_isOk() throws Exception {
		mockMvc.perform(get("/books/1")).andExpect(status().isOk()).andExpect(view().name("book"))
				.andExpect(model().attributeExists("book")).andExpect(model().attributeExists("locations"))
				.andExpect(model().attributeExists("alreadyAdded")).andExpect(model().attributeExists("reachedMaximum"))
				.andExpect(model().attributeExists("numberOfFavorites"));
	}

	@WithMockUser(username = "user", roles = { "USER" })
	@Test
	public void testgetBookDetailsPage_noBookWithGivenIdFound() throws Exception {
		mockMvc.perform(get("/books/950")).andExpect(status().isOk()).andExpect(view().name("error/bookNotFound"));

	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	public void testGetAddBookPage() throws Exception {
		mockMvc.perform(get("/books/add")).andExpect(status().isOk()).andExpect(view().name("addBook"))
				.andExpect(model().attributeExists("locations")).andExpect(model().attributeExists("authors"))
				.andExpect(model().attributeExists("book"));

	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	public void testPostAddBook() throws Exception {
		Book book = new Book(); // Create a dummy Book object
		book.setIsbn("9780807286005");
		book.setName("Test");
		book.setPrice(BigDecimal.valueOf(15.5));
		FormAuthorsWrapper wrapper = new FormAuthorsWrapper();
		Author author = new Author("Test", "Test");
		wrapper.setAuthorsWrapper(List.of(author));
		FormLocationsWrapper locationsWrapper = new FormLocationsWrapper();
		Location loc = new Location(15, 15, "Test", book);
		locationsWrapper.setLocationsWrapper(List.of(loc));

		mockMvc.perform(post("/books/add").flashAttr("Book", book).flashAttr("FormAuthorsWrapper", wrapper).flashAttr("FormLocationsWrapper", locationsWrapper).with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/books"));
	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	public void testPostAddBookToFavorites() throws Exception {

		mockMvc.perform(post("/books/favorites/add").param("bookId", "1").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/books"))
				.andExpect(model().attributeExists("message"));
	}

	@WithMockUser(username = "admin", roles = { "ADMIN" })
	@Test
	public void testPostRemoveBookFromFavorites() throws Exception {

		mockMvc.perform(post("/books/favorites/remove").param("bookId", "1").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/books"))
				.andExpect(model().attributeExists("message"));

	}

}
