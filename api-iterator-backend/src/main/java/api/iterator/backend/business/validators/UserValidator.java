package api.iterator.backend.business.validators;

import api.iterator.backend.exceptions.DuplicateEmailException;
import api.iterator.backend.exceptions.DuplicateUsernameException;
import api.iterator.backend.exceptions.UserNotExistException;
import api.iterator.backend.repositories.UserLoginRepository;
import api.iterator.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;

    public UserValidator(UserLoginRepository userLoginRepository, UserRepository userRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userRepository = userRepository;
    }

    public void checkDuplicateUsername(String username) throws DuplicateUsernameException {
        if (userLoginRepository.findByUserName(username) != null) {
            throw new DuplicateUsernameException(username);
        }
    }

    public void checkDuplicateEmail(String email) throws DuplicateEmailException {
        if (userRepository.findByEmail(email) != null) {
            throw new DuplicateEmailException(email);
        }
    }

    public void checkUserExist(Long id) throws UserNotExistException {
        if(userRepository.findUserById(id) == null) {
            throw new UserNotExistException(id);
        }
    }
}
