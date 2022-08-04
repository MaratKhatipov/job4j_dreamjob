package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Oezcan Acar", "Есть опыт в разработке 10 лет", LocalDateTime.of(2012, 4, 15, 7, 45)));
        candidates.put(2, new Candidate(2, "Dan Allen", "что-то знаю, что-то могу", LocalDateTime.of(2018, 10, 20, 10, 24)));
        candidates.put(3, new Candidate(3, "Dion Almaer", "тяжело уволить, легко потерять, невозможно найти резюме", LocalDateTime.of(2020, 1, 17, 14, 3)));
        candidates.put(4, new Candidate(4, "Tagir Valeev ", "Легенда", LocalDateTime.now()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        System.out.println(candidates.values());
        return candidates.values();
    }
}
