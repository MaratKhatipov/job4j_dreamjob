package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final  AtomicInteger num = new AtomicInteger();

    private PostStore() {
        posts.put(num.incrementAndGet(), new Post(num.get(), "Junior Java Job", "Работа начинающим программистом ", LocalDateTime.of(2022, 1, 17, 8, 32)));
        posts.put(num.incrementAndGet(), new Post(num.get(), "Middle Java Job", "Работа для тех у кого есть опыт ", LocalDateTime.of(2022, 8, 1, 10, 26)));
        posts.put(num.incrementAndGet(), new Post(num.get(), "Senior Java Job", "Для Супер-Мега разработчиков ", LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        num.incrementAndGet();
        post.setId(num.get());
        post.setCreated(LocalDateTime.now());
        posts.put(num.get(), post);
    }
}
