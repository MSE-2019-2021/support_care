import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TreatmentComponentsPage, TreatmentDeleteDialog, TreatmentUpdatePage } from './treatment.page-object';

const expect = chai.expect;

describe('Treatment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let treatmentComponentsPage: TreatmentComponentsPage;
  let treatmentUpdatePage: TreatmentUpdatePage;
  let treatmentDeleteDialog: TreatmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Treatments', async () => {
    await navBarPage.goToEntity('treatment');
    treatmentComponentsPage = new TreatmentComponentsPage();
    await browser.wait(ec.visibilityOf(treatmentComponentsPage.title), 5000);
    expect(await treatmentComponentsPage.getTitle()).to.eq('supportivecareApp.treatment.home.title');
    await browser.wait(ec.or(ec.visibilityOf(treatmentComponentsPage.entities), ec.visibilityOf(treatmentComponentsPage.noResult)), 1000);
  });

  it('should load create Treatment page', async () => {
    await treatmentComponentsPage.clickOnCreateButton();
    treatmentUpdatePage = new TreatmentUpdatePage();
    expect(await treatmentUpdatePage.getPageTitle()).to.eq('supportivecareApp.treatment.home.createOrEditLabel');
    await treatmentUpdatePage.cancel();
  });

  it('should create and save Treatments', async () => {
    const nbButtonsBeforeCreate = await treatmentComponentsPage.countDeleteButtons();

    await treatmentComponentsPage.clickOnCreateButton();

    await promise.all([treatmentUpdatePage.setTypeInput('type')]);

    expect(await treatmentUpdatePage.getTypeInput()).to.eq('type', 'Expected Type value to be equals to type');

    await treatmentUpdatePage.save();
    expect(await treatmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await treatmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Treatment', async () => {
    const nbButtonsBeforeDelete = await treatmentComponentsPage.countDeleteButtons();
    await treatmentComponentsPage.clickOnLastDeleteButton();

    treatmentDeleteDialog = new TreatmentDeleteDialog();
    expect(await treatmentDeleteDialog.getDialogTitle()).to.eq('supportivecareApp.treatment.delete.question');
    await treatmentDeleteDialog.clickOnConfirmButton();

    expect(await treatmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
