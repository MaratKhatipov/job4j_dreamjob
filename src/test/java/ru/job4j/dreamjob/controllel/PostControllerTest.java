package ru.job4j.dreamjob.controllel;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );

        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService);
        String page = postController.posts(model, session);
        System.out.println(page);
        verify(model).addAttribute("posts", posts);
        assertEquals(page, "posts");
    }

    @Test
    void addPost() {
        List<City> cities = Arrays.asList(
                new City(1, "City1"),
                new City(2, "City2")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(
                postService,
                cityService);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("post", new Post());
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "addPost");
    }

    @Test
    void whenCreatPost() {
        Post input = new Post(1, "new Post");
        City city = new City(1, "City");
        input.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.creatPost(input);
        verify(postService).add(input);
        assertEquals(page, "redirect:/posts");
    }

    @Test
    void formUpdatePost() {
        List<City> cities = Arrays.asList(
                new City(1, "City1"),
                new City(2, "City2")
        );
        Post input = new Post(1, "new Post");
        input.setCity(cities.get(1));
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findById(1)).thenReturn(input);
        when(cityService.getAllCities()).thenReturn(cities);

        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, 1, session);
        verify(model).addAttribute("post", input);
        verify(model).addAttribute("cities", cities);
        assertEquals(page, "updatePost");
    }

    @Test
    void updatePost() {
        Post post = new Post(1, "new Post");
        City city = new City(1, "City");
        post.setCity(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);

        PostController postController = new PostController(
                postService,
                cityService
        );

        String page = postController.updatePost(post);
        verify(postService).update(post);
        assertEquals(page, "redirect:/posts");
    }
}