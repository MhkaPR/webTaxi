package ir.mhkapr.webtaxi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.mhkapr.webtaxi.DTOs.OrderRequest;
import ir.mhkapr.webtaxi.DTOs.OrderResponse;
import ir.mhkapr.webtaxi.DTOs.PaymentResponse;
import ir.mhkapr.webtaxi.DTOs.StatusResponse;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.exception.UserBusynessException;
import ir.mhkapr.webtaxi.service.OrderService;
import ir.mhkapr.webtaxi.service.PaymentService;
import ir.mhkapr.webtaxi.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final OrderService orderService;
    private final StatusService statusService;
    private final PaymentService paymentService;
    @PostMapping("/register-order")
    public ResponseEntity<OrderResponse> registerOrder(@RequestBody OrderRequest request)
            throws UserBusynessException, DriverNotFoundInRangeException, JsonProcessingException {
        return ResponseEntity.ok(orderService.registerOrder(request));
    }
    @GetMapping("/get-status")
    public ResponseEntity<StatusResponse> getStatus(){
        return ResponseEntity.ok(statusService.getStatus());
    }
    @GetMapping("/payment")
    public ResponseEntity<PaymentResponse> payment() throws UserBusynessException {
        return ResponseEntity.ok(paymentService.pay());
    }
}
