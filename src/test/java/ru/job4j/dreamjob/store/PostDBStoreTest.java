package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

class PostDBStoreTest {
    private static final BasicDataSource POOL = new Main().loadPoll();
    private static final PostDBStore STORE = new PostDBStore(POOL);

    @BeforeEach
    public void reset() {
        STORE.reset();
    }

    @Test
    public void whenCreatePost() {
        Post post = STORE.add(new Post(0, "Java Job", "Desk", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB")));
        Post postInDb = STORE.findById(post.getId());
        Assertions.assertThat(post.getName()).isEqualTo(postInDb.getName());
    }

    @Test
    public void whenFindById() {
        Post post = STORE.add(new Post(0, "Java", "Job", LocalDateTime.now(), new City(1, "YEKB")));
        Post postInDb = STORE.findById(post.getId());
        Assertions.assertThat(postInDb.getId()).isEqualTo(post.getId());
    }

    @Test
    public void whenCreateThenFindAll() {
        Post post1 = STORE.add(new Post(1, "Java1", "Desk1", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB1")));
        Post post2 = STORE.add(new Post(2, "Java2", "Desk2", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB2")));
        Post post3 = STORE.add(new Post(3, "Java3", "Desk3", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB3")));
        Post post4 = STORE.add(new Post(4, "Java4", "Desk4", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB4")));
        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post4);
        List<Post> expected = STORE.findAll();
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertThat(expected.get(i).getName()).isEqualTo(list.get(i).getName());
        }
    }

    @Test
    public void whenUpdate() {
        Post post1 = STORE.add(new Post(1, "Java1", "Desk1", LocalDateTime.now(ZoneId.systemDefault()), new City(1, "YEKB1")));
        STORE.add(post1);
        post1.setName("update Java");
        post1.setDescription("update desk");
        STORE.update(post1);
        Post postInDb = STORE.findById(post1.getId());
        Assertions.assertThat(postInDb.getName()).isEqualTo("update Java");
        Assertions.assertThat(postInDb.getDescription()).isEqualTo("update desk");
    }
}