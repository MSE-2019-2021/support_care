package uc.dei.mse.supportivecare.web.rest.vm;

import javax.validation.constraints.Size;
import uc.dei.mse.supportivecare.GeneratedByJHipster;
import uc.dei.mse.supportivecare.service.dto.UserDTO;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
@GeneratedByJHipster
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
