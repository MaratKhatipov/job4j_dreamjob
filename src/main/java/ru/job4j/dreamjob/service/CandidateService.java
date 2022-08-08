package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;

public class CandidateService {

    private static final CandidateService INST = new CandidateService();
    private final CandidateStore store = CandidateStore.instOf();

    public CandidateService() {
    }

    public static CandidateService instanceOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
