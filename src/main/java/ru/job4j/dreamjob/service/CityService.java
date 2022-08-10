package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.store.CityStorage;

import java.util.Collection;

@Service
public class CityService {

    private final CityStorage storage;

    public CityService(CityStorage storage) {
        this.storage = storage;
    }

    public Collection<City> getAllCities() {
        return storage.getAllCities();
    }

    public City findById(int id) {
        return storage.findById(id);
    }
}
