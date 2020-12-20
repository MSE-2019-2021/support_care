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

describe.only('ActiveSubstance e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load ActiveSubstances', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('ActiveSubstance').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details ActiveSubstance page', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('activeSubstance');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create ActiveSubstance page', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ActiveSubstance');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit ActiveSubstance page', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('ActiveSubstance');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  /*
  it.only('should create an instance of ActiveSubstance', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ActiveSubstance');

    // Due to unique constraint, add date
    const now = new Date().getTime();

    cy.get(`[data-cy="name"]`).type('PCI_'+ now, { force: true }).invoke('val').should('match', new RegExp('PCI_'+ now));

    cy.get(`[data-cy="dosage"]`).type('Iowa services', { force: true }).invoke('val').should('match', new RegExp('Iowa services'));

    cy.get(`[data-cy="form"]`)
      .type('alliance empowering', { force: true })
      .invoke('val')
      .should('match', new RegExp('alliance empowering'));

    cy.get(`[data-cy="description"]`)
      .type('programming International', { force: true })
      .invoke('val')
      .should('match', new RegExp('programming International'));

    cy.setFieldSelectToLastOfEntity('administration');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/active-substances*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
*/

  /*
  it('should delete last instance of ActiveSubstance', () => {
    cy.server();
    cy.route('GET', '/api/active-substances*').as('entitiesRequest');
    cy.route('DELETE', '/api/active-substances/!*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-substance');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('activeSubstance').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/active-substances*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('active-substance');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
*/
});
