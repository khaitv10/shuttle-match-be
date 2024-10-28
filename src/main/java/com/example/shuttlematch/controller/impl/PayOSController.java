package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IPayOSController;
import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SubscriptionPaymentRequest;
import com.example.shuttlematch.payload.response.PaymentResponse;
import com.example.shuttlematch.payload.response.TransactionResponse;
import com.example.shuttlematch.service.impl.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentLinkData;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class PayOSController implements IPayOSController {

    private final TransactionService transactionService;


    @Value("${PAYOS_CLIENT_ID}")
    private String payosClientId;

    @Value("${PAYOS_API_KEY}")
    private String payosApiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String payosChecksumKey;

    @Override
    public ResponseEntity<ApiResponse<PaymentLinkData>> getPaymentLinkInformation(long transactionId) {
        PayOS payOS = new PayOS(payosClientId, payosApiKey, payosChecksumKey);
        try {
            PaymentLinkData paymentLinkData = payOS.getPaymentLinkInformation(transactionId);

            ApiResponse<PaymentLinkData> response = new ApiResponse<>(ResponseCode.SUCCESS, paymentLinkData);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Payment get link information failed: ", e);

            ApiResponse errorResponse = new ApiResponse<PaymentLinkData>().error("Could not get payment link information");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<ApiResponse<PaymentResponse>> subscriptionPayment(SubscriptionPaymentRequest request, Principal principal) {
        long transactionId = transactionService.createUserSubscriptionTransaction(principal.getName(), request.getSubscriptionId());
        String paymentUrl = transactionService.getPaymentUrl(transactionId, request.getRedirectUrl());

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentId(transactionId);
        paymentResponse.setPaymentUrl(paymentUrl);

        ApiResponse<PaymentResponse> response = new ApiResponse<>(ResponseCode.SUCCESS, paymentResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<ApiResponse<TransactionResponse>> updateTransaction(long transactionId) {
//        log.info("Has a request to update transaction with id: {}", transactionId);
//        ApiResponse<TransactionResponse> response = transactionService.updateTransaction(transactionId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    @Override
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getAllPaymentUser(Principal principal) {
        log.info("Has a request to get all payment with user: {}", principal.getName());
        ApiResponse<List<TransactionResponse>> response = transactionService.getAllPaymentUser(principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
