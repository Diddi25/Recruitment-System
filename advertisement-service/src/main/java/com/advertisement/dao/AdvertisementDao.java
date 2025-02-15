package com.advertisement.dao;

import com.advertisement.domain.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AdvertisementDao {
    List<Advertisement> findAll();
    Optional<Advertisement> findById(int id);
    Advertisement save(Advertisement advertisement);
    Optional<Advertisement> update(int id, Advertisement advertisement);
    boolean delete(int id);
    Optional<Advertisement> updateStatus(int id, String status);
}

@Repository
class AdvertisementDaoImpl implements AdvertisementDao {
    private final List<Advertisement> advertisements = new ArrayList<>();

    public AdvertisementDaoImpl() {
        // Dummy data
        advertisements.add(new Advertisement(1, "Karusell", "Anna", "unhandled"));
        advertisements.add(new Advertisement(2, "Bergodalbana", "Diddi", "rejected"));
        advertisements.add(new Advertisement(3, "Frittfall", "Mike", "accepted"));
    }

    @Override
    public List<Advertisement> findAll() {
        return new ArrayList<>(advertisements);
    }

    @Override
    public Optional<Advertisement> findById(int id) {
        return advertisements.stream()
                .filter(ad -> ad.getId() == id)
                .findFirst();
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        int newId = advertisements.size() + 1;
        advertisement.setId(newId);
        advertisements.add(advertisement);
        return advertisement;
    }

    @Override
    public Optional<Advertisement> update(int id, Advertisement advertisement) {
        for (int i = 0; i < advertisements.size(); i++) {
            if (advertisements.get(i).getId() == id) {
                advertisement.setId(id);
                advertisements.set(i, advertisement);
                return Optional.of(advertisement);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return advertisements.removeIf(ad -> ad.getId() == id);
    }

    @Override
    public Optional<Advertisement> updateStatus(int id, String status) {
        return findById(id).map(ad -> {
            ad.setStatus(status);
            return ad;
        });
    }
}