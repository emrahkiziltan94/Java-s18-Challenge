package com.workintech.s18challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.s18challenge.controller.AuthorController;
import com.workintech.s18challenge.controller.BookController;
import com.workintech.s18challenge.controller.CategoryController;
import com.workintech.s18challenge.entity.Author;
import com.workintech.s18challenge.entity.Book;
import com.workintech.s18challenge.entity.Category;
import com.workintech.s18challenge.service.AuthorService;
import com.workintech.s18challenge.service.BookService;
import com.workintech.s18challenge.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {AuthorController.class, BookController.class, CategoryController.class})
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService mockAuthorService;

    @MockBean
    private BookService mockBookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Author sampleAuthorAuthorController;
    private Book sampleBookAuthorController;

    @MockBean
    private CategoryService mockCategoryService;

    private Book sampleBookBookController;
    private Category sampleCategoryBookController;
    private Author sampleAuthorBookController;
    private Category sampleCategoryCategoryController;

    @BeforeEach
    void setUp() {
        sampleAuthorAuthorController = new Author();
        sampleAuthorAuthorController.setId(1);
        sampleAuthorAuthorController.setFirstName("John");
        sampleAuthorAuthorController.setLastName("Doe");

        sampleBookAuthorController = new Book();
        sampleBookAuthorController.setId(1L);
        sampleBookAuthorController.setName("Sample Book");
        Category category = new Category();
        category.setId(1);
        category.setName("Sample Category");
        sampleBookAuthorController.setCategory(category);

        sampleCategoryBookController = new Category();
        sampleCategoryBookController.setId(1L);
        sampleCategoryBookController.setName("Fiction");

        sampleAuthorBookController = new Author();
        sampleAuthorBookController.setId(1L);
        sampleAuthorBookController.setFirstName("John");
        sampleAuthorBookController.setLastName("Doe");

        sampleBookBookController = new Book();
        sampleBookBookController.setId(1L);
        sampleBookBookController.setName("Sample Book");


        sampleCategoryCategoryController = new Category();
        sampleCategoryCategoryController.setId(1L);
        sampleCategoryCategoryController.setName("Fiction");

    }

    @Test
    @DisplayName("test save author in author controller layer")
    void testSaveAuthorOnAuthorController() throws Exception {
        given(mockAuthorService.save(any())).willReturn(sampleAuthorAuthorController);

        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthorAuthorController)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test save author with book in author controller layer")
    void testSaveAuthorWithBookOnAuthorController() throws Exception {
        given(mockBookService.findById(sampleBookAuthorController.getId())).willReturn(sampleBookAuthorController);
        given(mockAuthorService.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/author/{bookId}", sampleBookAuthorController.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthorAuthorController)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test save book in book controller layer")
    void testSaveBookWithCategoryBookController() throws Exception {
        given(mockCategoryService.findById(sampleCategoryBookController.getId())).willReturn(sampleCategoryBookController);
        given(mockBookService.save(any())).willReturn(sampleBookBookController);

        mockMvc.perform(post("/book/{categoryId}", sampleCategoryBookController.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookBookController)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) sampleBookBookController.getId())))
                .andExpect(jsonPath("$.name", is(sampleBookBookController.getName())))
                .andExpect(jsonPath("$.categoryName", is(sampleCategoryBookController.getName())))
                .andExpect(jsonPath("$.authorResponse").doesNotExist());

        verify(mockCategoryService).findById(sampleCategoryBookController.getId());
        verify(mockBookService).save(any());
    }

    @Test
    @DisplayName("test save book with author and category in book controller layer")
    void testSaveBookWithAuthorAndCategoryBookController() throws Exception {
        given(mockCategoryService.findById(sampleCategoryBookController.getId())).willReturn(sampleCategoryBookController);
        given(mockAuthorService.findById(sampleAuthorBookController.getId())).willReturn(sampleAuthorBookController);
        given(mockBookService.save(any())).willReturn(sampleBookBookController);

        mockMvc.perform(post("/book/saveByAuthor?categoryId={categoryId}&authorId={authorId}", sampleCategoryBookController.getId(), sampleAuthorBookController.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookBookController)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) sampleBookBookController.getId())))
                .andExpect(jsonPath("$.name", is(sampleBookBookController.getName())))
                .andExpect(jsonPath("$.categoryName", is(sampleCategoryBookController.getName())))
                .andExpect(jsonPath("$.authorResponse.id", is((int) sampleAuthorBookController.getId())))
                .andExpect(jsonPath("$.authorResponse.authorName", is(sampleAuthorBookController.getFirstName() + " " + sampleAuthorBookController.getLastName())));

        verify(mockCategoryService).findById(sampleCategoryBookController.getId());
        verify(mockAuthorService).findById(sampleAuthorBookController.getId());
        verify(mockBookService).save(any());
    }


    @Test
    @DisplayName("test save category in category controller layer")
    void testSaveCategoryCategoryController() throws Exception {
        given(mockCategoryService.save(sampleCategoryCategoryController)).willReturn(sampleCategoryCategoryController);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCategoryCategoryController)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fiction")));


    }

    @Test
    @DisplayName("test get category in category controller layer")
    void testGetCategoryCategoryController() throws Exception {
        when(mockCategoryService.findById(sampleCategoryCategoryController.getId())).thenReturn(sampleCategoryCategoryController);

        mockMvc.perform(get("/category/{id}", sampleCategoryCategoryController.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fiction")));


    }
}
