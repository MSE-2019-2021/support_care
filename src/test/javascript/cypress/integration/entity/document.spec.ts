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

describe('Document e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/documents*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('document');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  /*
  it.only('should load Documents', () => {
    cy.server();
    cy.route('GET', '/api/documents*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('document');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Document').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });
  */

  // it('should load details Document page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/documents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('document');
  //   cy.wait('@entitiesRequest');
  //   if (startingEntitiesCount > 0) {
  //     cy.get(entityDetailsButtonSelector).first().click({ force: true });
  //     cy.getEntityDetailsHeading('document');
  //     cy.get(entityDetailsBackButtonSelector).should('exist');
  //   }
  //   cy.visit('/');
  // });

  // it('should load create Document page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/documents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('document');
  //   cy.wait('@entitiesRequest');
  //   cy.get(entityCreateButtonSelector).click({ force: true });
  //   cy.getEntityCreateHeading('Document');
  //   cy.get(entityCreateSaveButtonSelector).should('exist');
  //   cy.visit('/');
  // });

  // it('should load edit Document page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/documents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('document');
  //   cy.wait('@entitiesRequest');
  //   if (startingEntitiesCount > 0) {
  //     cy.get(entityEditButtonSelector).first().click({ force: true });
  //     cy.getEntityUpdateHeading('Document');
  //     cy.get(entityCreateSaveButtonSelector).should('exist');
  //   }
  //   cy.visit('/');
  // });

  /* this test is commented because it contains required relationships
  it('should create an instance of Document', () => {
    cy.server();
    cy.route('GET', '/api/documents*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('document');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({force: true});
    cy.getEntityCreateUpdateHeading('Document');

    cy.get(`[data-cy="title"]`).type('invoice Tanzanian', { force: true }).invoke('val').should('match', new RegExp('invoice Tanzanian'));


    cy.get(`[data-cy="size"]`).type('49810').should('have.value', '49810');


    cy.get(`[data-cy="mimeType"]`).type('24/7', { force: true }).invoke('val').should('match', new RegExp('24/7'));

    cy.setFieldSelectToLastOfEntity('content');

    cy.setFieldSelectToLastOfEntity('outcome');

    cy.get(entityCreateSaveButtonSelector).click({force: true});
    cy.scrollTo('top', {ensureScrollable: false});
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/documents*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('document');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
  */

  /* this test is commented because it contains required relationships
  it('should delete last instance of Document', () => {
    cy.server();
    cy.route('GET', '/api/documents*').as('entitiesRequest');
    cy.route('DELETE', '/api/documents/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('document');
    cy.wait('@entitiesRequest').its('responseBody').then(array => {
      startingEntitiesCount = array.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({force: true});
        cy.getEntityDeleteDialogHeading('document').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({force: true});
        cy.wait('@deleteEntityRequest');
        cy.route('GET', '/api/documents*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('document');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
  */
});
