package uc.dei.mse.supportivecare.service;

import uc.dei.mse.supportivecare.GeneratedByJHipster;

@GeneratedByJHipster
public class UsernameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }
}
