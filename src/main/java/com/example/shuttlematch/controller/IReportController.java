package com.example.shuttlematch.controller;

import com.example.shuttlematch.enums.Reason;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.ReportRequest;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.ReasonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Tag(name = "Report controller")
@RequestMapping("/report")
@Validated
@Valid
public interface IReportController {
    @Operation(
            summary = "Report"
    )
    @PostMapping("/v1/report")
    ResponseEntity<ApiResponse<String>> report(@Valid @RequestBody ReportRequest request, Principal principal);

    @Operation(
            summary = "List reason"
    )
    @GetMapping("/v1/listReason")
    List<ReasonResponse> listReason();
}
