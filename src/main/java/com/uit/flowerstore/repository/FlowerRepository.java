package com.uit.flowerstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uit.flowerstore.domain.Flower;


public interface FlowerRepository extends JpaRepository<Flower, String> {
	@SuppressWarnings("unchecked")
	Flower save(Flower flower);
	List < Flower > findAll();


}
