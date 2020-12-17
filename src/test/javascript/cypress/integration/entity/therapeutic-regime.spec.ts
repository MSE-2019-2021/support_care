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

describe('TherapeuticRegime e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest')
      .its('responseBody')
      .then(array => {
        startingEntitiesCount = array.length;
      });
    cy.visit('/');
  });

  it('should load TherapeuticRegimes', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('TherapeuticRegime').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details TherapeuticRegime page', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('therapeuticRegime');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create TherapeuticRegime page', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('TherapeuticRegime');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit TherapeuticRegime page', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('TherapeuticRegime');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  /* this test is commented because it contains required relationships
  it('should create an instance of TherapeuticRegime', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({force: true});
    cy.getEntityCreateUpdateHeading('TherapeuticRegime');

    cy.get(`[data-cy="name"]`).type('Aço', { force: true }).invoke('val').should('match', new RegExp('Aço'));


    cy.get(`[data-cy="acronym"]`).type('AI Atum Chapéu', { force: true }).invoke('val').should('match', new RegExp('AI Atum Chapéu'));


    cy.get(`[data-cy="purpose"]`).type('synergize calculating', { force: true }).invoke('val').should('match', new RegExp('synergize calculating'));


    cy.get(`[data-cy="condition"]`).type('Business-focused Madeira HTTP', { force: true }).invoke('val').should('match', new RegExp('Business-focused Madeira HTTP'));


    cy.get(`[data-cy="timing"]`).type('Enterprise-wide TCP', { force: true }).invoke('val').should('match', new RegExp('Enterprise-wide TCP'));


    cy.get(`[data-cy="indication"]`).type('generating Peru system', { force: true }).invoke('val').should('match', new RegExp('generating Peru system'));


    cy.get(`[data-cy="criteria"]`).type('AI e-commerce visionary', { force: true }).invoke('val').should('match', new RegExp('AI e-commerce visionary'));


    cy.get(`[data-cy="notice"]`).type('reintermediate deliverables', { force: true }).invoke('val').should('match', new RegExp('reintermediate deliverables'));

    cy.setFieldSelectToLastOfEntity('drug');

    cy.setFieldSelectToLastOfEntity('treatment');

    cy.get(entityCreateSaveButtonSelector).click({force: true});
    cy.scrollTo('top', {ensureScrollable: false});
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });
  */

  /* this test is commented because it contains required relationships
  it('should delete last instance of TherapeuticRegime', () => {
    cy.server();
    cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequest');
    cy.route('DELETE', '/api/therapeutic-regimes/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('therapeutic-regime');
    cy.wait('@entitiesRequest').its('responseBody').then(array => {
      startingEntitiesCount = array.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({force: true});
        cy.getEntityDeleteDialogHeading('therapeuticRegime').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({force: true});
        cy.wait('@deleteEntityRequest');
        cy.route('GET', '/api/therapeutic-regimes*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('therapeutic-regime');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
  */
});
