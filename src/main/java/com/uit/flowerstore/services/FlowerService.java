package com.uit.flowerstore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.Flower;
import com.uit.flowerstore.domain.UserPayment;
import com.uit.flowerstore.repository.FlowerRepository;
import com.uit.flowerstore.repository.UserPaymentRepository;

@Service
public class FlowerService {
	
	private final FlowerRepository flowerRepository;

	public FlowerService(FlowerRepository flowerRepository) {
		this.flowerRepository = flowerRepository;
	}
	public Flower createFlower(Flower flower) {
    	return flowerRepository.save(flower);
    }

    public List<Flower> getAllFlowers() {
		return flowerRepository.findAll();
        
    }
	

	

}
