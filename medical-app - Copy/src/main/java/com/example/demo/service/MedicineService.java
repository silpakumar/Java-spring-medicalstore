package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Medicine;
import com.example.demo.repository.MedicineRepository;

@Transactional
@Service("medicineService")
public class MedicineService {
    private MedicineRepository medicineRepository;

    @Autowired
    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Optional<Medicine> findById(Integer id) {
        return medicineRepository.findById(id);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public void saveMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

	public void deleteMedicine(int id) {
        medicineRepository.deleteById(id);

	}



}
