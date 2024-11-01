package com.example.shuttlematch.service.impl;


import com.example.shuttlematch.entity.*;
import com.example.shuttlematch.enums.ResponseCode;
import com.example.shuttlematch.enums.Role;
import com.example.shuttlematch.enums.Status;
import com.example.shuttlematch.enums.SwipeType;
import com.example.shuttlematch.exception.BusinessException;
import com.example.shuttlematch.payload.common.ApiResponse;
import com.example.shuttlematch.payload.request.*;
import com.example.shuttlematch.payload.response.TokenResponse;
import com.example.shuttlematch.payload.response.UserResponse;
import com.example.shuttlematch.payload.response.UserSummaryResponse;
import com.example.shuttlematch.repository.*;
import com.example.shuttlematch.security.jwt.JwtUtilities;
import com.example.shuttlematch.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final SwipeRepository swipeRepository;
    private final UserPhotoRepository userPhotoRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;
    //private final IServiceMail serviceMail;

//    @Value("${spring.mail.username}")
//    private String mailFrom;


//    @Override
//    public User saverUser(User user) {
//        return iUserRepository.save(user);
//    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponse>> register(UserRegisterRequest request) {
        try {
            Set<String> photos = request.getPhoto();
            if (photos.isEmpty() || photos.size() > 6 || photos.size() < 2) {
                throw new BusinessException(ResponseCode.USER_PHOTO_COUNT_INVALID);
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BusinessException(ResponseCode.USER_EMAIL_EXISTED);
            }
            if (userRepository.existsByPhone(request.getPhone())) {
                throw new BusinessException(ResponseCode.USER_PHONE_EXISTED);
            }
            if (Period.between(request.getDob(), LocalDate.now()).getYears() < 16) {
                throw new BusinessException(ResponseCode.AGE_NEED_OVER_16);
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhone(request.getPhone());
            user.setFullName(request.getName());
            user.setDob(request.getDob());
            user.setGender(request.getGender().toUpperCase());
            user.setOccupation(request.getOccupation());
            user.setLevel(request.getLevel());
            user.setDescription(request.getDescription());
            user.setLocation(request.getLocation());
            user.setAvailableTime(request.getAvailableTime());
            user.setDiamondMember(false);
            user.setReportCount(0);
            user.setStatus(Status.ACTIVE);
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(Set.of(Role.USER));
            user = userRepository.save(user);

            for (String photoUrl : photos) {
                UserPhoto userPhoto = new UserPhoto();
                userPhoto.setPhotoUrl(photoUrl);
                userPhoto.setUser(user);
                userPhotoRepository.save(userPhoto);
            }

            Subscription subscription = subscriptionRepository.findById(1);
            UserSubscription userSubscription = new UserSubscription()
                    .setUser(user)
                    .setSubscription(subscription)
                    .setActive(true);

            userSubscriptionRepository.save(userSubscription);

            // Fetch photos of new user
            Set<UserPhoto> listPts = userPhotoRepository.findByUserId(user.getId());
            System.out.println("Photos for user: " + listPts.size());
            for (UserPhoto photo : listPts) {
                System.out.println("Photo URL: " + photo.getPhotoUrl());
            }

            return new ResponseEntity<>(new ApiResponse<>(ResponseCode.SUCCESS, new UserResponse(user, listPts)), HttpStatus.OK);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.USER_REGISTER_FAILED);
        }
    }

//    @Override
//    public String authenticate(LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                loginDto.getEmail(),
//                loginDto.getPassword()
//            )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        List<String> rolesNames = new ArrayList<>();
//        user.getRoles().forEach(r -> rolesNames.add(r.name()));
//        String token = jwtUtilities.generateToken(user.getUsername(), rolesNames);
//        return token;
//    }


    @Override
    public ApiResponse<TokenResponse> login(LoginRequest request) {
        try {
            log.info("Attempting login for email: {}", request.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Login successful for email: {}", request.getEmail());
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND));
            if (user.getReportCount() >= 3 || user.getStatus().equals(Status.BANNED)) {
                throw new BusinessException(ResponseCode.USER_BANNED_AND_INACTIVE);
            }
            List<String> rolesNames = new ArrayList<>();
            user.getRole().forEach(r -> rolesNames.add(r.name()));
            String accessToken = jwtUtilities.generateToken(user.getUsername(), rolesNames);
            return new ApiResponse<>(ResponseCode.SUCCESS, new TokenResponse(user.getId(), user.getEmail(), accessToken));


        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for email: {}", request.getEmail());
            throw new BusinessException(ResponseCode.USER_EMAIL_OR_PASSWORD_INCORRECT);
        }
    }

    @Override
    public ApiResponse<TokenResponse> loginGoogle(LoginGoogleRequest request) {
        String token = request.getToken();
        try {
            URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonData = jsonParser.parseMap(response.toString());
                String email = (String) jsonData.get("email");
                String givenName = (String) jsonData.get("given_name");
                //String picture = (String) jsonData.get("picture");
                Optional<User> userOpt = userRepository.findByEmail(email);
                User user;
                if (userOpt.isPresent()) {
                    user = userOpt.get();
                    if (user.getReportCount() >= 3 || user.getStatus().equals(Status.BANNED)) {
                        throw new BusinessException(ResponseCode.USER_BANNED_AND_INACTIVE);
                    }
                } else {
                    user = new User();
                    user.setEmail(email);
                    user.setFullName(givenName);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setStatus(Status.INACTIVE);
                    user.setRole(Set.of(Role.USER));
                    user.setCreatedAt(LocalDateTime.now());
                    userRepository.save(user);

                    Subscription subscription = subscriptionRepository.findById(1);
                    UserSubscription userSubscription = new UserSubscription()
                            .setUser(user)
                            .setSubscription(subscription)
                            .setActive(true);

                    userSubscriptionRepository.save(userSubscription);
                }


                List<String> rolesNames = new ArrayList<>();
                user.getRole().forEach(r -> rolesNames.add(r.name()));
                String accessToken = jwtUtilities.generateToken(user.getUsername(), rolesNames);

                log.info("Google API Response: {}", response.toString());
                return new ApiResponse<>(ResponseCode.SUCCESS, new TokenResponse(user.getId(), user.getEmail(), accessToken));

            } else {
                throw new BusinessException(ResponseCode.INVALID_GOOGLE_TOKEN);
            }
        } catch (Exception e) {
            log.error("Error during Google login: ", e);
            throw new BusinessException(ResponseCode.LOGIN_GOOGLE_FAILED);
        }
    }

    public boolean checkPasswordChange(String requestOldPass, String userPass) {
        if (!passwordEncoder.matches(requestOldPass, userPass)) {
            throw new BusinessException(ResponseCode.PASSWORD_NOT_FOUND);
        }
        return true;
    }

    @Override
    public ApiResponse<String> changePassword(PasswordChangeRequest request, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> {
                        throw new BusinessException(ResponseCode.USER_NOT_FOUND);
                    }
            );
            if (checkPasswordChange(request.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
            }
            return new ApiResponse<>(ResponseCode.SUCCESS, "Password changed successfully");
        } catch (Exception e) {
            //e.printStackTrace();
            throw new BusinessException(ResponseCode.PASSWORD_NOT_FOUND);
        }
    }


    @Override
    public ApiResponse<UserResponse> getUserInfo(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> {
                        throw new BusinessException(ResponseCode.USER_NOT_FOUND);
                    }
            );
            if (user.getStatus().equals(Status.BANNED)) {
                throw new BusinessException(ResponseCode.USER_BANNED_AND_INACTIVE);
            }

            return new ApiResponse<>(ResponseCode.SUCCESS, new UserResponse(user));

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAILED);
        }
    }

    //
//    @Override
//    public ApiResponse<StatusResponse> checkOtpWhenRegister(CheckOtpWhenRegisterRequest request) {
//        try {
//            User user = iUserRepository.findByEmailAndDeleted(request.getEmail(),false).orElse(null);
//
//            if(Objects.isNull(user)){
//                throw new BusinessException(ResponseCode.USER_NOT_FOUND);
//            }
//            if(!request.getOtp().equals(user.getOtp())){
//                throw new BusinessException(ResponseCode.USER_CHECK_OTP_FAILED);
//            }
//
//            user.setInactive(false);
//            user.setOtp("");
//            iUserRepository.save(user);
//            return new ApiResponse<StatusResponse>(ResponseCode.SUCCESS, new StatusResponse(true));
//
//        } catch (BusinessException e) {
//            throw  e;
//        } catch (Exception e) {
//            log.error("Have error when check otp with email: {}", request.getEmail());
//            throw new BusinessException(ResponseCode.USER_CHECK_OTP_FAILED);
//        }
//    }
//
    private String getEmailRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "";
    }

    @Override
    public ApiResponse<UserResponse> updateUserInfo(UserUpdateRequest request) {
        try {
            User user = userRepository.findByEmail(getEmailRequest()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            if (user.getStatus().equals(Status.INACTIVE)) {
                if (StringUtils.isBlank(request.getPhone()) ||
                    request.getDob() == null ||
                    StringUtils.isBlank(request.getOccupation()) ||
                    StringUtils.isBlank(request.getGender()) ||
                    request.getLevel() == null ||
                    StringUtils.isBlank(request.getLocation()) ||
                    request.getPhoto() == null) {
                    throw new BusinessException(ResponseCode.REQUIRED_FIELDS_MISSING);
                }

                Set<String> photos = request.getPhoto();
                if (photos.isEmpty() || photos.size() > 6 || photos.size() < 2) {
                    throw new BusinessException(ResponseCode.USER_PHOTO_COUNT_INVALID);
                }

                if (Period.between(request.getDob(), LocalDate.now()).getYears() < 16) {
                    throw new BusinessException(ResponseCode.AGE_NEED_OVER_16);
                }

                user.setPhone(request.getPhone());
                user.setDob(request.getDob());
                user.setOccupation(request.getOccupation());
                user.setGender(request.getGender());
                user.setLevel(request.getLevel());
                user.setDescription(request.getDescription());
                user.setLocation(request.getLocation());
                user.setAvailableTime(request.getAvailableTime());
                for (String photoUrl : photos) {
                    UserPhoto userPhoto = new UserPhoto();
                    userPhoto.setPhotoUrl(photoUrl);
                    userPhoto.setUser(user);
                    userPhotoRepository.save(userPhoto);
                }
                user.setStatus(Status.ACTIVE);
            } else {
                if (StringUtils.isNoneBlank(request.getFullName())) {
                    user.setFullName(request.getFullName());
                }
                if (StringUtils.isNoneBlank(request.getPhone())) {
                    user.setPhone(request.getPhone());
                }
                if (request.getDob() != null) {
                    user.setDob(request.getDob());
                }
                if (request.getOccupation() != null) {
                    user.setOccupation(request.getOccupation());
                }
                if (request.getGender() != null) {
                    user.setGender(request.getGender());
                }
                if (request.getLevel() != null) {
                    user.setLevel(request.getLevel());
                }
                if (request.getDescription() != null) {
                    user.setDescription(request.getDescription());
                }
                if (request.getLocation() != null) {
                    user.setLocation(request.getLocation());
                }
                if (request.getAvailableTime() != null) {
                    user.setAvailableTime(request.getAvailableTime());
                }
                user.setUpdatedAt(LocalDateTime.now());
            }

            userRepository.save(user);
            return new ApiResponse<>(ResponseCode.SUCCESS, new UserResponse(user));

        } catch (BusinessException e) {
            throw e;
        } catch (DateTimeException e) {
            throw new BusinessException(ResponseCode.DATETIME_INVALID);
        } catch (Exception e) {
            log.error("Have error : {}", e.getLocalizedMessage());
            throw new BusinessException(ResponseCode.FAILED);
        }
    }


    @Override
    public ApiResponse<List<UserSummaryResponse>> getAll(String email) {
        try {
            User currentUser = userRepository.findByEmail(getEmailRequest()).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );
            String currentEmail = currentUser.getEmail();

            List<Swipe> listSwiped = swipeRepository.findByFromUserId(currentUser.getId());
            Set<Long> likedUserIds = listSwiped.stream()
                    .map(swipe -> swipe.getToUser().getId())  // Assuming Swipe entity has a getToUser() method to get the liked user
                    .collect(Collectors.toSet());

            List<Swipe> listUserSwiped = swipeRepository.findByToUserIdAndSwipeType(currentUser.getId(), SwipeType.LIKE);
            Set<Long> UserlikedIds = listUserSwiped.stream()
                    .map(swipe -> swipe.getFromUser().getId())
                    .collect(Collectors.toSet());


            List<User> userList = userRepository.findAllByStatus(Status.ACTIVE);
            //random user
            Collections.shuffle(userList);

            List<UserSummaryResponse> filteredUsers = userList.stream()
                    .filter(user -> !user.getEmail().equals(currentEmail) && !user.getFullName().equals("Admin"))
                    .filter(user -> !likedUserIds.contains(user.getId()))
                    .filter(user -> !UserlikedIds.contains(user.getId()))
                    .map(UserSummaryResponse::new)
                    .toList();

            return new ApiResponse<>(ResponseCode.SUCCESS, filteredUsers);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAILED);
        }
    }

    @Override
    public ApiResponse<UserResponse> getInfo(long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new BusinessException(ResponseCode.USER_NOT_FOUND)
            );

            return new ApiResponse<>(ResponseCode.SUCCESS, new UserResponse(user));
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAILED);
        }
    }
}

