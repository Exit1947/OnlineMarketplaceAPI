package com.onlineShop.service.serviceImpl;

import com.onlineShop.config.preload.RolePrivilegePreload;
import com.onlineShop.converter.users.UserEntityConverter;
import com.onlineShop.dto.media.MediaResponse;
import com.onlineShop.dto.user.userEntity.admin.AdminRequest;
import com.onlineShop.dto.user.userEntity.admin.AdminResponse;
import com.onlineShop.dto.user.userEntity.endUser.EndUserRequest;
import com.onlineShop.dto.user.userEntity.endUser.EndUserResponse;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorRequest;
import com.onlineShop.dto.user.userEntity.moderator.ModeratorResponse;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepRequest;
import com.onlineShop.dto.user.userEntity.salesRep.SalesRepResponse;
import com.onlineShop.dto.user.userEntity.staff.StaffRequest;
import com.onlineShop.dto.user.userEntity.userEntity.UserEntityRequest;
import com.onlineShop.models.Shop.Company.Company;
import com.onlineShop.models.Shop.Shop;
import com.onlineShop.models.Users.EndUser;
import com.onlineShop.models.Users.RolePrivilege.Privilege;
import com.onlineShop.models.Users.RolePrivilege.Role;
import com.onlineShop.models.Users.RolePrivilege.UserEntityPrivilege;
import com.onlineShop.models.Users.Staff.*;
import com.onlineShop.models.Users.UserEntity;
import com.onlineShop.repository.*;
import com.onlineShop.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserEntityApiServiceImpl implements UserEntityApiService {

    private final UserEntityService userEntityService;
    private final RolePrivilegePreload rolePrivilegePreload;
    private final ShopService shopService;
    private final CompanyService companyService;
    private final FeedbackService feedbackService;
    private final CartService cartService;
    private final ShoppingOrderService shoppingOrderService;
    private final AvatarUserEntityApiServiceImpl avatarUserEntityApiServiceImpl;
    private final AvatarUserEntityServiceImpl avatarUserEntityServiceImpl;
    private final StaffRepository staffRepository;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;
    private final SalesRepRepository salesRepRepository;
    private final EndUserRepository endUserRepository;
    private final StaffListRepository staffListRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserEntityApiServiceImpl(UserEntityService userEntityService, RolePrivilegePreload rolePrivilegePreload, ShopService shopService,
                                    CompanyService companyService, FeedbackServiceImpl feedbackService, CartService cartService, ShoppingOrderService shoppingOrderService,
                                    AvatarUserEntityApiServiceImpl avatarUserEntityApiServiceImpl, AvatarUserEntityServiceImpl avatarUserEntityServiceImpl,
                                    StaffRepository staffRepository, AdminRepository adminRepository, ModeratorRepository moderatorRepository,
                                    SalesRepRepository salesRepRepository, EndUserRepository endUserRepository, StaffListRepository staffListRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userEntityService = userEntityService;
        this.rolePrivilegePreload = rolePrivilegePreload;
        this.shopService = shopService;
        this.companyService = companyService;
        this.feedbackService = feedbackService;
        this.cartService = cartService;
        this.shoppingOrderService = shoppingOrderService;
        this.avatarUserEntityApiServiceImpl = avatarUserEntityApiServiceImpl;
        this.avatarUserEntityServiceImpl = avatarUserEntityServiceImpl;
        this.staffRepository = staffRepository;
        this.adminRepository = adminRepository;
        this.moderatorRepository = moderatorRepository;
        this.salesRepRepository = salesRepRepository;
        this.endUserRepository = endUserRepository;
        this.staffListRepository = staffListRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createAdmin(final AdminRequest adminRequest) {
        if(!userEntityService.exist(adminRequest.getEmail(), adminRequest.getLogin(), adminRequest.getPhoneNumber())) {
            Admin admin = UserEntityConverter.toAdmin(adminRequest);

            ResponseEntity<String> res = checkAndSetRoleAndPrivileges(adminRequest, admin);
            if(res != null){
                return res;
            }

            List<StaffList> staffLists = toListOfStaffList(adminRequest, admin);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            admin.setShops(staffLists);

            admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));

            userEntityService.save(admin);

            return new ResponseEntity<>(admin.getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<String> createModerator(final ModeratorRequest moderatorRequest) {
        if(!userEntityService.exist(moderatorRequest.getEmail(), moderatorRequest.getLogin(), moderatorRequest.getPhoneNumber())){
            Moderator moderator = UserEntityConverter.toModerator(moderatorRequest);

            ResponseEntity<String> res = checkAndSetRoleAndPrivileges(moderatorRequest, moderator);
            if(res != null){
                return res;
            }

            List<StaffList> staffLists = toListOfStaffList(moderatorRequest, moderator);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            moderator.setShops(staffLists);

            Optional<Shop> shop = shopService.getById(moderatorRequest.getShopId());
            if(shop.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            moderator.setShop(shop.get());

            Optional<Admin> existingAdmin = adminRepository.findById(moderatorRequest.getAdminId());
            if(existingAdmin.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            moderator.setAdmin(existingAdmin.get());

            moderator.setPassword(passwordEncoder.encode(moderatorRequest.getPassword()));

            userEntityService.save(moderator);

            return new ResponseEntity<>(moderator.getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<String> createSalesRep(final SalesRepRequest salesRepRequest) {
        if(!userEntityService.exist(salesRepRequest.getEmail(), salesRepRequest.getLogin(), salesRepRequest.getPhoneNumber())){
            SalesRep salesRep = UserEntityConverter.toSalesRep(salesRepRequest);

            ResponseEntity<String> res = checkAndSetRoleAndPrivileges(salesRepRequest, salesRep);
            if(res != null){
                return res;
            }

            List<StaffList> staffLists = toListOfStaffList(salesRepRequest, salesRep);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            salesRep.setShops(staffLists);

            Optional<Shop> shop = shopService.getById(salesRepRequest.getShopId());
            if(shop.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            salesRep.setShop(shop.get());

            Optional<Admin> existingAdmin = adminRepository.findById(salesRepRequest.getAdminId());
            if(existingAdmin.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            salesRep.setAdmin(existingAdmin.get());

            salesRep.setPassword(passwordEncoder.encode(salesRepRequest.getPassword()));

            userEntityService.save(salesRep);

            return new ResponseEntity<>(salesRep.getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<String> createEndUser(final EndUserRequest endUserRequest) {
        if(!userEntityService.exist(endUserRequest.getEmail(), endUserRequest.getLogin(), endUserRequest.getPhoneNumber())){
            EndUser endUser = UserEntityConverter.toEndUser(endUserRequest);

            ResponseEntity<String> res = checkAndSetRoleAndPrivileges(endUserRequest, endUser);
            if(res != null){
                return res;
            }

            endUser.setPassword(passwordEncoder.encode(endUser.getPassword()));

            userEntityService.save(endUser);

            return new ResponseEntity<>(endUser.getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<AdminResponse> getAdminById(final String id) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        return getAdminResponseResponseEntity(existingAdmin);
    }

    @Override
    public ResponseEntity<AdminResponse> getAdminByEmail(final String email) {
        Optional<Admin> existingAdmin = adminRepository.findByEmail(email);
        return getAdminResponseResponseEntity(existingAdmin);
    }

    @Override
    public ResponseEntity<AdminResponse> getAdminByLogin(final String login) {
        Optional<Admin> existingAdmin = adminRepository.findByLogin(login);
        return getAdminResponseResponseEntity(existingAdmin);
    }

    @Override
    public ResponseEntity<AdminResponse> getAdminByPhoneNumber(final String phoneNumber) {
        Optional<Admin> existingAdmin = adminRepository.findByPhoneNumber(phoneNumber);
        return getAdminResponseResponseEntity(existingAdmin);
    }

    @Override
    public ResponseEntity<ModeratorResponse> getModeratorById(final String id) {
        Optional<Moderator> existingModerator = moderatorRepository.findById(id);
        return getModeratorResponseResponseEntity(existingModerator);
    }

    @Override
    public ResponseEntity<ModeratorResponse> getModeratorByEmail(final String email) {
        Optional<Moderator> existingUser = moderatorRepository.findByEmail(email);
        return getModeratorResponseResponseEntity(existingUser);
    }

    @Override
    public ResponseEntity<ModeratorResponse> getModeratorByLogin(final String login) {
        Optional<Moderator> existingModerator = moderatorRepository.findByLogin(login);
        return getModeratorResponseResponseEntity(existingModerator);
    }

    @Override
    public ResponseEntity<ModeratorResponse> getModeratorByPhoneNumber(final String phoneNumber) {
        Optional<Moderator> existingModerator = moderatorRepository.findByPhoneNumber(phoneNumber);
        return getModeratorResponseResponseEntity(existingModerator);
    }

    @Override
    public ResponseEntity<SalesRepResponse> getSalesRepById(final String id) {
        Optional<SalesRep> existingSalesRep = salesRepRepository.findById(id);
        return getSalesRepResponseResponseEntity(existingSalesRep);
    }

    @Override
    public ResponseEntity<SalesRepResponse> getSalesRepByEmail(final String email) {
        Optional<SalesRep> existingSalesRep = salesRepRepository.findByEmail(email);
        return getSalesRepResponseResponseEntity(existingSalesRep);
    }

    @Override
    public ResponseEntity<SalesRepResponse> getSalesRepByLogin(final String login) {
        Optional<SalesRep> existingSalesRep = salesRepRepository.findByLogin(login);
        return getSalesRepResponseResponseEntity(existingSalesRep);
    }

    @Override
    public ResponseEntity<SalesRepResponse> getSalesRepByPhoneNumber(final String phoneNumber) {
        Optional<SalesRep> existingSalesRep = salesRepRepository.findByPhoneNumber(phoneNumber);
        return getSalesRepResponseResponseEntity(existingSalesRep);
    }

    @Override
    public ResponseEntity<EndUserResponse> getEndUserById(final String id) {
        Optional<EndUser> existingEndUser = endUserRepository.findById(id);
        return getEndUserResponseResponseEntity(existingEndUser);
    }

    @Override
    public ResponseEntity<EndUserResponse> getEndUserByEmail(final String email) {
        Optional<EndUser> existingEndUser = endUserRepository.findByEmail(email);
        return getEndUserResponseResponseEntity(existingEndUser);
    }

    @Override
    public ResponseEntity<EndUserResponse> getEndUserByLogin(final String login) {
        Optional<EndUser> existingEndUser = endUserRepository.findByLogin(login);
        return getEndUserResponseResponseEntity(existingEndUser);
    }

    @Override
    public ResponseEntity<EndUserResponse> getEndUserByPhoneNumber(final String phoneNumber) {
        Optional<EndUser> existingEndUser = endUserRepository.findByPhoneNumber(phoneNumber);
        return getEndUserResponseResponseEntity(existingEndUser);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateAdmin(final AdminRequest adminRequest) {
        Optional<Admin> existingUser = adminRepository.findById(adminRequest.getId());
        if(existingUser.isPresent()){
            Admin admin =  existingUser.get();

            if(!checkUniqueConstrains(adminRequest, admin)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Admin updateAdmin = UserEntityConverter.toAdmin(adminRequest);

            ResponseEntity<HttpStatus> updateRes = updateRolePrivilege(adminRequest, admin, updateAdmin);
            if(updateRes != null){
                return updateRes;
            }

            List<StaffList> staffLists = toListOfStaffList(adminRequest, updateAdmin);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateAdmin.setShops(staffLists);

            if(adminRequest.getPassword().isEmpty()){
                updateAdmin.setPassword(existingUser.get().getPassword());
            }
            else{
                updateAdmin.setPassword(passwordEncoder.encode(updateAdmin.getPassword()));
            }

            updateAdmin.setCreationDate(admin.getCreationDate());

            userEntityService.save(updateAdmin);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateModerator(final ModeratorRequest moderatorRequest) {
        Optional<Moderator> existingUser = moderatorRepository.findById(moderatorRequest.getId());
        if(existingUser.isPresent()){
            Moderator moderator = existingUser.get();

            if(!checkUniqueConstrains(moderatorRequest, moderator)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Moderator updateModerator = UserEntityConverter.toModerator(moderatorRequest);

            ResponseEntity<HttpStatus> updateRes = updateRolePrivilege(moderatorRequest, moderator, updateModerator);
            if(updateRes != null){
                return updateRes;
            }


            List<StaffList> staffLists = toListOfStaffList(moderatorRequest, updateModerator);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateModerator.setShops(staffLists);

            Optional<Shop> shop = shopService.getById(moderatorRequest.getShopId());
            if(shop.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateModerator.setShop(shop.get());

            if(moderatorRequest.getPassword().isEmpty()){
                updateModerator.setPassword(existingUser.get().getPassword());
            }
            else{
                updateModerator.setPassword(passwordEncoder.encode(updateModerator.getPassword()));
            }

            if(moderator.getAdmin() != null) {
                updateModerator.setAdmin(moderator.getAdmin());
            }
            else{
                Optional<Admin> admin = adminRepository.findById(moderatorRequest.getId());
                if(admin.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                moderator.setAdmin(admin.get());
            }

            updateModerator.setCreationDate(moderator.getCreationDate());

            userEntityService.save(updateModerator);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateSalesRep(final SalesRepRequest salesRepRequest) {
        Optional<SalesRep> existingUser = salesRepRepository.findById(salesRepRequest.getId());
        if(existingUser.isPresent()){
            SalesRep salesRep = existingUser.get();

            if(!checkUniqueConstrains(salesRepRequest, salesRep)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            SalesRep updateSalesRep = UserEntityConverter.toSalesRep(salesRepRequest);

            ResponseEntity<HttpStatus> updateRes = updateRolePrivilege(salesRepRequest, salesRep, updateSalesRep);
            if(updateRes != null){
                return updateRes;
            }

            List<StaffList> staffLists = toListOfStaffList(salesRepRequest, updateSalesRep);
            if(staffLists.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateSalesRep.setShops(staffLists);

            Optional<Shop> shop = shopService.getById(salesRepRequest.getShopId());
            if(shop.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateSalesRep.setShop(shop.get());


            Optional<Company> company = companyService.getById(salesRepRequest.getCompanyId());
            if (company.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            updateSalesRep.setCompany(company.get());

            if(salesRepRequest.getPassword().isEmpty()){
                updateSalesRep.setPassword(existingUser.get().getPassword());
            }
            else {
                updateSalesRep.setPassword(passwordEncoder.encode(updateSalesRep.getPassword()));
            }

            if(salesRep.getAdmin() != null) {
                updateSalesRep.setAdmin(salesRep.getAdmin());
            }
            else{
                Optional<Admin> admin = adminRepository.findById(updateSalesRep.getId());
                if(admin.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                salesRep.setAdmin(admin.get());
            }

            updateSalesRep.setCreationDate(salesRep.getCreationDate());

            userEntityService.save(updateSalesRep);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateEndUser(final EndUserRequest endUserRequest) {
        Optional<EndUser> existingUser = endUserRepository.findById(endUserRequest.getId());
        if(existingUser.isPresent()){
            EndUser endUser = existingUser.get();

            if(!checkUniqueConstrains(endUserRequest, endUser)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            EndUser updateEndUser = UserEntityConverter.toEndUser(endUserRequest);

            ResponseEntity<HttpStatus> updateRes = updateRolePrivilege(endUserRequest, endUser, updateEndUser);
            if(updateRes!=null){
                return updateRes;
            }

            if(endUserRequest.getPassword().isEmpty()){
                updateEndUser.setPassword(existingUser.get().getPassword());
            }
            else {
                updateEndUser.setPassword(passwordEncoder.encode(updateEndUser.getPassword()));
            }

            updateEndUser.setCreationDate(endUser.getCreationDate());

            userEntityService.save(updateEndUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteAdmin(String id, String requestAdminId) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if(existingAdmin.isPresent()) {
            Admin admin = existingAdmin.get();
            if (requestAdminId.equals(admin.getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Optional<Admin> existingRequestAdmin = adminRepository.findById(requestAdminId);
            if (existingRequestAdmin.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Admin requestAdmin = existingRequestAdmin.get();

            moderatorRepository.getAllByAdminId(admin.getId())
                        .forEach(moderator -> {
                            moderator.setAdmin(requestAdmin);
                            moderatorRepository.save(moderator);
                        });

            salesRepRepository.getAllByAdminId(admin.getId())
                        .forEach(salesRep -> {
                            salesRep.setAdmin(requestAdmin);
                            salesRepRepository.save(salesRep);
                        });

            adminRepository.deleteById(admin.getId());

            deleteStaff(admin);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteModerator(final String id){
        Optional<Moderator> existingModerator = moderatorRepository.findById(id);
        if(existingModerator.isPresent()) {
            Moderator moderator = existingModerator.get();

            moderatorRepository.deleteById(moderator.getId());

            deleteStaff(moderator);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteSalesRep(final String id){
        Optional<SalesRep> existingSalesRep = salesRepRepository.findById(id);
        if(existingSalesRep.isPresent()) {
            SalesRep salesRep = existingSalesRep.get();

            salesRepRepository.deleteById(salesRep.getId());

            deleteStaff(salesRep);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteEndUser(final String id){
        Optional<EndUser> existingEndUser = endUserRepository.findById(id);
        if(existingEndUser.isPresent()) {
            EndUser endUser = existingEndUser.get();

            if(endUser.getCart() != null) {
                cartService.deleteCartById(endUser.getCart().getId());
            }

            if(!endUser.getShoppingOrders().isEmpty()) {
                shoppingOrderService.deleteAll(endUser.getShoppingOrders());
            }

            endUserRepository.deleteById(endUser.getId());

            deleteUserEntity(endUser);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> saveAvatar(String entityId, MultipartFile mediaFile) {
        return avatarUserEntityApiServiceImpl.save(entityId, mediaFile);
    }

    @Override
    public ResponseEntity<MediaResponse> getAvatarByMediaName(String mediaName) {
        return avatarUserEntityApiServiceImpl.getByMediaName(mediaName);
    }

    @Override
    public ResponseEntity<MediaResponse> getAvatarForEntity(String userId) {
        return avatarUserEntityApiServiceImpl.getForEntity(userId);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAvatar(String userId) {
        return avatarUserEntityApiServiceImpl.delete(userId);
    }


    private void deleteStaff(final Staff staff){
        if(!staff.getShops().isEmpty()){
            staffListRepository.deleteStaffListByStaffId(staff.getId());
            staffRepository.deleteById(staff.getId());
        }
        deleteUserEntity(staff);
    }

    private void deleteUserEntity(final UserEntity userEntity){
        feedbackService.getAllFeedbacksForUser(userEntity.getId())
                        .forEach(feedback -> feedbackService.delete(feedback.getId()));

        userEntity.getPrivileges().clear();

        userEntityService.delete(userEntity.getId());

        if(userEntity.getAvatar() != null) {
            avatarUserEntityServiceImpl.delete(userEntity.getId());
        }
    }

    private boolean checkUniqueConstrains(UserEntityRequest userEntityRequest, UserEntity existedUser) {
        if(userEntityRequest.getEmail() != null) {
            if (userEntityService.existsByEmail(userEntityRequest.getEmail())
                    && !existedUser.getEmail().equals(userEntityRequest.getEmail())) {
                return false;
            }
        }

        if(userEntityRequest.getLogin() != null) {
            if (userEntityService.existByLogin(userEntityRequest.getLogin())
                    && !existedUser.getLogin().equals(userEntityRequest.getLogin())) {
                return false;
            }
        }

        if(userEntityRequest.getPhoneNumber() != null) {
            if (userEntityService.existByPhoneNumber(userEntityRequest.getPhoneNumber())
                    && !existedUser.getPhoneNumber().equals(userEntityRequest.getPhoneNumber())) {
                return false;
            }
        }
        return true;
    }

    private ResponseEntity<AdminResponse> getAdminResponseResponseEntity(Optional<Admin> existingAdmin) {
        if(existingAdmin.isPresent()){
            Admin admin = existingAdmin.get();
            AdminResponse response = UserEntityConverter.toAdminResponse(admin);
            response.setAvatar(avatarUserEntityServiceImpl.getUrl(avatarUserEntityServiceImpl.getForEntity(admin.getId())));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ModeratorResponse> getModeratorResponseResponseEntity(final Optional<Moderator> existingModerator) {
        if(existingModerator.isPresent()){
            Moderator moderator = existingModerator.get();
            ModeratorResponse response = UserEntityConverter.toModeratorResponse(moderator);
            response.setAvatar(avatarUserEntityServiceImpl.getUrl(avatarUserEntityServiceImpl.getForEntity(moderator.getId())));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<SalesRepResponse> getSalesRepResponseResponseEntity(final Optional<SalesRep> existingSalesRep) {
        if(existingSalesRep.isPresent()){
            SalesRep salesRep = existingSalesRep.get();
            SalesRepResponse response = UserEntityConverter.toSalesRepResponse(salesRep);
            response.setAvatar(avatarUserEntityServiceImpl.getUrl(avatarUserEntityServiceImpl.getForEntity(salesRep.getId())));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<EndUserResponse> getEndUserResponseResponseEntity(final Optional<EndUser> existingEndUser) {
        if(existingEndUser.isPresent()){
            EndUser endUser = existingEndUser.get();
            EndUserResponse response = UserEntityConverter.toEndUserResponse(endUser);
            response.setAvatar(avatarUserEntityServiceImpl.getUrl(avatarUserEntityServiceImpl.getForEntity(endUser.getId())));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> checkAndSetRoleAndPrivileges(UserEntityRequest userEntityRequest, UserEntity userEntity) {
        if(userEntityRequest.getRole() != null) {
            Optional<Role> role = rolePrivilegePreload.getRole(userEntityRequest.getRole().getId());
            if(role.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            userEntity.setRole(role.get());
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!userEntityRequest.getPrivileges().isEmpty()) {
            List<UserEntityPrivilege> userEntityPrivileges = toListOfUserEntityPrivilege(userEntityRequest, userEntity);

            if(userEntityPrivileges.isEmpty()){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            userEntity.setPrivileges(userEntityPrivileges);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private List<StaffList> toListOfStaffList(StaffRequest staffRequest, Staff staff){
        return staffRequest.getShops().stream()
                .map(shopId -> {
                    Optional<Shop> shop = shopService.getById(shopId);
                    if(shop.isPresent()) {
                        StaffList staffList = new StaffList();
                        staffList.setShop(shop.get());
                        staffList.setStaff(staff);
                        return staffList;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private ResponseEntity<HttpStatus> updateRolePrivilege(UserEntityRequest userEntityRequest, UserEntity existingUserEntity, UserEntity updateUserEntity) {
        Optional<Role> role;
        if (userEntityRequest.getRole() != null) {
            role = rolePrivilegePreload.getRole(userEntityRequest.getRole().getId());
            if (role.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            updateUserEntity.setRole(role.get());
        } else {
            updateUserEntity.setRole(existingUserEntity.getRole());
        }

        List<UserEntityPrivilege> userEntityPrivileges;
        if (userEntityRequest.getPrivileges()!=null && !userEntityRequest.getPrivileges().isEmpty()) {
            userEntityPrivileges = toListOfUserEntityPrivilege(userEntityRequest, updateUserEntity);

            if (userEntityPrivileges.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            updateUserEntity.setPrivileges(userEntityPrivileges);
        } else {
            userEntityPrivileges = existingUserEntity.getPrivileges();
        }
        updateUserEntity.setPrivileges(userEntityPrivileges);

        return null;
    }

    private List<UserEntityPrivilege> toListOfUserEntityPrivilege(UserEntityRequest userEntityRequest, UserEntity userEntity) {
        return userEntityRequest.getPrivileges().stream()
                .map(privilegeRequest -> {
                    Optional<Privilege> existingPrivilege = rolePrivilegePreload.getPrivilege(privilegeRequest.getId());
                    if (existingPrivilege.isPresent()) {
                        UserEntityPrivilege userEntityPrivilege =  new UserEntityPrivilege();
                        userEntityPrivilege.setUserEntity(userEntity);
                        userEntityPrivilege.setPrivilege(existingPrivilege.get());
                        return userEntityPrivilege;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

}