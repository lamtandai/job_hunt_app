package application.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import application.project.domain.Exception.IdInvalidException;
import application.project.domain.dto.request.ReqUserRegisterDTO;
import application.project.domain.dto.request.ReqUserUpdateDTO;
import application.project.domain.dto.response.PageMetadata;
import application.project.domain.dto.response.ResUserDTO;
import application.project.domain.dto.response.ResultReturnedDTO;
import application.project.domain.User.User_account;
import application.project.repository.FilterableJdbcRepository;
import application.project.repository.UserRepository;
import application.project.repository.JdbcSpecification.IjdbcSpecification;
import application.project.util.Mapper.UserMapper;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FilterableJdbcRepository repo;

    public UserService(FilterableJdbcRepository repo, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
    }

    public Optional<User_account> handleCreateUser(ReqUserRegisterDTO userDto) {
        String hash_password = this.passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(hash_password);
        return this.userRepository.createUser(userDto).flatMap(this::handleGetOneUser);
    }

    public ResultReturnedDTO handleGetAllUser(int page, int size) {

        Page<User_account> userPage = this.userRepository.findAllByPage(page, size);

        PageMetadata meta = PageMetadata
                .builder()
                .currentPage(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalPage(userPage.getTotalPages())
                .totalElement(userPage.getNumberOfElements())
                .build();

        return new ResultReturnedDTO(meta, userPage.getContent());

    }

    public Optional<User_account> handleGetOneUser(long id) {
        return this.userRepository.find(id);
    }

    public Optional<User_account> handleGetOneUserByUsername(String username) {
        return this.userRepository.findByUserName(username);
    }

    public Optional<User_account> handleUpdateUser(long id, ReqUserUpdateDTO dto) {
        return this.userRepository.find(id).map(
                user -> {

                    List<String> conditions = new ArrayList<>();
                    Map<String, Object> value_for_update = new HashMap<>();

                    value_for_update.put("id", String.valueOf(id));

                    if (dto.getEmail() != null && !dto.getEmail().equals(user.getUs_email())) {
                        conditions.add("us_email = :us_email");
                        value_for_update.put("us_email", dto.getEmail());
                    }

                    if (dto.getPassword() != null) {
                        String hash_password = this.passwordEncoder.encode(dto.getPassword());
                        conditions.add("us_password  = :us_password");
                        value_for_update.put("us_password", hash_password);
                    }
                    if (dto.getAvatar() != null) {
                        conditions.add("us_avatar = :us_avatar");
                        value_for_update.put("us_avatar", dto.getAvatar());
                    }

                    if (dto.getPhone() != null && !dto.getPhone().equals(user.getUs_phone())) {
                        conditions.add("us_phone = :us_phone");
                        value_for_update.put("us_phone", dto.getPhone());
                    }
                    
                    if (dto.getUser_gender() != null && !dto.getUser_gender().equals(user.getUs_gender())) {
                        conditions.add("us_gender = :us_gender");
                        value_for_update.put("us_gender", dto.getUser_gender().name());
                    }

                    if (dto.getUser_role() != null && !dto.getUser_role().equals(user.getUs_role())) {
                        conditions.add("us_role = :us_role");
                        value_for_update.put("us_role", dto.getUser_role().name());
                    }

                    if (dto.getCpn_id() != 0 && dto.getCpn_id() != user.getUs_cpn_id()){
                        conditions.add("us_cpn_id = :us_cpn_id");
                        value_for_update.put("us_cpn_id", dto.getCpn_id());
                    }

                    return this.userRepository.update(conditions, value_for_update).orElseThrow();
                });
    }

    public boolean handleUserEmailExist(String email) {
        return this.userRepository.findByUserEmail(email).isPresent();
    }

    public boolean handleUserNameExist(String username) {
        return this.userRepository.findByUserName(username).isPresent();
    }
    
    public <T> ResultReturnedDTO handleGetAllUserByFilter(

            IjdbcSpecification<T> spec,
            Pageable pageable) {

        Page<T> userPage = this.repo.findAll(spec, pageable);

        PageMetadata meta = PageMetadata
                .builder()
                .currentPage(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .totalPage(userPage.getTotalPages())
                .totalElement(userPage.getNumberOfElements())
                .build();

        @SuppressWarnings("unchecked")
        Page<User_account> casted = (Page<User_account>) userPage;
        List<ResUserDTO> userDtos = casted.getContent().stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());

        return new ResultReturnedDTO(meta, userDtos);

    }

    public void handleUpdateUserToken(String Token, String userName) {
        Optional<User_account> UaOpt = this.handleGetOneUserByUsername(userName);
        if (UaOpt.isPresent()) {
            this.userRepository.updateUserRefreshToken(Token, UaOpt.get().getUs_account_id());
        }

    }

    public Optional<User_account> handleGetUserByUsernameAndRefreshToken(String token, String userName) {
        return this.userRepository.findByUserNameAndRefreshToken(token, userName);
    }

    public void handleDeleteUserRefreshToken(long id) {
        this.userRepository.deleteUserRefreshToken(id);
    }

    public void handleDeleteUserById(long id) throws IdInvalidException{
        Optional <User_account> userOpt = handleGetOneUser(id);
        if (userOpt.isPresent() && !userOpt.get().isUs_deleted()){
            this.userRepository.deleteUserById(id);
            return;
        }

        throw new IdInvalidException(id);
    }
}
