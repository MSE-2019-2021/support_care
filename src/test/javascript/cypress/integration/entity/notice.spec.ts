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

describe('Notice e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/notices*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load Notices', () => {
    cy.server();
    cy.route('GET', '/api/notices*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Notice').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Notice page', () => {
    cy.server();
    cy.route('GET', '/api/notices*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('notice');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  // it('should load create Notice page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/notices*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('notice');
  //   cy.wait('@entitiesRequest');
  //   cy.get(entityCreateButtonSelector).click({ force: true });
  //   cy.getEntityCreateHeading('Notice');
  //   cy.get(entityCreateSaveButtonSelector).should('exist');
  //   cy.visit('/');
  // });

  // it('should load edit Notice page', () => {
  //   cy.server();
  //   cy.route('GET', '/api/notices*').as('entitiesRequest');
  //   cy.visit('/');
  //   cy.clickOnEntityMenuItem('notice');
  //   cy.wait('@entitiesRequest');
  //   if (startingEntitiesCount > 0) {
  //     cy.get(entityEditButtonSelector).first().click({ force: true });
  //     cy.getEntityUpdateHeading('Notice');
  //     cy.get(entityCreateSaveButtonSelector).should('exist');
  //   }
  //   cy.visit('/');
  // });

  /* this test is commented because it contains required relationships
  it('should create an instance of Notice', () => {
    cy.server();
    cy.route('GET', '/api/notices*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({force: true});
    cy.getEntityCreateUpdateHeading('Notice');

    cy.get(`[data-cy="description"]`).type('generating Bicicleta', { force: true }).invoke('val').should('match', new RegExp('generating Bicicleta'));


    cy.get(`[data-cy="evaluation"]`).type('Visionary Checa', { force: true }).invoke('val').should('match', new RegExp('Visionary Checa'));


    cy.get(`[data-cy="intervention"]`).type('Toalhas middleware', { force: true }).invoke('val').should('match', new RegExp('Toalhas middleware'));

    cy.setFieldSelectToLastOfEntity('activeSubstance');

    cy.get(entityCreateSaveButtonSelector).click({force: true});
    cy.scrollTo('top', {ensureScrollable: false});
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/notices*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
  */

  /* this test is commented because it contains required relationships
  it('should delete last instance of Notice', () => {
    cy.server();
    cy.route('GET', '/api/notices*').as('entitiesRequest');
    cy.route('DELETE', '/api/notices/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('notice');
    cy.wait('@entitiesRequest').its('responseBody').then(array => {
      startingEntitiesCount = array.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({force: true});
        cy.getEntityDeleteDialogHeading('notice').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({force: true});
        cy.wait('@deleteEntityRequest');
        cy.route('GET', '/api/notices*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('notice');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
  */
});
