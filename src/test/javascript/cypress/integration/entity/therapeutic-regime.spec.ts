import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
} from '../../support/entity';

describe('TherapeuticRegime e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load TherapeuticRegimes (MSEDO-83 - 1)', () => {
    cy.intercept('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('TherapeuticRegime').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details TherapeuticRegime page (MSEDO-82 - 1)', () => {
    cy.intercept('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('therapeuticRegime');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create TherapeuticRegime page', () => {
    cy.intercept('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateHeading('TherapeuticRegime');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit TherapeuticRegime page (MSEDO-85 - 1)', () => {
    cy.intercept('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.get(entityEditButtonSelector).click({ force: true });
      cy.getEntityUpdateHeading('TherapeuticRegime');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });
});
