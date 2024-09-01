package ir.mhkapr.webtaxi.controller;

import ir.mhkapr.webtaxi.DTOs.OrderRequest;
import ir.mhkapr.webtaxi.DTOs.OrderResponse;
import ir.mhkapr.webtaxi.DTOs.StatusResponse;
import ir.mhkapr.webtaxi.exception.DriverNotFoundInRangeException;
import ir.mhkapr.webtaxi.exception.UserBusynessException;
import ir.mhkapr.webtaxi.service.OrderService;
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
    @PostMapping("/register-order")
    public ResponseEntity<OrderResponse> registerOrder(@RequestBody OrderRequest request)
            throws UserBusynessException, DriverNotFoundInRangeException {
        return ResponseEntity.ok(orderService.registerOrder(request));
    }
    @GetMapping("/get-status")
    public ResponseEntity<StatusResponse> getStatus(){
        return ResponseEntity.ok(statusService.getStatus());
    }
    @GetMapping("/payment")
    public ResponseEntity<OrderResponse> payment(){

    }
}
