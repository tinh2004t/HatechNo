package com.hatechno.service;

import com.hatechno.model.Apartment;
import com.hatechno.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Optional<Apartment> getApartmentById(int id) {
        return apartmentRepository.findById(id);
    }

    public Apartment saveApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(int id) {
        apartmentRepository.deleteById(id);
    }
}
