package ru.job4j.dreamjob.store;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class PostStore {

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final  AtomicInteger num = new AtomicInteger();

    private PostStore() {
        posts.put(num.incrementAndGet(),
                new Post(num.get(),
                        "Junior Java Job", "Работа начинающим программистом ",
                        LocalDateTime.of(2022, 1, 17, 8, 32), new CityStorage().findById(num.get())));
        posts.put(num.incrementAndGet(),
                new Post(num.get(),
                        "Middle Java Job", "Работа для тех у кого есть опыт ",
                        LocalDateTime.of(2022, 8, 1, 10, 26), new CityStorage().findById(num.get())));
        posts.put(num.incrementAndGet(),
                new Post(num.get(),
                        "Senior Java Job", "Для Супер-Мега разработчиков ",
                        LocalDateTime.now(), new CityStorage().findById(num.get())));
    }


    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(num.incrementAndGet());
        post.setCreated(LocalDateTime.now());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        post.setCreated(LocalDateTime.now());
        posts.replace(post.getId(), post);
    }
}
