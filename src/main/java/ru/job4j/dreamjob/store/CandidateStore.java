package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger ids = new AtomicInteger();

    private CandidateStore() {
        candidates.put(ids.incrementAndGet(), new Candidate(ids.get(), "Oezcan Acar", "Есть опыт в разработке 10 лет", LocalDateTime.of(2012, 4, 15, 7, 45)));
        candidates.put(ids.incrementAndGet(), new Candidate(ids.get(), "Dan Allen", "что-то знаю, что-то могу", LocalDateTime.of(2018, 10, 20, 10, 24)));
        candidates.put(ids.incrementAndGet(), new Candidate(ids.get(), "Dion Almaer", "тяжело уволить, легко потерять, невозможно найти резюме", LocalDateTime.of(2020, 1, 17, 14, 3)));
        candidates.put(ids.incrementAndGet(), new Candidate(ids.get(), "Tagir Valeev ", "Легенда", LocalDateTime.now()));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(ids.incrementAndGet());
        candidate.setCreated(LocalDateTime.now());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidate.setCreated(LocalDateTime.now());
        candidates.replace(candidate.getId(), candidate);
    }
}
