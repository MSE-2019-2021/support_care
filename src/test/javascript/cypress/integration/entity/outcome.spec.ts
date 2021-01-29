import { entityTableSelector, entityDetailsButtonSelector, entityDetailsBackButtonSelector } from '../../support/entity';

describe('Outcome e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Outcomes (MSEDO-177 - 4)', () => {
    cy.intercept('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Outcome').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Outcome page (MSEDO-179 - 3)', () => {
    cy.intercept('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('outcome');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });
});
