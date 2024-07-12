package com.supermarket.backend.Service;

import com.supermarket.backend.Entity.PromotionEntity;
import com.supermarket.backend.Payload.Request.PromotionRequest;
import com.supermarket.backend.Repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public List<PromotionEntity> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public List<PromotionEntity> getByCustomerId(Integer id) {
        return promotionRepository.findByCusId(id);
    }

    public PromotionEntity getById(Integer id) {
        return promotionRepository.findById(id).orElse(null);
    }

    public PromotionEntity getByCode(String code) {
        return promotionRepository.findByCode(code).orElse(null);
    }

    public PromotionEntity savePromotion(PromotionEntity promotionEntity) {
        return promotionRepository.save(promotionEntity);
    }

    public PromotionEntity update(Integer id, PromotionRequest promotionRequest) {
        Optional<PromotionEntity> optionalPromotion = promotionRepository.findById(id);

        if (optionalPromotion.isEmpty()) throw new RuntimeException("Promotion does not exist.");

        PromotionEntity promotion = optionalPromotion.get();
        promotion.setCode(promotionRequest.getCode());
        promotion.setCustomer(promotionRequest.getCustomer());
        promotion.setDescription(promotionRequest.getDescription());
        promotion.setStartDate(promotionRequest.getStartDate());
        promotion.setEndDate(promotionRequest.getEndDate());

        return promotionRepository.save(promotion);
    }

    public void delete(Integer id) {
        Optional<PromotionEntity> optionalPromotion = promotionRepository.findById(id);

        if (optionalPromotion.isEmpty()) {
            throw new RuntimeException("Promotion does not exist.");
        }

        promotionRepository.deleteById(id);
    }

}
