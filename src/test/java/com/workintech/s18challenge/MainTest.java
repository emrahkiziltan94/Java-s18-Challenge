package com.workintech.s18challenge;

import com.workintech.s18challenge.entity.Author;
import com.workintech.s18challenge.entity.Book;
import com.workintech.s18challenge.entity.Category;
import com.workintech.s18challenge.repository.AuthorRepository;
import com.workintech.s18challenge.repository.BookRepository;
import com.workintech.s18challenge.repository.CategoryRepository;
import com.workintech.s18challenge.service.AuthorServiceImpl;
import com.workintech.s18challenge.service.BookServiceImpl;
import com.workintech.s18challenge.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
 class MainTest {
    private Book book;
    private Author author;
    private Category category;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author sampleAuthor;
    private Book sampleBookRepoBook;
    private Author sampleBookRepoAuthor;
    private Category sampleBookRepoCategory;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category sampleCategoryRepoCategory;

    @Mock
    private AuthorRepository mockAuthorRepository;


    private AuthorServiceImpl authorServiceInjected;

    private Author sampleAuthorServiceTest;
    @Mock
    private BookRepository mockBookRepository;


    private BookServiceImpl bookServiceInjected;

    private Book sampleBookBookServiceTest;

    @Mock
    private CategoryRepository mockCategoryRepository;


    private CategoryServiceImpl categoryService;

    private Category sampleCategoryCategoryServiceTest;
    @BeforeEach
    void setUp() {
        book = new Book();
        author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        category = new Category();
        category.setName("Fiction");


        sampleAuthor = new Author();
        sampleAuthor.setFirstName("John");
        sampleAuthor.setLastName("Doe");

        sampleAuthor = entityManager.persistFlushFind(sampleAuthor);

        sampleBookRepoAuthor = new Author();
        sampleBookRepoAuthor.setFirstName("John");
        sampleBookRepoAuthor.setLastName("Doe");
        sampleBookRepoAuthor = entityManager.persistFlushFind(sampleBookRepoAuthor);


        sampleBookRepoCategory = new Category();
        sampleBookRepoCategory.setName("Fiction");
        sampleBookRepoCategory = entityManager.persistFlushFind(sampleBookRepoCategory);


        sampleBookRepoBook = new Book();
        sampleBookRepoBook.setName("The Great Adventure");
        sampleBookRepoBook.setAuthor(sampleBookRepoAuthor);
        sampleBookRepoBook.setCategory(sampleBookRepoCategory);
        sampleBookRepoBook = entityManager.persistFlushFind(sampleBookRepoBook);

        sampleCategoryRepoCategory = new Category();
        sampleCategoryRepoCategory.setName("Fiction");
        entityManager.persist(sampleCategoryRepoCategory);
        entityManager.flush();

        authorServiceInjected = new AuthorServiceImpl(mockAuthorRepository);

        sampleAuthorServiceTest = new Author();
        sampleAuthorServiceTest.setId(1L);
        sampleAuthorServiceTest.setFirstName("John");
        sampleAuthorServiceTest.setLastName("Doe");


        bookServiceInjected = new BookServiceImpl(mockBookRepository);
        sampleBookBookServiceTest = new Book();
        sampleBookBookServiceTest.setId(1L);
        sampleBookBookServiceTest.setName("Test Book");


        categoryService = new CategoryServiceImpl(mockCategoryRepository);
        sampleCategoryCategoryServiceTest = new Category();
        sampleCategoryCategoryServiceTest.setId(1L);
        sampleCategoryCategoryServiceTest.setName("Fiction");

    }

    @Test
    @DisplayName("Test book author relation")
    void addBookToAuthor() {
        assertNull(author.getBooks());
        author.addBook(book);
        assertNotNull(author.getBooks());
        assertEquals(1, author.getBooks().size());
        assertEquals(book, author.getBooks().get(0));
    }

    @Test
    @DisplayName("Test author getter setters")
    void testAuthorSettersAndGetters() {
        author.setId(1L);
        author.setFirstName("Jane");
        author.setLastName("Doe");

        assertEquals(1L, author.getId());
        assertEquals("Jane", author.getFirstName());
        assertEquals("Doe", author.getLastName());
    }




    @Test
    @DisplayName("Test book entity getters and setters and relationships")
    void testBookSettersAndGetters() {
        book.setName("Example Book");
        book.setAuthor(author);
        book.setCategory(category);

        assertEquals("Example Book", book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals("John", book.getAuthor().getFirstName());
        assertEquals("Doe", book.getAuthor().getLastName());
        assertEquals(category, book.getCategory());
        assertEquals("Fiction", book.getCategory().getName());
    }



    @Test
    void testIdSetterAndGetter() {
        long idValue = 1L;
        category.setId(idValue);
        assertEquals(idValue, category.getId(), "Getter and setter for 'id' field did not work as expected.");
    }

    @Test
    void testNameSetterAndGetter() {
        String nameValue = "Fiction";
        category.setName(nameValue);
        assertEquals(nameValue, category.getName(), "Getter and setter for 'name' field did not work as expected.");
    }

    @Test
    void testBooksSetterAndGetter() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        books.add(book);
        category.setBooks(books);
        assertEquals(books, category.getBooks(), "Getter and setter for 'books' field did not work as expected.");
        assertEquals(1, category.getBooks().size(), "The size of 'books' collection should be 1.");
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        category.addBook(book);
        assertNotNull(category.getBooks(), "The 'books' collection should not be null after adding a book.");
        assertEquals(1, category.getBooks().size(), "The 'books' collection should contain 1 book after adding.");
    }

    @Test
    void testSaveAndFindById() {

        Optional<Author> foundAuthor = authorRepository.findById(sampleAuthor.getId());
        assertTrue(foundAuthor.isPresent(), "Author should be present in the database.");
        assertEquals(sampleAuthor.getFirstName(), foundAuthor.get().getFirstName(), "First names should match.");
        assertEquals(sampleAuthor.getLastName(), foundAuthor.get().getLastName(), "Last names should match.");
    }

    @Test
    void testUpdateAuthor() {

        sampleAuthor.setFirstName("Jane");
        sampleAuthor.setLastName("Smith");
        authorRepository.save(sampleAuthor);


        Optional<Author> updatedAuthor = authorRepository.findById(sampleAuthor.getId());
        assertTrue(updatedAuthor.isPresent(), "Updated author should be present.");
        assertEquals("Jane", updatedAuthor.get().getFirstName(), "Updated first name should be Jane.");
        assertEquals("Smith", updatedAuthor.get().getLastName(), "Updated last name should be Smith.");
    }

    @Test
    void testDeleteAuthor() {

        authorRepository.delete(sampleAuthor);
        Optional<Author> deletedAuthor = authorRepository.findById(sampleAuthor.getId());
        assertTrue(deletedAuthor.isEmpty(), "Deleted author should not be present in the database.");
    }


    @Test
    void testSaveAndFindByIdBookRepo() {

        Optional<Book> foundBook = bookRepository.findById(sampleBookRepoBook.getId());
        assertTrue(foundBook.isPresent(), "Book should be present in the database.");
        assertEquals(sampleBookRepoBook.getName(), foundBook.get().getName(), "Book names should match.");
    }

    @Test
    void testUpdateBook() {

        sampleBookRepoBook.setName("The Great Adventure Revised");
        bookRepository.save(sampleBookRepoBook);


        Optional<Book> updatedBook = bookRepository.findById(sampleBookRepoBook.getId());
        assertTrue(updatedBook.isPresent(), "Updated book should be present.");
        assertEquals("The Great Adventure Revised", updatedBook.get().getName(), "Updated book name should match.");
    }

    @Test
    void testDeleteBook() {

        bookRepository.delete(sampleBookRepoBook);


        Optional<Book> deletedBook = bookRepository.findById(sampleBookRepoBook.getId());
        assertTrue(deletedBook.isEmpty(), "Deleted book should not be present in the database.");
    }
    @Test
    void testSaveAndFindByIdCategory() {

        Optional<Category> foundCategory = categoryRepository.findById(sampleCategoryRepoCategory.getId());
        assertTrue(foundCategory.isPresent(), "Category should be present in the database.");
        assertEquals(sampleCategoryRepoCategory.getName(), foundCategory.get().getName(), "Category names should match.");
    }

    @Test
    void testUpdateCategory() {

        sampleCategoryRepoCategory.setName("Non-Fiction");
        categoryRepository.save(sampleCategoryRepoCategory);


        Optional<Category> updatedCategory = categoryRepository.findById(sampleCategoryRepoCategory.getId());
        assertTrue(updatedCategory.isPresent(), "Updated category should be present.");
        assertEquals("Non-Fiction", updatedCategory.get().getName(), "Updated category name should match.");
    }

    @Test
    void testDeleteCategory() {

        categoryRepository.delete(sampleCategoryRepoCategory);


        Optional<Category> deletedCategory = categoryRepository.findById(sampleCategoryRepoCategory.getId());
        assertTrue(deletedCategory.isEmpty(), "Deleted category should not be present in the database.");
    }
    @Test
    void testFindByIdFoundAuthor() {
        when(mockAuthorRepository.findById(any())).thenReturn(Optional.of(sampleAuthorServiceTest));

        Author foundAuthor = authorServiceInjected.findById(sampleAuthorServiceTest.getId());

        assertNotNull(foundAuthor, "Author should not be null");
        assertEquals(sampleAuthorServiceTest.getId(), foundAuthor.getId(), "Author IDs should match");
        assertEquals(sampleAuthorServiceTest.getFirstName(), foundAuthor.getFirstName(), "First names should match");
        assertEquals(sampleAuthorServiceTest.getLastName(), foundAuthor.getLastName(), "Last names should match");

        verify(mockAuthorRepository).findById(sampleAuthorServiceTest.getId());
    }

    @Test
    void testFindByIdNotFoundAuthor() {
        //given(mockAuthorRepository.findById(anyLong())).willReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authorServiceInjected.findById(999L);
        });

        assertTrue(exception.getMessage().contains("not found"), "Exception message should contain 'not found'");
    }

    @Test
    void testSaveAuthor() {
        given(mockAuthorRepository.save(any(Author.class))).willReturn(sampleAuthorServiceTest);

        Author savedAuthor = authorServiceInjected.save(new Author());

        assertNotNull(savedAuthor, "Saved author should not be null");
        assertEquals(sampleAuthorServiceTest.getId(), savedAuthor.getId(), "Author IDs should match");

        verify(mockAuthorRepository).save(any(Author.class));
    }

    @Test
    @DisplayName("Test findById with found in book  service layer")
    void testFindByIdFoundBook() {
        given(mockBookRepository.findById(sampleBookBookServiceTest.getId())).willReturn(Optional.of(sampleBookBookServiceTest));

        Book foundBook = bookServiceInjected.findById(sampleBookBookServiceTest.getId());

        assertNotNull(foundBook, "Book should not be null");
        assertEquals(sampleBookBookServiceTest.getId(), foundBook.getId(), "Book IDs should match");
        assertEquals(sampleBookBookServiceTest.getName(), foundBook.getName(), "Book names should match");
    }

    @Test
    @DisplayName("Test findById with not found in book service")
    void testFindByIdNotFoundBook() {
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookServiceInjected.findById(999L));
        assertTrue(exception.getMessage().contains("not found"), "Exception message should contain 'not found'");
    }

    @Test
    @DisplayName("Test save in book service")
    void testSaveBook() {
        given(mockBookRepository.save(any(Book.class))).willReturn(sampleBookBookServiceTest);

        Book savedBook = bookServiceInjected.save(new Book());

        assertNotNull(savedBook, "Saved book should not be null");
        assertEquals(sampleBookBookServiceTest.getId(), savedBook.getId(), "Book IDs should match");
    }

    @Test
    @DisplayName("Test findById with found in category service layer")
    void testFindByIdFoundCategoryService() {
        given(mockCategoryRepository.findById(sampleCategoryCategoryServiceTest.getId())).willReturn(Optional.of(sampleCategoryCategoryServiceTest));

        Category foundCategory = categoryService.findById(sampleCategoryCategoryServiceTest.getId());

        assertNotNull(foundCategory, "The category should not be null.");
        assertEquals(sampleCategoryCategoryServiceTest.getId(), foundCategory.getId(), "Category IDs should match.");
        assertEquals(sampleCategoryCategoryServiceTest.getName(), foundCategory.getName(), "Category names should match.");
    }

    @Test
    @DisplayName("Test findById with not found in category service")
    void testFindByIdNotFoundCategoryService() {
        given(mockCategoryRepository.findById(anyLong())).willReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.findById(999L));
        assertTrue(exception.getMessage().contains("not found"), "Exception message should contain 'not found'.");
    }

    @Test
    @DisplayName("Test save in category service")
    void testSaveCategoryService() {
        given(mockCategoryRepository.save(any(Category.class))).willReturn(sampleCategoryCategoryServiceTest);

        Category savedCategory = categoryService.save(new Category());

        assertNotNull(savedCategory, "Saved category should not be null.");
        assertEquals(sampleCategoryCategoryServiceTest.getId(), savedCategory.getId(), "Category IDs should match.");
    }
}
