package com.hatechno.controller;

import com.hatechno.model.Apartment;
import com.hatechno.service.ApartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apartments")
@Tag(name = "Apartments", description = "Quản lý căn hộ")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @Operation(summary = "Lấy danh sách căn hộ", description = "Trả về danh sách tất cả căn hộ")
    @GetMapping
    public List<Apartment> getAllApartments() {
        return apartmentService.getAllApartments();
    }

    @GetMapping("/{id}")
    public Optional<Apartment> getApartmentById(@PathVariable int id) {
        return apartmentService.getApartmentById(id);
    }
    
    @PutMapping("/{id}")
    public Apartment updateApartment(@PathVariable int id, @RequestBody Apartment apartment) {
        return apartmentService.updateApartment(id, apartment);
    }

    @PostMapping
    public Apartment createApartment(@RequestBody Apartment apartment) {
        return apartmentService.saveApartment(apartment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa căn hộ", description = "Xóa căn hộ theo ID")
    public ResponseEntity<String> deleteApartment(@PathVariable int id) {
        try {
            apartmentService.deleteApartment(id);
            return ResponseEntity.ok("Xóa căn hộ thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa căn hộ: " + e.getMessage());
        }
    }

}
