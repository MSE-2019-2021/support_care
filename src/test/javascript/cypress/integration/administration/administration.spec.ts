import { userManagementPageHeadingSelector } from '../../support/commands';

describe('/admin', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.visit('');
    cy.login('admin', 'admin');
  });

  describe('/user-management', () => {
    it('should load the page', () => {
      cy.clickOnAdminMenuItem('user-management');
      cy.get(userManagementPageHeadingSelector).should('be.visible');
    });
  });
});
