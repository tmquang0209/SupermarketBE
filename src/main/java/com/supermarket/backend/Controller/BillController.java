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
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillController {

    private final BillService billService;
    private final BillInfoService billInfoService;
    private final BillDetailsService billDetailsService;
    private final PromotionService promotionService;
    private final ProductService productService;
    private final EmployeeServiceImpl employeeService;
    private final CustomerService customerService;
    private final JwtUtils jwtUtils;

    @Autowired
    public BillController(
            BillService billService,
            BillInfoService billInfoService,
            BillDetailsService billDetailsService,
            PromotionService promotionService,
            ProductService productService,
            EmployeeServiceImpl employeeService,
            CustomerService customerService,
            JwtUtils jwtUtils) {
        this.billService = billService;
        this.billInfoService = billInfoService;
        this.billDetailsService = billDetailsService;
        this.promotionService = promotionService;
        this.productService = productService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/create")
    public ApiResponse<?> createBill(@RequestHeader(value = "Authorization") String bearerToken, @Valid @RequestBody BillRequest request) {
        try {
            validateToken(bearerToken);
            EmployeeEntity employee = getEmployeeFromToken(bearerToken);
            request.setEmployee(employee);

            handlePromotion(request);

            float total = calculateTotal(request.getCart());

            handleCustomerPoints(request, total);

            BillEntity bill = saveBill(request);
            saveBillDetails(request, bill);

            updateCustomerPoints(request, total);

            return new ApiResponse<>(true, billInfoService.getBillInfoById(bill.getId()), "Create bill successful.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse<>(false, null, e.getMessage());
        }
    }

    private void validateToken(String bearerToken) throws Exception {
        if (!jwtUtils.validateJwtToken(bearerToken)) {
            throw new Exception("Invalid token");
        }
    }

    private EmployeeEntity getEmployeeFromToken(String bearerToken) throws Exception {
        String username = jwtUtils.getUserNameFromJwtToken(bearerToken);
        EmployeeEntity employee = employeeService.getByUsername(username);
        if (employee == null) {
            throw new Exception("Employee not found");
        }
        return employee;
    }

    private void handlePromotion(BillRequest request) throws Exception {
        if (request.getPromotionCode() != null) {
            PromotionEntity promotionEntity = promotionService.getByCode(request.getCustomer().getId(), request.getPromotionCode());
            if (promotionEntity == null) {
                throw new Exception("No promotional code found.");
            }

            validatePromotionDates(promotionEntity);

            request.setDiscount(promotionEntity.getDiscount());
            productService.delete(promotionEntity.getId());
        }
    }

    private void validatePromotionDates(PromotionEntity promotionEntity) throws Exception {
        Date currentDate = new Date();
        if (currentDate.before(promotionEntity.getStartDate())) {
            throw new Exception("The start date to use discount code " + promotionEntity.getCode() + " has not yet arrived");
        }
        if (currentDate.after(promotionEntity.getEndDate())) {
            throw new Exception("Discount code " + promotionEntity.getCode() + " has expired. Please use another discount code");
        }
    }

    private float calculateTotal(List<CartDTO> cart) throws Exception {
        float total = 0;
        for (CartDTO cartItem : cart) {
            ProductEntity product = productService.getById(cartItem.getProductId());
            if (product == null) {
                throw new Exception("No product found with id: " + cartItem.getProductId());
            }
            total += product.getUnitPrice();
        }
        return total;
    }

    private void handleCustomerPoints(BillRequest request, float total) throws Exception {
        if (request.getCustomer() != null)
            if (request.getPoint() != 0 && !customerService.checkPoint(request.getCustomer().getId(), request.getPoint())) {
                throw new Exception("The user does not have enough points to purchase this order.");
            }
    }

    private BillEntity saveBill(BillRequest request) {
        BillEntity bill = new BillEntity(request);
        return billService.save(bill);
    }

    private void saveBillDetails(BillRequest request, BillEntity bill) throws Exception {
        for (CartDTO cartItem : request.getCart()) {
            ProductEntity product = productService.getById(cartItem.getProductId());
            BillDetailsEntity billDetailsEntity = new BillDetailsEntity(bill, product, cartItem.getQty(), product.getUnitPrice());
            billDetailsService.save(billDetailsEntity);
        }
    }

    private void updateCustomerPoints(BillRequest request, float total) throws Exception {
        if (request.getCustomer() != null) {
            CustomerEntity customer = customerService.getById(request.getCustomer().getId());
            if (customer == null) {
                throw new Exception("Can not find user with id: " + request.getCustomer().getId());
            }

            total -= total * request.getDiscount() / 100;
            int point = (int) Math.ceil(total / 1000) + customer.getPoint();
            customer.setPoint(point);
            customerService.update(customer.getId(), customer);
        }
    }

    @GetMapping("/{id}/details")
    public ApiResponse<?> getDetails(@PathVariable(value = "id") int id) {
        return new ApiResponse<>(true, billInfoService.getBillInfoById(id), "Get bill details successful.");
    }
}
