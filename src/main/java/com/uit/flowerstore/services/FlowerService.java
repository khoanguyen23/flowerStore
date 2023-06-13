package com.uit.flowerstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uit.flowerstore.domain.Flower;
import com.uit.flowerstore.repository.FlowerRepository;

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
    public void deleteFlower(String id) {
        flowerRepository.deleteById(id);
    }
    public Flower updateFlower(String id, Flower updatedFlower) {
        Optional<Flower> optionalFlower = flowerRepository.findById(id);
        if (optionalFlower.isPresent()) {
            Flower flower = optionalFlower.get();
            // Cập nhật thông tin hoa từ updatedFlower
            flower.setName(updatedFlower.getName());
            flower.setFullDescription(updatedFlower.getFullDescription());
            flower.setShortDescription(updatedFlower.getShortDescription());
            flower.setCategory(updatedFlower.getCategory());
            flower.setImage(updatedFlower.getImage());
            flower.setTag(updatedFlower.getTag());
            flower.setDiscount(updatedFlower.getDiscount());
            flower.setRating(updatedFlower.getRating());
            flower.setOfferEnd(updatedFlower.getOfferEnd());
            flower.setStock(updatedFlower.getStock());
            flower.setPrice(updatedFlower.getPrice());
            // Lưu hoa đã được cập nhật vào cơ sở dữ liệu
            return flowerRepository.save(flower);
        } else {
            return null;
        }
    }
    public Flower getFlowerById(String flowerId) {
        Optional<Flower> flowerOptional = flowerRepository.findById(flowerId);
        return flowerOptional.orElse(null);
    }
    
}
