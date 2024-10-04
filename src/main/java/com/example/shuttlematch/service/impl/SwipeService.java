package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.repository.SwipeRepository;
import com.example.shuttlematch.repository.UserPhotoRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.security.jwt.JwtUtilities;
import com.example.shuttlematch.service.ISwipeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SwipeService implements ISwipeService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final UserPhotoRepository userPhotoRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtilities jwtUtilities;

    @Override
    public ApiResponse<String> swipe(SwipeRequest request, String email) {
        try {
            User fromUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            User toUser = userRepository.findById(request.getToUserId()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            Swipe swipe = new Swipe()
                    .setFromUser(fromUser)
                    .setToUser(toUser)
                    .setSwipeType(request.getSwipeType())
                    .setSwipeTime(LocalDateTime.now());

            swipeRepository.save(swipe);

            return new ApiResponse<>(ResponseCode.SUCCESS, request.getSwipeType().toString());
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}
