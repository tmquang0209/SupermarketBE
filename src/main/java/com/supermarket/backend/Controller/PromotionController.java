package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.PromotionEntity;
import com.supermarket.backend.Payload.Request.PromotionRequest;
import com.supermarket.backend.Service.PromotionService;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping("/create")
    public ApiResponse<?> createPromotion(@Valid @RequestBody PromotionRequest request) {
        try {
            PromotionEntity newPromotion = new PromotionEntity(request);
            newPromotion = promotionService.savePromotion(newPromotion);
            return new ApiResponse<>(true, newPromotion, "Create promotion successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<?> getAllPromotions() {
        try {
            List<PromotionEntity> promotionEntities = promotionService.getAllPromotions();
            return new ApiResponse<>(true, promotionEntities, "Get all promotions successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/customer")
    public ApiResponse<?> getPromotionsOfCustomer(@PathVariable(name = "id") Integer customerId) {
        try {
            List<PromotionEntity> promotionEntities = promotionService.getAllPromotions();
            return new ApiResponse<>(true, promotionEntities, "Get all promotions of customer's id: " + customerId + " successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<?> search(@RequestParam(value = "code") String code) {
        try {
            PromotionEntity promotionEntity = promotionService.getByCode(code);
            return new ApiResponse<>(true, promotionEntity, "Get code promotions successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getById(@PathVariable(name = "id") Integer id) {
        try {
            PromotionEntity promotionEntity = promotionService.getById(id);
            return new ApiResponse<>(true, promotionEntity, "Get details successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/{id}/update")
    public ApiResponse<?> update(@PathVariable(name = "id") Integer id, @Valid @RequestBody PromotionRequest request) {
        try {
            PromotionEntity promotionEntity = promotionService.update(id, request);
            return new ApiResponse<>(true, promotionEntity, "Update promotion successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ApiResponse<?> delete(@PathVariable(name = "id") Integer id) {
        try {
            promotionService.delete(id);
            return new ApiResponse<>(true, null, "Delete promotion successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
