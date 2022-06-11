package api.iterator.backend.business.logic;

import api.iterator.backend.entities.ApplicantEntity;
import api.iterator.backend.entities.UserEntity;
import api.iterator.backend.entities.UserLoginEntity;
import api.iterator.backend.models.UserRegisterModel;
import api.iterator.backend.repositories.ApplicantRepository;
import api.iterator.backend.repositories.UserLoginRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserRegisterBusinessImpl {
    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final ApplicantRepository applicantRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserRegisterBusinessImpl(UserRepository userRepository, UserLoginRepository userLoginRepository, ApplicantRepository applicantRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.applicantRepository = applicantRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void register(UserRegisterModel registerModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerModel.getFirstName());
        userEntity.setLastName(registerModel.getLastName());
        userEntity.setPhoneNumber(registerModel.getPhoneNumber());
        userEntity.setEmail(registerModel.getEmail());
        userEntity.setUserTypeId(registerModel.getUserTypeId());

        // Save new user
        UserEntity newUser = userRepository.save(userEntity);

        UserLoginEntity userLoginEntity = new UserLoginEntity();
        userLoginEntity.setUserId(newUser.getId());
        userLoginEntity.setUserName(registerModel.getUserName());
        userLoginEntity.setUserPassword(bCryptPasswordEncoder.encode(registerModel.getUserPassword()));

        // Save new user's login credentials
        userLoginRepository.save(userLoginEntity);

        // Check if user type is applicant
        if (newUser.getUserTypeId() == 1) {
            ApplicantEntity applicantEntity = new ApplicantEntity();
            applicantEntity.setUserId(newUser.getId());
            applicantEntity.setEntryLevel(registerModel.getEntryLevel());
            applicantEntity.setExperienceYear(registerModel.getExperienceYear());

            applicantRepository.save(applicantEntity);
        }
    }
}
