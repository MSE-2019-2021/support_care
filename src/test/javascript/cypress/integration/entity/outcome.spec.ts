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

describe('Outcome e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Outcomes', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
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

  it('should load details Outcome page', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
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

  it('should load create Outcome page', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Outcome');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Outcome page', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Outcome');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Outcome', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Outcome');

    cy.get(`[data-cy="name"]`).type('Calças Metal', { force: true }).invoke('val').should('match', new RegExp('Calças Metal'));

    cy.get(`[data-cy="description"]`).type('SMS morph', { force: true }).invoke('val').should('match', new RegExp('SMS morph'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/outcomes*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Outcome', () => {
    cy.server();
    cy.route('GET', '/api/outcomes*').as('entitiesRequest');
    cy.route('DELETE', '/api/outcomes/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('outcome');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('outcome').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/outcomes*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('outcome');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
});
