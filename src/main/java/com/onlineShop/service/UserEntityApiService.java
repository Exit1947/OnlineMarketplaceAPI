package com.onlineShop.service;

import com.onlineShop.dto.media.MediaResponse;
import com.onlineShop.dto.user.userEntity.admin.AdminRequest;
import com.onlineShop.dto.user.userEntity.admin.AdminResponse;
import com.onlineShop.dto.user.userEntity.endUser.EndUserRequest;
import com.onlineShop.dto.user.userEntity.endUser.EndUserResponse;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorRequest;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorResponse;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepRequest;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserEntityApiService {

    ResponseEntity<String> createAdmin(AdminRequest adminRequest);

    ResponseEntity<String> createModerator(ModeratorRequest moderatorRequest);

    ResponseEntity<String> createSalesRep(SalesRepRequest salesRepRequest);

    ResponseEntity<String> createEndUser(EndUserRequest endUserRequest);

    ResponseEntity<AdminResponse> getAdminById(String id);

    ResponseEntity<AdminResponse> getAdminByEmail(String email);

    ResponseEntity<AdminResponse> getAdminByLogin(String login);

    ResponseEntity<AdminResponse> getAdminByPhoneNumber(String phoneNumber);

    ResponseEntity<ModeratorResponse> getModeratorById(String id);

    ResponseEntity<ModeratorResponse> getModeratorByEmail(String email);

    ResponseEntity<ModeratorResponse> getModeratorByLogin(String login);

    ResponseEntity<ModeratorResponse> getModeratorByPhoneNumber(String phoneNumber);

    ResponseEntity<SalesRepResponse> getSalesRepById(String id);

    ResponseEntity<SalesRepResponse> getSalesRepByEmail(String email);

    ResponseEntity<SalesRepResponse> getSalesRepByLogin(String login);

    ResponseEntity<SalesRepResponse> getSalesRepByPhoneNumber(String phoneNumber);

    ResponseEntity<EndUserResponse> getEndUserById(String id);

    ResponseEntity<EndUserResponse> getEndUserByEmail(String email);

    ResponseEntity<EndUserResponse> getEndUserByLogin(String login);

    ResponseEntity<EndUserResponse> getEndUserByPhoneNumber(String email);

    ResponseEntity<HttpStatus> updateAdmin(AdminRequest adminRequest);

    ResponseEntity<HttpStatus> updateModerator(ModeratorRequest moderatorRequest);

    ResponseEntity<HttpStatus> updateSalesRep(SalesRepRequest salesRepRequest);

    ResponseEntity<HttpStatus> updateEndUser(EndUserRequest endUserRequest);

    ResponseEntity<HttpStatus> deleteAdmin(String id, String requestAdminId);

    ResponseEntity<HttpStatus> deleteModerator(String id);

    ResponseEntity<HttpStatus> deleteSalesRep(String id);

    ResponseEntity<HttpStatus> deleteEndUser(String id);

    public ResponseEntity<HttpStatus> saveAvatar(String entityId, MultipartFile mediaFile);

    public ResponseEntity<MediaResponse> getAvatarByMediaName(String mediaName);

    public ResponseEntity<MediaResponse> getAvatarForEntity(String userId);

    public ResponseEntity<HttpStatus> deleteAvatar(String userId);

}
