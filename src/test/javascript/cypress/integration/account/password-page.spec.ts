import {
  currentPasswordSelector,
  newPasswordSelector,
  confirmPasswordSelector,
  submitPasswordSelector,
  classInvalid,
  classValid,
} from '../../support/commands';

describe('/account/password', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.visit('/');
    cy.login('user', 'user');
  });
  // TODO: review this when doing task MSEDO-175
  //code commented because this view is not present and it will be changed in a future feature
  /*beforeEach(() => {
    cy.server();
    cy.route('POST', '/api/account/change-password').as('passwordSave');
  });

  it('requires current password (MSEDO-174 -  3)', () => {
    cy.clickOnPasswordItem();
    cy.get(currentPasswordSelector).should('have.class', classInvalid).type('wrong-current-password').should('have.class', classValid);
    cy.get(currentPasswordSelector).clear();
  });

  it('requires new password', () => {
    cy.get(newPasswordSelector).should('have.class', classInvalid).type('jhipster').should('have.class', classValid);
    cy.get(newPasswordSelector).clear();
  });

  it('requires confirm new password', () => {
    cy.get(newPasswordSelector).type('jhipster');
    cy.get(confirmPasswordSelector).should('have.class', classInvalid).type('jhipster').should('have.class', classValid);
    cy.get(newPasswordSelector).clear();
    cy.get(confirmPasswordSelector).clear();
  });

  it('should fail to update password when using incorrect current password', () => {
    cy.get(currentPasswordSelector).type('wrong-current-password');
    cy.get(newPasswordSelector).type('jhipster');
    cy.get(confirmPasswordSelector).type('jhipster');
    cy.get(submitPasswordSelector).click({ force: true });
    cy.wait('@passwordSave').its('status').should('equal', 400);
    cy.get(currentPasswordSelector).clear();
    cy.get(newPasswordSelector).clear();
    cy.get(confirmPasswordSelector).clear();
  });

  it('should be able to update password (MSEDO-174 -  5)', () => {
    cy.get(currentPasswordSelector).type('user');
    cy.get(newPasswordSelector).type('user');
    cy.get(confirmPasswordSelector).type('user');
    cy.get(submitPasswordSelector).click({ force: true });
    cy.wait('@passwordSave').its('status').should('equal', 200);
  });*/
});
