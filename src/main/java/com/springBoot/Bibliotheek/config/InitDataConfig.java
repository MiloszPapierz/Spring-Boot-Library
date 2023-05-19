package com.springBoot.Bibliotheek.config;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;
import com.springBoot.Bibliotheek.model.Location;
import com.springBoot.Bibliotheek.model.Role;
import com.springBoot.Bibliotheek.model.User;
import com.springBoot.Bibliotheek.repository.AuthorRepository;
import com.springBoot.Bibliotheek.repository.BookRepository;
import com.springBoot.Bibliotheek.repository.FavoriteBookRepository;
import com.springBoot.Bibliotheek.repository.LocationRepository;
import com.springBoot.Bibliotheek.repository.RoleRepository;
import com.springBoot.Bibliotheek.repository.UserRepository;

@Component
public class InitDataConfig implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private FavoriteBookRepository favoriteBookRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Author author1 = new Author("Rebecca", "Yarros");
		Author author2 = new Author("Jennifer Lynn", "Barnes");
		Author author3 = new Author("Chris", "Jordan");
		Author author4 = new Author("Adalyn", "Grace");
		Author author5 = new Author("Carley", "Fortune");

		Book book1 = new Book("Fourth Wing",
				"https://prodimage.images-bn.com/lf?set=key%5Bresolve.pixelRatio%5D,value%5B1%5D&set=key%5Bresolve.width%5D,value%5B300%5D&set=key%5Bresolve.height%5D,value%5B10000%5D&set=key%5Bresolve.imageFit%5D,value%5Bcontainerwidth%5D&set=key%5Bresolve.allowImageUpscaling%5D,value%5B0%5D&set=key%5Bresolve.format%5D,value%5Bwebp%5D&product=path%5B/pimages/9781649374042_p0_v5%5D&call=url%5Bfile:common/decodeProduct.chain%5D",
				"9781649374042", BigDecimal.valueOf(16.99));
		book1.getAuthors().add(author1);
		book1.getAuthors().add(author2);

		Book book2 = new Book("The Brothers Hawthorne",
				"https://prodimage.images-bn.com/lf?set=key%5Bresolve.pixelRatio%5D,value%5B1%5D&set=key%5Bresolve.width%5D,value%5B300%5D&set=key%5Bresolve.height%5D,value%5B10000%5D&set=key%5Bresolve.imageFit%5D,value%5Bcontainerwidth%5D&set=key%5Bresolve.allowImageUpscaling%5D,value%5B0%5D&set=key%5Bresolve.format%5D,value%5Bwebp%5D&product=path%5B/pimages/9780316565233_p0_v1%5D&call=url%5Bfile:common/decodeProduct.chain%5D",
				"9780316480772", BigDecimal.valueOf(29.99));
		book2.getAuthors().add(author2);

		Book book3 = new Book("Wildfire",
				"https://prodimage.images-bn.com/lf?set=key%5Bresolve.pixelRatio%5D,value%5B1%5D&set=key%5Bresolve.width%5D,value%5B300%5D&set=key%5Bresolve.height%5D,value%5B10000%5D&set=key%5Bresolve.imageFit%5D,value%5Bcontainerwidth%5D&set=key%5Bresolve.allowImageUpscaling%5D,value%5B0%5D&set=key%5Bresolve.format%5D,value%5Bwebp%5D&product=path%5B/pimages/9781668046845_p0_v1%5D&call=url%5Bfile:common/decodeProduct.chain%5D",
				"9781338606072", BigDecimal.valueOf(16.99));
		book3.getAuthors().add(author3);

		Book book4 = new Book("Foxglove",
				"https://prodimage.images-bn.com/lf?set=key%5Bresolve.pixelRatio%5D,value%5B1%5D&set=key%5Bresolve.width%5D,value%5B300%5D&set=key%5Bresolve.height%5D,value%5B10000%5D&set=key%5Bresolve.imageFit%5D,value%5Bcontainerwidth%5D&set=key%5Bresolve.allowImageUpscaling%5D,value%5B0%5D&set=key%5Bresolve.format%5D,value%5Bwebp%5D&product=path%5B/pimages/9780316565257_p0_v1%5D&call=url%5Bfile:common/decodeProduct.chain%5D",
				"9780316162500", BigDecimal.valueOf(34.99));
		book4.getAuthors().add(author4);
		
		Book book5 = new Book("Meet Me at the lake",
				"https://prodimage.images-bn.com/lf?set=key%5Bresolve.pixelRatio%5D,value%5B1%5D&set=key%5Bresolve.width%5D,value%5B300%5D&set=key%5Bresolve.height%5D,value%5B10000%5D&set=key%5Bresolve.imageFit%5D,value%5Bcontainerwidth%5D&set=key%5Bresolve.allowImageUpscaling%5D,value%5B0%5D&set=key%5Bresolve.format%5D,value%5Bwebp%5D&product=path%5B/pimages/9780593641279_p0_v1%5D&call=url%5Bfile:common/decodeProduct.chain%5D",
				"9780735243781", BigDecimal.valueOf(29.99));
		book5.getAuthors().add(author5);

		author1.getBooks().add(book1);
		author2.getBooks().add(book2);
		author3.getBooks().add(book3);
		author4.getBooks().add(book4);
		author5.getBooks().add(book5);
		
		Location location1 = new Location(50, 100, "ABC",book1);
		Location location2 = new Location(50, 101, "ABC",book1);
		Location location3 = new Location(60, 200, "CDE",book2);
		Location location4 = new Location(60, 220, "CDE",book3);
		Location location5 = new Location(60, 221, "CDE",book3);
		Location location6 = new Location(150, 250, "GHI",book4);
		Location location7 = new Location(240, 300, "GHI",book5);
		
		authorRepository.saveAll(Arrays.asList(author1, author2, author3, author4, author5));
		bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5));
		locationRepository.saveAll(Arrays.asList(location1,location2,location3,location4,location5,location6,location7));
		
		var encoder = new BCryptPasswordEncoder();
		
		Role role1 = new Role("ROLE_ADMIN");
		Role role2 = new Role("ROLE_USER");
		
		User user1 = new User("admin", encoder.encode("admin"), new HashSet<>(List.of(role1,role2)),3);
		User user2 = new User("user",encoder.encode("user"),new HashSet<>(List.of(role2)),2);
		
		roleRepository.saveAll(Arrays.asList(role1,role2));
		userRepository.saveAll(Arrays.asList(user1,user2));
		
		//FavoriteBook fb1 = new FavoriteBook(user1, book1);
		FavoriteBook fb2 = new FavoriteBook(user1, book2);
		FavoriteBook fb3 = new FavoriteBook(user1, book5);
		FavoriteBook fb4 = new FavoriteBook(user2, book5);
		FavoriteBook fb5 = new FavoriteBook(user2, book3);
		
		favoriteBookRepository.saveAll(Arrays.asList(fb2,fb3,fb4,fb5));
	}

}
