package com.example.demo.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Medicine;


@Repository("medicineRepository")
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
	
	Optional<Medicine> findById(Integer id);



	
	
	
}
