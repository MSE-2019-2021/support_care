import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DiagnosticComponentsPage, DiagnosticDeleteDialog, DiagnosticUpdatePage } from './diagnostic.page-object';

const expect = chai.expect;

describe('Diagnostic e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let diagnosticComponentsPage: DiagnosticComponentsPage;
  let diagnosticUpdatePage: DiagnosticUpdatePage;
  let diagnosticDeleteDialog: DiagnosticDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Diagnostics', async () => {
    await navBarPage.goToEntity('diagnostic');
    diagnosticComponentsPage = new DiagnosticComponentsPage();
    await browser.wait(ec.visibilityOf(diagnosticComponentsPage.title), 5000);
    expect(await diagnosticComponentsPage.getTitle()).to.eq('supportivecareApp.diagnostic.home.title');
    await browser.wait(ec.or(ec.visibilityOf(diagnosticComponentsPage.entities), ec.visibilityOf(diagnosticComponentsPage.noResult)), 1000);
  });

  it('should load create Diagnostic page', async () => {
    await diagnosticComponentsPage.clickOnCreateButton();
    diagnosticUpdatePage = new DiagnosticUpdatePage();
    expect(await diagnosticUpdatePage.getPageTitle()).to.eq('supportivecareApp.diagnostic.home.createOrEditLabel');
    await diagnosticUpdatePage.cancel();
  });

  it('should create and save Diagnostics', async () => {
    const nbButtonsBeforeCreate = await diagnosticComponentsPage.countDeleteButtons();

    await diagnosticComponentsPage.clickOnCreateButton();

    await promise.all([diagnosticUpdatePage.setNameInput('name'), diagnosticUpdatePage.setNoticeInput('notice')]);

    expect(await diagnosticUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await diagnosticUpdatePage.getNoticeInput()).to.eq('notice', 'Expected Notice value to be equals to notice');

    await diagnosticUpdatePage.save();
    expect(await diagnosticUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await diagnosticComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Diagnostic', async () => {
    const nbButtonsBeforeDelete = await diagnosticComponentsPage.countDeleteButtons();
    await diagnosticComponentsPage.clickOnLastDeleteButton();

    diagnosticDeleteDialog = new DiagnosticDeleteDialog();
    expect(await diagnosticDeleteDialog.getDialogTitle()).to.eq('supportivecareApp.diagnostic.delete.question');
    await diagnosticDeleteDialog.clickOnConfirmButton();

    expect(await diagnosticComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
