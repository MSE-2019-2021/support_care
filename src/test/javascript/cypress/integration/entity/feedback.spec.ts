import { entityTableSelector } from '../../support/entity';

describe('Feedback e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Feedbacks (MSEDO-185 - 1)', () => {
    cy.intercept('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Feedback').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });
});
