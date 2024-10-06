package com.example.shuttlematch.controller.impl;

import com.example.shuttlematch.controller.IReportController;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.ReportRequest;
import com.example.shuttlematch.service.IReportService;
import com.example.shuttlematch.service.ISwipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@Valid
@RequiredArgsConstructor
public class ReportController implements IReportController {

    private final IReportService reportService;
    @Override
    public ResponseEntity<ApiResponse<String>> report(ReportRequest request, Principal principal) {
        log.info("Has a request to report: {}", request.toString());
        ApiResponse<String> reportResponse = reportService.report(request, principal.getName());
        return new ResponseEntity<>(reportResponse, HttpStatus.OK);
    }
}
