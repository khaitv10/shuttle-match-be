package com.example.shuttlematch.service;

import com.example.shuttlematch.enums.Reason;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.ReportRequest;
import com.example.shuttlematch.payload.request.SwipeRequest;

public interface IReportService {
    ApiResponse<String> report(ReportRequest request, String email);
}
