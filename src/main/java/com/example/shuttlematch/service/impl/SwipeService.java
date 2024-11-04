package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Match;
import com.example.shuttlematch.entity.Swipe;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.entity.request.RoomRequest;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.SwipeType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.SwipeRequest;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.MatchRepository;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SwipeService implements ISwipeService {

    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final MatchRepository matchRepository;
    private final ChatService chatService;
    @Override
    public ApiResponse<String> swipe(SwipeRequest request, String email) {
        try {
            User fromUser = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            User toUser = userRepository.findById(request.getToUserId()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            Swipe swipeCheck = swipeRepository.findByToUserIdAndFromUserIdAndSwipeTypeAndStatus(
                    fromUser.getId(), toUser.getId(), SwipeType.LIKE, null
            );

            if (request.getSwipeType().equals(SwipeType.LIKE)) {
                if (fromUser.getLikeRemaining() == 0) {
                    return new ApiResponse<>(ResponseCode.FAILED, ResponseCode.RUN_OUT_OF_LIKE.getMessage());
                } else if (swipeCheck != null) {
                    Match match = new Match()
                            .setUser1(fromUser)
                            .setUser2(toUser)
                            .setMatchTime(LocalDateTime.now())
                            .setActive(true);

                    swipeCheck.setStatus(Status.MATCHED);
                    fromUser.setLikeRemaining(fromUser.getLikeRemaining() - 1);
                    swipeRepository.save(swipeCheck);
                    userRepository.save(fromUser);
                    matchRepository.save(match);

                    // Tạo phòng chat khi match
                    RoomRequest roomRequest = new RoomRequest();
                    roomRequest.setMembers(Arrays.asList(fromUser.getId(), toUser.getId()));
                    chatService.createNewRoom(roomRequest);

                    return new ApiResponse<>(ResponseCode.SUCCESS, request.getSwipeType().toString());
                } else {
                    fromUser.setLikeRemaining(fromUser.getLikeRemaining() - 1);
                    userRepository.save(fromUser);
                }
            }

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

    @Override
    public ApiResponse<List<UserSummaryResponse>> getAllLike(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            List<Swipe> swipes = swipeRepository.findByToUserIdAndSwipeTypeAndStatus(user.getId(), SwipeType.LIKE, null);

            if (swipes.isEmpty()) {
                return new ApiResponse<>(ResponseCode.SUCCESS, Collections.emptyList());
            }

            List<UserSummaryResponse> listUserLikeYou = swipes.stream()
                    .map(swipe -> new UserSummaryResponse(swipe.getFromUser())) // assuming fromUser is User
                    .toList();

            return new ApiResponse<>(ResponseCode.SUCCESS, listUserLikeYou);
        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}
