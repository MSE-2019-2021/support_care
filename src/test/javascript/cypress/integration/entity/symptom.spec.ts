import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
} from '../../support/entity';

describe('Symptom e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Symptoms (MSEDO-29 - 1)', () => {
    cy.intercept('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Symptom').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Symptom page (MSEDO-28 - 1)', () => {
    cy.intercept('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('symptom');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Symptom page (MSEDO-25-1)', () => {
    cy.intercept('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateHeading('Symptom');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Symptom page', () => {
    cy.intercept('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.get(entityEditButtonSelector).click({ force: true });
      cy.getEntityUpdateHeading('Symptom');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });
});
