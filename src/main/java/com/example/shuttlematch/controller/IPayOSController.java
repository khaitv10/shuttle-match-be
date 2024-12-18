package com.example.shuttlematch.controller;


import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionPaymentRequest;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.PaymentResponse;
import com.example.shuttlematch.payload.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.PaymentLinkData;


import java.security.Principal;
import java.util.List;

@Tag(name = "PayOS controller")
@RequestMapping("/payos")
@Validated
@Valid
public interface IPayOSController {
    @Operation(
            summary = "Get payment link information"
    )
    @GetMapping("/v1/getPaymentLinkInformation")
    ResponseEntity<ApiResponse<PaymentLinkData>> getPaymentLinkInformation(@RequestParam long transactionId);

    @Operation(
            summary = "Subscription payment"
    )
    @PostMapping("/v1/subscriptionPayment")
    ResponseEntity<ApiResponse<PaymentResponse>> subscriptionPayment(@Valid @RequestBody SubscriptionPaymentRequest request, Principal principal);

//    @Operation(
//            summary = "Update transaction when payment success"
//    )
//    @PutMapping("/v1/updateTransaction")
//    ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(@RequestParam long transactionId);

    @Operation(
            summary = "Get all success payment of user"
    )
    @GetMapping("/v1/getAllPaymentUser")
    ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllPaymentUser(@Valid Principal principal);

    @Operation(
            summary = "Get all success payment by user id"
    )
    @GetMapping("/v1/getAllPaymentUserId")
    ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllPaymentUserId(@RequestParam long userId);
}
