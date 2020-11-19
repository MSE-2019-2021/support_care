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

describe('Administration e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    //cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Administrations', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Administration').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Administration page', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('administration');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Administration page', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Administration');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Administration page', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Administration');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Administration', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Administration');

    cy.get(`[data-cy="type"]`).type('HDD array Fresco', { force: true }).invoke('val').should('match', new RegExp('HDD array Fresco'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/administrations*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Administration', () => {
    cy.server();
    cy.route('GET', '/api/administrations*').as('entitiesRequest');
    cy.route('DELETE', '/api/administrations/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('administration');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('administration').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/administrations*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('administration');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
});
