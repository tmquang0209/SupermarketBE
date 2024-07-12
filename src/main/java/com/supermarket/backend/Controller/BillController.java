package com.supermarket.backend.Controller;

import com.supermarket.backend.Entity.*;
import com.supermarket.backend.Payload.Dto.CartDTO;
import com.supermarket.backend.Payload.Request.BillRequest;
import com.supermarket.backend.Security.Jwt.JwtUtils;
import com.supermarket.backend.Service.*;
import com.supermarket.backend.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private BillInfoService billInfoService;

    @Autowired
    private BillDetailsService billDetailsService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/create")
    public ApiResponse<?> createBill(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody BillRequest request) {
        try {
            boolean isValidToken = jwtUtils.validateJwtToken(bearerToken);
            if (!isValidToken) throw new Exception("Invalid token");

            String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
            EmployeeEntity employee = employeeService.getByUsername(username);
            request.setEmployee(employee);

            // check promotion code if exists
            if (request.getPromotionCode() != null) {
                PromotionEntity promotionEntity = promotionService.getByCode(request.getPromotionCode());
                if (promotionEntity == null)
                    throw new Exception("No promotional code found.");

                // check expired date
                Date currentDate = new Date();
                if (currentDate.before(promotionEntity.getStartDate())) {
                    throw new Exception("The start date to use discount code " + promotionEntity.getCode() + " has not yet arrived");
                }

                if (currentDate.after(promotionEntity.getEndDate())) {
                    productService.delete(promotionEntity.getId());
                    throw new Exception("Discount code " + promotionEntity.getCode() + " has expired. Please use another discount code");
                }

                request.setDiscount(promotionEntity.getDiscount());
                productService.delete(promotionEntity.getId());
            }

            // check item in cart and create bill details
            for (CartDTO cart : request.getCart()) {
                ProductEntity product = productService.getById(cart.getProductId());

                if (product == null)
                    throw new Exception("No product found with id: " + cart.getProductId());
            }

            // check point if exists
            if (request.getPoint() != 0 && !customerService.checkPoint(request.getCustomer().getId(), request.getPoint())) {
                throw new Exception("The user does not have enough points to purchase this order.");
            }

            // execute insert into db
            BillEntity bill = new BillEntity(request);
            BillEntity newBill = billService.save(bill);

            for (CartDTO cart : request.getCart()) {
                ProductEntity product = productService.getById(cart.getProductId());
                BillDetailsEntity billDetailsEntity = new BillDetailsEntity(newBill, product, cart.getQty(), product.getUnitPrice());

                billDetailsService.save(billDetailsEntity);
            }

            // update point for user
            return new ApiResponse<>(true, billInfoService.getBillInfoById(newBill.getId()), "Create bill successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getDetails(@PathVariable(value = "id") int id) {
        return new ApiResponse<>(true, billInfoService.getBillInfoById(id), "Get bill details successful.");
    }
}
