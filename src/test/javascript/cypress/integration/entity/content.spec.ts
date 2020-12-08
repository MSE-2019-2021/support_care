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

describe('Content e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/contents*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Contents', () => {
    cy.server();
    cy.route('GET', '/api/contents*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Content').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  // it('should load details Content page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/contents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('content');
  //   cy.wait('@entitiesRequest');
  //   if (startingEntitiesCount > 0) {
  //     cy.get(entityDetailsButtonSelector).first().click({ force: true });
  //     cy.getEntityDetailsHeading('content');
  //     cy.get(entityDetailsBackButtonSelector).should('exist');
  //   }
  //   cy.visit('/');
  // });

  // it('should load create Content page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/contents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('content');
  //   cy.wait('@entitiesRequest');
  //   cy.get(entityCreateButtonSelector).click({ force: true });
  //   cy.getEntityCreateHeading('Content');
  //   cy.get(entityCreateSaveButtonSelector).should('exist');
  //   cy.visit('/');
  // });

  // it('should load edit Content page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/contents*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('content');
  //   cy.wait('@entitiesRequest');
  //   if (startingEntitiesCount > 0) {
  //     cy.get(entityEditButtonSelector).first().click({ force: true });
  //     cy.getEntityUpdateHeading('Content');
  //     cy.get(entityCreateSaveButtonSelector).should('exist');
  //   }
  //   cy.visit('/');
  // });

  /* this test is commented because it contains required relationships
  it('should create an instance of Content', () => {
    cy.server();
    cy.route('GET', '/api/contents*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({force: true});
    cy.getEntityCreateUpdateHeading('Content');

    cy.setFieldImageAsBytesOfEntity('data', 'integration-test.png', 'image/png');

    cy.get(entityCreateSaveButtonSelector).click({force: true});
    cy.scrollTo('top', {ensureScrollable: false});
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/contents*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
  */

  /* this test is commented because it contains required relationships
  it('should delete last instance of Content', () => {
    cy.server();
    cy.route('GET', '/api/contents*').as('entitiesRequest');
    cy.route('DELETE', '/api/contents/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('content');
    cy.wait('@entitiesRequest').its('responseBody').then(array => {
      startingEntitiesCount = array.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({force: true});
        cy.getEntityDeleteDialogHeading('content').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({force: true});
        cy.wait('@deleteEntityRequest');
        cy.route('GET', '/api/contents*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('content');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
  */
});
