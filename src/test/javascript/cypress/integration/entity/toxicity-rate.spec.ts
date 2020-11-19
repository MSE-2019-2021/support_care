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

describe('ToxicityRate e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  /*it('should load ToxicityRates', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('ToxicityRate').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });*/

  it('should load details ToxicityRate page', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('toxicityRate');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create ToxicityRate page', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ToxicityRate');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit ToxicityRate page', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('ToxicityRate');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of ToxicityRate', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ToxicityRate');

    cy.get(`[data-cy="name"]`)
      .type('Yen Rufiyaa Accountability', { force: true })
      .invoke('val')
      .should('match', new RegExp('Yen Rufiyaa Accountability'));

    cy.get(`[data-cy="description"]`)
      .type('Open-source impactful', { force: true })
      .invoke('val')
      .should('match', new RegExp('Open-source impactful'));

    cy.get(`[data-cy="notice"]`)
      .type('Camisa Profit-focused', { force: true })
      .invoke('val')
      .should('match', new RegExp('Camisa Profit-focused'));

    cy.get(`[data-cy="autonomousIntervention"]`).type('digital', { force: true }).invoke('val').should('match', new RegExp('digital'));

    cy.get(`[data-cy="interdependentIntervention"]`)
      .type('Coordinator', { force: true })
      .invoke('val')
      .should('match', new RegExp('Coordinator'));

    cy.get(`[data-cy="selfManagement"]`).type('foreground', { force: true }).invoke('val').should('match', new RegExp('foreground'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of ToxicityRate', () => {
    cy.server();
    cy.route('GET', '/api/toxicity-rates*').as('entitiesRequest');
    cy.route('DELETE', '/api/toxicity-rates/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('toxicity-rate');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
        if (startingEntitiesCount > 0) {
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
          cy.get(entityDeleteButtonSelector).last().click({ force: true });
          cy.getEntityDeleteDialogHeading('toxicityRate').should('exist');
          cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
          cy.wait('@deleteEntityRequest');
          cy.route('GET', '/api/toxicity-rates*').as('entitiesRequestAfterDelete');
          cy.visit('/');
          cy.clickOnEntityMenuItem('toxicity-rate');
          cy.wait('@entitiesRequestAfterDelete');
          cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
        }
        cy.visit('/');
      });
  });
});
