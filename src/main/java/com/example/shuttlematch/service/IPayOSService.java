package com.example.shuttlematch.service;

import com.example.shuttlematch.entity.ChatMessage;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.PayOSRequest;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;
import vn.payos.type.ItemData;

public interface IPayOSService {
    CheckoutResponseData createPaymentUrl(PayOSRequest payOSRequest);
}


