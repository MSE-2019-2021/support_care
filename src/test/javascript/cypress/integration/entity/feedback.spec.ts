import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Feedback e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Feedbacks (MSEDO-185 - 1)', () => {
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
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

  it('should load details Feedback page', () => {
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('feedback');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Feedback page', () => {
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Feedback');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Feedback page', () => {
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Feedback');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should delete last instance of Feedback', () => {
    cy.server();
    cy.route('GET', '/api/feedbacks*').as('entitiesRequest');
    cy.route('DELETE', '/api/feedbacks/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('feedback');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('feedback').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/feedbacks*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('feedback');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
});
