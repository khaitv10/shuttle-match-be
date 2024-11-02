package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Transaction;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Role;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.AllAccountResponse;
import com.example.shuttlematch.payload.response.AllPaymentResponse;
import com.example.shuttlematch.payload.response.TransactionResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.TransactionRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.service.IAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public ApiResponse<AllAccountResponse> getAllAccount(String email, int page, int size) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            User currentUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            //log.info("role: {}", currentUser.getRole());
            if(!currentUser.getRole().contains(Role.ADMIN)) throw new BusinessException(ResponseCode.USER_DO_NOT_PERMISSION);
            String currentEmail = currentUser.getEmail();
            Page<User> userList = userRepository.findAll(pageRequest);

            List<UserSummaryResponse> filteredUsers = userList.stream()
                    .filter(user -> !user.getEmail().equals(currentEmail) && !user.getFullName().equals("Admin"))
                    .map(UserSummaryResponse::new)
                    .toList();

            int total = filteredUsers.size();

            AllAccountResponse allAccountResponse = new AllAccountResponse(total, filteredUsers);

            return new ApiResponse<>(ResponseCode.SUCCESS, allAccountResponse);
        } catch (Exception e) {
            //log.error("An error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.USER_DO_NOT_PERMISSION);
        }

    }

    @Override
    public ApiResponse<AllPaymentResponse> getAllPayment(String email, int page, int size) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            User currentUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            if(!currentUser.getRole().contains(Role.ADMIN)) throw new BusinessException(ResponseCode.USER_DO_NOT_PERMISSION);

            Page<Transaction> transactionList = transactionRepository.findAll(pageRequest);
            List<TransactionResponse> responseList = transactionList.stream()
                    .map(TransactionResponse::new)
                    .toList();

            long revenue = transactionList.stream()
                    .filter(transaction -> transaction.getStatus().equals(Status.COMPLETED))
                    .mapToLong(Transaction::getAmount)
                    .sum();

            long totalCompleted = transactionList.stream()
                    .filter(transaction -> transaction.getStatus().equals(Status.COMPLETED))
                    .count();

            AllPaymentResponse allPaymentResponse = new AllPaymentResponse(totalCompleted, revenue, responseList);

            return new ApiResponse<>(ResponseCode.SUCCESS, allPaymentResponse);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.USER_DO_NOT_PERMISSION);
        }
    }
}
