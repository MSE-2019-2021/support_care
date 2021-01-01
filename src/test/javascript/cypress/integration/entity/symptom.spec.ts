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
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Symptoms', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
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

  it('should load details Symptom page', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
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

  it('should load create Symptom page', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateHeading('Symptom');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Symptom page', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
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
  // revision needed
  /*it('should create an instance of Symptom', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).first().click({ force: true });
    cy.getEntityCreateHeading('Symptom');

    cy.get(`[data-cy="name"]`)
      .type('Research visionary Desporto', { force: true })
      .invoke('val')
      .should('match', new RegExp('Research visionary Desporto'));

    cy.get(`[data-cy="notice"]`).type('Aço', { force: true }).invoke('val').should('match', new RegExp('Aço'));

    cy.setFieldSelectToLastOfEntity('therapeuticRegime');

    cy.setFieldSelectToLastOfEntity('outcome');

    cy.setFieldSelectToLastOfEntity('toxicityRate');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/symptoms*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });*/

  /***
  it('should delete last instance of Symptom', () => {
    cy.server();
    cy.route('GET', '/api/symptoms*').as('entitiesRequest');
    cy.route('DELETE', '/api/symptoms/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('symptom');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('symptom').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/symptoms*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('symptom');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });*/
});
