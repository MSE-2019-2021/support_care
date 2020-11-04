package uc.dei.mse.supportivecare.service;

import uc.dei.mse.supportivecare.GeneratedByJHipster;

@GeneratedByJHipster
public class EmailAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}
