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

describe('Treatment e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    // cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Treatments', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Treatment').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Treatment page', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('treatment');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Treatment page', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Treatment');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Treatment page', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Treatment');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Treatment', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Treatment');

    cy.get(`[data-cy="type"]`)
      .type('Bedfordshire Designer', { force: true })
      .invoke('val')
      .should('match', new RegExp('Bedfordshire Designer'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/treatments*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Treatment', () => {
    cy.server();
    cy.route('GET', '/api/treatments*').as('entitiesRequest');
    cy.route('DELETE', '/api/treatments/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('treatment');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('treatment').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/treatments*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('treatment');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
});
