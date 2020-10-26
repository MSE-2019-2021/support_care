import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AdministrationComponentsPage, AdministrationDeleteDialog, AdministrationUpdatePage } from './administration.page-object';

const expect = chai.expect;

describe('Administration e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let administrationComponentsPage: AdministrationComponentsPage;
  let administrationUpdatePage: AdministrationUpdatePage;
  let administrationDeleteDialog: AdministrationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Administrations', async () => {
    await navBarPage.goToEntity('administration');
    administrationComponentsPage = new AdministrationComponentsPage();
    await browser.wait(ec.visibilityOf(administrationComponentsPage.title), 5000);
    expect(await administrationComponentsPage.getTitle()).to.eq('supportivecareApp.administration.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(administrationComponentsPage.entities), ec.visibilityOf(administrationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Administration page', async () => {
    await administrationComponentsPage.clickOnCreateButton();
    administrationUpdatePage = new AdministrationUpdatePage();
    expect(await administrationUpdatePage.getPageTitle()).to.eq('supportivecareApp.administration.home.createOrEditLabel');
    await administrationUpdatePage.cancel();
  });

  it('should create and save Administrations', async () => {
    const nbButtonsBeforeCreate = await administrationComponentsPage.countDeleteButtons();

    await administrationComponentsPage.clickOnCreateButton();

    await promise.all([administrationUpdatePage.setTypeInput('type')]);

    expect(await administrationUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await administrationUpdatePage.save();
    expect(await administrationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await administrationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last Administration', async () => {
    const nbButtonsBeforeDelete = await administrationComponentsPage.countDeleteButtons();
    await administrationComponentsPage.clickOnLastDeleteButton();

    administrationDeleteDialog = new AdministrationDeleteDialog();
    expect(await administrationDeleteDialog.getDialogTitle()).to.eq('supportivecareApp.administration.delete.question');
    await administrationDeleteDialog.clickOnConfirmButton();

    expect(await administrationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
