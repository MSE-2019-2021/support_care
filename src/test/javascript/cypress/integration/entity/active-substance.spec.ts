import { entityTableSelector } from '../../support/entity';

describe.only('ActiveSubstance e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load ActiveSubstances', () => {
    cy.intercept('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('ActiveSubstance').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });
});
