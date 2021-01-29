import {
  usernameLoginSelector,
  forgetYourPasswordSelector,
  emailResetPasswordSelector,
  submitInitResetPasswordSelector,
  classInvalid,
  classValid,
} from '../../support/commands';

describe('forgot your password', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.visit('');
    cy.get(usernameLoginSelector).type('admin');
    cy.get(forgetYourPasswordSelector).click();
  });

  beforeEach(() => {
    cy.intercept('POST', '/api/account/reset-password/init').as('initResetPassword');
  });

  it('requires email', () => {
    cy.get(emailResetPasswordSelector).should('have.class', classInvalid).type('user@gmail.com').should('have.class', classValid);
    cy.get(emailResetPasswordSelector).clear();
  });

  it('should be able to init reset password', () => {
    cy.get(emailResetPasswordSelector).type('user@gmail.com');
    cy.get(submitInitResetPasswordSelector).click({ force: true });
    cy.wait('@initResetPassword').then(({ request, response }) => expect(response.statusCode).to.equal(200));
  });
});
