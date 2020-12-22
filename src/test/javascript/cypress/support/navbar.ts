/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable @typescript-eslint/no-use-before-define */

import {
  navbarSelector,
  adminMenuSelector,
  accountMenuSelector,
  registerItemSelector,
  loginItemSelector,
  logoutItemSelector,
  settingsItemSelector,
  passwordItemSelector,
  entityItemSelector,
  userInfoItemSelector,
} from './commands';

Cypress.Commands.add('clickOnLoginItem', () => {
  return cy.get(navbarSelector).get(accountMenuSelector).click({ force: true }).get(loginItemSelector).click({ force: true });
});

Cypress.Commands.add('clickOnLogoutItem', () => {
  return cy.get(navbarSelector).get(accountMenuSelector).click({ force: true }).get(logoutItemSelector).click({ force: true });
});

Cypress.Commands.add('clickOnRegisterItem', () => {
  return cy.get('[data-cy="registerLink"]').click({ force: true });
});

Cypress.Commands.add('clickOnSettingsItem', () => {
  return cy.get(navbarSelector).get(accountMenuSelector).click({ force: true }).get(settingsItemSelector).click({ force: true });
});

Cypress.Commands.add('clickOnPasswordItem', () => {
  return cy.get(navbarSelector).get(accountMenuSelector).click({ force: true }).get(passwordItemSelector).click({ force: true });
});

Cypress.Commands.add('clickOnuserInfoItem', (item: string) => {
  return cy
    .get(navbarSelector)
    .get(userInfoItemSelector)
    .click({ force: true })
    .get(`.nav-link[routerLink="admin/${item}"]`)
    .click({ force: true });
});

Cypress.Commands.add('clickOnEntityMenuItem', (entityName: string) => {
  return cy
    .get(navbarSelector)
    .get(entityItemSelector)
    .click({ force: true })
    .get(`.dropdown-item[href="/${entityName}"]`)
    .click({ force: true });
});

declare global {
  namespace Cypress {
    interface Chainable<Subject> {
      clickOnLoginItem(): Cypress.Chainable;
      clickOnLogoutItem(): Cypress.Chainable;
      clickOnRegisterItem(): Cypress.Chainable;
      clickOnSettingsItem(): Cypress.Chainable;
      clickOnPasswordItem(): Cypress.Chainable;
      clickOnAdminMenuItem(item: string): Cypress.Chainable;
      clickOnEntityMenuItem(entityName: string): Cypress.Chainable;
    }
  }
}

// Convert this to a module instead of script (allows import/export)
export {};
