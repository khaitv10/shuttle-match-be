package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Match;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.MatchRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.service.IMatchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MatchService implements IMatchService {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    @Override
    public ApiResponse<List<UserSummaryResponse>> getAllMatched(String email) {
        try {
            User currUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            List<Match> listMatched = matchRepository.findAllByUser1OrUser2(currUser, currUser);
            if (listMatched.isEmpty()) {
                throw new BusinessException(ResponseCode.USER_NOT_HAVE_ANY_MATCH);
            }

            List<User> listUserMatched = listMatched.stream()
                    .map(match -> match.getUser1().equals(currUser) ? match.getUser2() : match.getUser1())
                    .distinct() // Tránh trùng lặp người dùng
                    .collect(Collectors.toList());

            List<UserSummaryResponse> listResponse = listUserMatched.stream()
                    .map(UserSummaryResponse::new)
                    .collect(Collectors.toList());

            return new ApiResponse<>(ResponseCode.SUCCESS, listResponse);

        }  catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}
