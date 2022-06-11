package api.iterator.backend.configs.users;

import api.iterator.backend.constants.UserTypeConstant;
import api.iterator.backend.entities.UserEntity;
import api.iterator.backend.entities.UserLoginEntity;
import api.iterator.backend.repositories.ApplicantRepository;
import api.iterator.backend.repositories.UserLoginRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String APPLICANT = UserTypeConstant.APPLICANT;

    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;

    CustomUserDetailsService(UserRepository userRepository, UserLoginRepository userLoginRepository, ApplicantRepository applicantRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.applicantRepository = applicantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserLoginEntity userLoginEntity = userLoginRepository.findByUserName(username);
        if (userLoginEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        UserEntity userEntity = userRepository.findUserById(userLoginEntity.getUserId());
        // Set user type for user principal
        userEntity.setUserName(userLoginEntity.getUserName());
        userEntity.setUserPassword(userLoginEntity.getUserPassword());

        // If user role is applicant => set applicant id
        if (Objects.equals(userEntity.getUserType(), APPLICANT)) {
            applicantRepository.findByUserId(userEntity.getId()).ifPresent(
                    applicant -> userEntity.setApplicantId(applicant.getId())
            );
        }

        return new CustomUserPrincipal(userEntity);
    }
}
