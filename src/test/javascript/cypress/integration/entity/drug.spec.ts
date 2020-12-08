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

describe('Drug e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Drugs', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Drug').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Drug page', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('/symptom');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('drug');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Drug page', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateHeading('Drug');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Drug page', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('/symptom');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.get(entityEditButtonSelector).click({ force: true });
      cy.getEntityUpdateHeading('Drug');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  /* this test is commented because it contains required relationships
  it('should create an instance of Drug', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({force: true});
    cy.getEntityCreateUpdateHeading('Drug');

    cy.get(`[data-cy="name"]`).type('Loan', { force: true }).invoke('val').should('match', new RegExp('Loan'));


    cy.get(`[data-cy="description"]`).type('disintermediate', { force: true }).invoke('val').should('match', new RegExp('disintermediate'));

    cy.setFieldSelectToLastOfEntity('notice');

    cy.setFieldSelectToLastOfEntity('administration');

    cy.get(entityCreateSaveButtonSelector).click({force: true});
    cy.scrollTo('top', {ensureScrollable: false});
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/drugs*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
  */

  /* this test is commented because it contains required relationships
  it('should delete last instance of Drug', () => {
    cy.server();
    cy.route('GET', '/api/drugs*').as('entitiesRequest');
    cy.route('DELETE', '/api/drugs/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('drug');
    cy.wait('@entitiesRequest').its('responseBody').then(array => {
      startingEntitiesCount = array.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({force: true});
        cy.getEntityDeleteDialogHeading('drug').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({force: true});
        cy.wait('@deleteEntityRequest');
        cy.route('GET', '/api/drugs*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('drug');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
  */
});
