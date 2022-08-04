package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Работа начинающим программистом ", LocalDateTime.of(2022, 1, 17, 8, 32)));
        posts.put(2, new Post(2, "Middle Java Job", "Работа для тех у кого есть опыт ", LocalDateTime.of(2022, 8, 1, 10, 26)));
        posts.put(3, new Post(3, "Senior Java Job", "Для Супер-Мега разработчиков ", LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
