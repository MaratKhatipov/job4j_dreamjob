package ru.job4j.dreamjob.store;


import ru.job4j.dreamjob.model.Post;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    Calendar juniorCalendar = new GregorianCalendar(2022, Calendar.JANUARY, 17, 10, 26, 36);
    Calendar middleCalendar = new GregorianCalendar(2022, Calendar.AUGUST, 1, 8, 2, 48);
    Calendar seniorCalendar = Calendar.getInstance();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Работа начинающим программистом ", juniorCalendar));
        posts.put(2, new Post(2, "Middle Java Job", "Работа для тех у кого есть опыт ", middleCalendar));
        posts.put(3, new Post(3, "Senior Java Job", "Для Супер-Мега разработчиков ", seniorCalendar));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
