package com.onlineShop.controllers;

import com.onlineShop.dto.media.MediaResponse;
import com.onlineShop.dto.user.userEntity.admin.AdminRequest;
import com.onlineShop.dto.user.userEntity.admin.AdminResponse;
import com.onlineShop.dto.user.userEntity.endUser.EndUserRequest;
import com.onlineShop.dto.user.userEntity.endUser.EndUserResponse;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorRequest;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorResponse;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepRequest;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepResponse;
import com.onlineShop.security.JwtDecoder;
import com.onlineShop.security.JwtToPrincipalConverter;
import com.onlineShop.service.UserEntityApiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserEntityApiService userEntityApiService;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public UserController(UserEntityApiService userEntityApiService, JwtDecoder jwtDecoder) {
        this.userEntityApiService = userEntityApiService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/admin")
    public ResponseEntity<String> saveAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return userEntityApiService.createAdmin(adminRequest);
    }

    @PostMapping("/moderator")
    public ResponseEntity<String> saveModerator(@RequestBody @Valid ModeratorRequest moderatorRequest) {
        return userEntityApiService.createModerator(moderatorRequest);
    }

    @PostMapping("/sales-rep")
    public ResponseEntity<String> saveSalesRep(@RequestBody @Valid SalesRepRequest salesRepRequest) {
        return userEntityApiService.createSalesRep(salesRepRequest);
    }

    @PostMapping("/end-user")
    public ResponseEntity<String> saveEndUser(@RequestBody @Valid EndUserRequest endUserRequest) {
        return userEntityApiService.createEndUser(endUserRequest);
    }

    @GetMapping("/admin/me")
    public ResponseEntity<AdminResponse> getSelfInfoAdminById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestAdminId;
        try {
            requestAdminId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.getAdminById(requestAdminId);
    }

    @GetMapping("/admin/id={id}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.getAdminById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admin/email={email}")
    public ResponseEntity<AdminResponse> getAdminByEmail(@PathVariable("email") String email) {
        if(email!=null && !email.isEmpty()) {
            return userEntityApiService.getAdminByEmail(email);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admin/login={login}")
    public ResponseEntity<AdminResponse> getAdminByLogin(@PathVariable("login") String login) {
        if(login!=null && !login.isEmpty()) {
            return userEntityApiService.getAdminByLogin(login);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/admin/phoneNumber={phoneNumber}")
    public ResponseEntity<AdminResponse> getAdminByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        if(phoneNumber!=null && !phoneNumber.isEmpty()) {
            return userEntityApiService.getAdminByPhoneNumber(phoneNumber);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/moderator/me")
    public ResponseEntity<ModeratorResponse> getSelfInfoModeratorById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestModeratorId;
        try {
            requestModeratorId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.getModeratorById(requestModeratorId);
    }

    @GetMapping("/moderator/id={id}")
    public ResponseEntity<ModeratorResponse> getModeratorById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.getModeratorById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/moderator/email={email}")
    public ResponseEntity<ModeratorResponse> getModeratorByEmail(@PathVariable("email") String email) {
        if(email!=null && !email.isEmpty()) {
            return userEntityApiService.getModeratorByEmail(email);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/moderator/login={login}")
    public ResponseEntity<ModeratorResponse> getModeratorByLogin(@PathVariable("login") String login) {
        if(login!=null && !login.isEmpty()) {
            return userEntityApiService.getModeratorByLogin(login);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/moderator/phoneNumber={phoneNumber}")
    public ResponseEntity<ModeratorResponse> getModeratorByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        if(phoneNumber!=null && !phoneNumber.isEmpty()) {
            return userEntityApiService.getModeratorByPhoneNumber(phoneNumber);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sales-rep/me")
    public ResponseEntity<SalesRepResponse> getSelfInfoSalesRepById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestSalesRepId;
        try {
            requestSalesRepId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.getSalesRepById(requestSalesRepId);
    }

    @GetMapping("/sales-rep/id={id}")
    public ResponseEntity<SalesRepResponse> getSalesRepById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.getSalesRepById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sales-rep/email={email}")
    public ResponseEntity<SalesRepResponse> getSalesRepByEmail(@PathVariable("email") String email) {
        if(email!=null && !email.isEmpty()) {
            return userEntityApiService.getSalesRepByEmail(email);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sales-rep/login={login}")
    public ResponseEntity<SalesRepResponse> getSalesRepByLogin(@PathVariable("login") String login) {
        if(login!=null && !login.isEmpty()) {
            return userEntityApiService.getSalesRepByLogin(login);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sales-rep/phoneNumber={phoneNumber}")
    public ResponseEntity<SalesRepResponse> getSalesRepByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        if(phoneNumber!=null && !phoneNumber.isEmpty()) {
            return userEntityApiService.getSalesRepByPhoneNumber(phoneNumber);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/end-user/me")
    public ResponseEntity<EndUserResponse> getSelfInfoEndUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.getEndUserById(requestEndUserId);
    }

    @GetMapping("/end-user/id={id}")
    public ResponseEntity<EndUserResponse> getEndUserById(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.getEndUserById(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/end-user/email={email}")
    public ResponseEntity<EndUserResponse> getEndUserByEmail(@PathVariable("email") String email) {
        if(email!=null && !email.isEmpty()) {
            return userEntityApiService.getEndUserByEmail(email);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/end-user/login={login}")
    public ResponseEntity<EndUserResponse> getEndUserByLogin(@PathVariable("login") String login) {
        if(login!=null && !login.isEmpty()) {
            return userEntityApiService.getEndUserByLogin(login);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/end-user/phoneNumber={phoneNumber}")
    public ResponseEntity<EndUserResponse> getEndUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        if(phoneNumber!=null && !phoneNumber.isEmpty()) {
            return userEntityApiService.getEndUserByPhoneNumber(phoneNumber);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/admin")
    public ResponseEntity<HttpStatus> updateAdmin(@RequestBody @Valid AdminRequest adminRequest) {
        return userEntityApiService.updateAdmin(adminRequest);
    }

    @PutMapping("/moderator")
    public ResponseEntity<HttpStatus> updateModerator(@RequestBody @Valid ModeratorRequest moderatorRequest) {
        return userEntityApiService.updateModerator(moderatorRequest);
    }

    @PutMapping("/sales-rep")
    public ResponseEntity<HttpStatus> updateSalesRep(@RequestBody @Valid SalesRepRequest salesRepRequest) {
        return userEntityApiService.updateSalesRep(salesRepRequest);
    }

    @PutMapping("/end-user/me")
    public ResponseEntity<HttpStatus> updateEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody @Valid EndUserRequest endUserRequest) {
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(requestEndUserId.equals(endUserRequest.getId())) {
            return userEntityApiService.updateEndUser(endUserRequest);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/end-user")
    public ResponseEntity<HttpStatus> updateEndUser(@RequestBody @Valid EndUserRequest endUserRequest) {
        return userEntityApiService.updateEndUser(endUserRequest);
    }

    @DeleteMapping("/admin/id={id}")
    public ResponseEntity<HttpStatus> deleteAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            String requestAdminId;
            try {
                requestAdminId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
            }
            catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return userEntityApiService.deleteAdmin(id, requestAdminId);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/moderator/id={id}")
    public ResponseEntity<HttpStatus> deleteModerator(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.deleteModerator(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/sales-rep/id={id}")
    public ResponseEntity<HttpStatus> deleteSalesRep(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.deleteSalesRep(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/end-user/me")
    public ResponseEntity<HttpStatus> deleteSelfEndUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestEndUserId;
        try {
            requestEndUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.deleteEndUser(requestEndUserId);
    }

    @DeleteMapping("/end-user/id={id}")
    public ResponseEntity<HttpStatus> deleteEndUser(@PathVariable("id") String id) {
        if(id!=null && !id.isEmpty()) {
            return userEntityApiService.deleteEndUser(id);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/avatar/user-id={userId}")
    public ResponseEntity<HttpStatus> saveAvatar(@PathVariable("userId") String userId,  @RequestPart("avatar") MultipartFile avatar) {
        if(userId!=null && !userId.isEmpty()) {
            return userEntityApiService.saveAvatar(userId, avatar);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/avatar/me")
    public ResponseEntity<HttpStatus> saveSelfAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,  @RequestPart("avatar") MultipartFile avatar) {
        String requestUserId;
        try {
            requestUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.saveAvatar(requestUserId, avatar);
    }

    @GetMapping("/avatar/me")
    public ResponseEntity<MediaResponse> getSelfAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestUserId;
        try {
            requestUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.getAvatarForEntity(requestUserId);
    }

    @GetMapping("/avatar/user-id={userId}")
    public ResponseEntity<MediaResponse> getAvatar(@PathVariable("userId") String userId) {
        return userEntityApiService.getAvatarForEntity(userId);
    }

    @DeleteMapping("/avatar/me")
    public ResponseEntity<HttpStatus> deleteSelfAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String requestUserId;
        try {
            requestUserId = JwtToPrincipalConverter.convert(jwtDecoder.decode(authorizationHeader.substring(7))).getUserId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return userEntityApiService.deleteAvatar(requestUserId);
    }

    @DeleteMapping("/avatar/user-id={userId}")
    public ResponseEntity<HttpStatus> deleteAvatar(@PathVariable("userId") String userId) {
        if(userId!=null && !userId.isEmpty()) {
            return userEntityApiService.deleteAvatar(userId);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
