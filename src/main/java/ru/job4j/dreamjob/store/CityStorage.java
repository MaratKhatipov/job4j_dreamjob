package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CityStorage {

    private final Map<Integer, City> citiesStorage = new ConcurrentHashMap<>();
    private final AtomicInteger num = new AtomicInteger();

    public CityStorage() {
        citiesStorage.put(num.incrementAndGet(), new City(num.get(), "Moscow"));
        citiesStorage.put(num.incrementAndGet(), new City(num.get(), "Saint Petersburg "));
        citiesStorage.put(num.incrementAndGet(), new City(num.get(), "Yekaterinburg"));
    }

    public Collection<City> getAllCities() {
        return citiesStorage.values();
    }

    public City findById(int id) {
        return citiesStorage.get(id);
    }
}
