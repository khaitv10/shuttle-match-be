package com.example.shuttlematch.service.impl;

import com.example.shuttlematch.entity.Match;
import com.example.shuttlematch.entity.Report;
import com.example.shuttlematch.entity.User;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.ReportRequest;
import com.example.shuttlematch.repository.MatchRepository;
import com.example.shuttlematch.repository.ReportRepository;
import com.example.shuttlematch.repository.SwipeRepository;
import com.example.shuttlematch.repository.UserRepository;
import com.example.shuttlematch.service.IReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportService implements IReportService {

    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final MatchRepository matchRepository;
    private final ReportRepository reportRepository;

    @Override
    public ApiResponse<String> report(ReportRequest request, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            User toUser = userRepository.findById(request.getToUserId()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            Match match1 = matchRepository.findByUser1IdAndUser2IdAndActive(user.getId(), toUser.getId(), true);
            Match match2 = matchRepository.findByUser2IdAndUser1IdAndActive(user.getId(), toUser.getId(), true);

            if (match1 == null && match2 == null) {
                throw new BusinessException(ResponseCode.USER_NOT_MATCH);
            }

            Report report = new Report()
                    .setReportTime(LocalDateTime.now())
                    .setReportedBy(user)
                    .setReportedUser(toUser)
                    .setReason(request.getReason());
            reportRepository.save(report);

            if (match1!=null) {
                match1.setActive(false);
                matchRepository.save(match1);
            }
            if (match2!=null) {
                match2.setActive(false);
                matchRepository.save(match2);
            }

            toUser.setReportCount(toUser.getReportCount()+1);
            if (toUser.getReportCount()>=3) toUser.setStatus(Status.BANNED);
            userRepository.save(toUser);

            return new ApiResponse<>(ResponseCode.SUCCESS, request.getReason().toString());
        } catch (BusinessException e) {
            log.error("BusinessException: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage(), e);
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}
