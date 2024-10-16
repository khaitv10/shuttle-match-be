package com.example.shuttlematch.service.impl;


import com.example.shuttlematch.payload.request.PayOSRequest;
import com.example.shuttlematch.service.IPayOSService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PayOSService implements IPayOSService {


    @Value("${PAYOS_CLIENT_ID}")
    private String payosClientId;

    @Value("${PAYOS_API_KEY}")
    private String payosApiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String payosChecksumKey;

    @Override
    public CheckoutResponseData createPaymentUrl(PayOSRequest payOSRequest) {
        PayOS payOS = new PayOS(payosClientId, payosApiKey, payosChecksumKey);

        ItemData item = ItemData.builder()
                .name(payOSRequest.getSubName())
                .quantity(1)
                .price((int) payOSRequest.getAmount())
                .build();
        List<ItemData> items = Arrays.asList(item);

        PaymentData paymentData = PaymentData.builder()
                .orderCode((long) payOSRequest.getOrderId())
                .amount((int) payOSRequest.getAmount())
                .description("Subscription Payment")
                .items(items)
                .cancelUrl(payOSRequest.getCancelUrl())
                .returnUrl(payOSRequest.getRedirectUrl())
                .build();

        try {
            return payOS.createPaymentLink(paymentData);
        } catch (Exception e) {
            log.error("Payment creation failed: ", e);
            throw new RuntimeException("Could not create payment link", e);
        }
    }
}
