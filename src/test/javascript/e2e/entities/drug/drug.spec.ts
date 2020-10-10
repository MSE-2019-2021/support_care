import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DrugComponentsPage, DrugDeleteDialog, DrugUpdatePage } from './drug.page-object';

const expect = chai.expect;

describe('Drug e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let drugComponentsPage: DrugComponentsPage;
  let drugUpdatePage: DrugUpdatePage;
  let drugDeleteDialog: DrugDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Drugs', async () => {
    await navBarPage.goToEntity('drug');
    drugComponentsPage = new DrugComponentsPage();
    await browser.wait(ec.visibilityOf(drugComponentsPage.title), 5000);
    expect(await drugComponentsPage.getTitle()).to.eq('supportcareApp.drug.home.title');
    await browser.wait(ec.or(ec.visibilityOf(drugComponentsPage.entities), ec.visibilityOf(drugComponentsPage.noResult)), 1000);
  });

  it('should load create Drug page', async () => {
    await drugComponentsPage.clickOnCreateButton();
    drugUpdatePage = new DrugUpdatePage();
    expect(await drugUpdatePage.getPageTitle()).to.eq('supportcareApp.drug.home.createOrEditLabel');
    await drugUpdatePage.cancel();
  });

  it('should create and save Drugs', async () => {
    const nbButtonsBeforeCreate = await drugComponentsPage.countDeleteButtons();

    await drugComponentsPage.clickOnCreateButton();

    await promise.all([
      drugUpdatePage.setDrugNameInput('drugName'),
      drugUpdatePage.setCreateUserInput('createUser'),
      drugUpdatePage.setCreateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      drugUpdatePage.setUpdateUserInput('updateUser'),
      drugUpdatePage.setUpdateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await drugUpdatePage.getDrugNameInput()).to.eq('drugName', 'Expected DrugName value to be equals to drugName');
    expect(await drugUpdatePage.getCreateUserInput()).to.eq('createUser', 'Expected CreateUser value to be equals to createUser');
    expect(await drugUpdatePage.getCreateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createDate value to be equals to 2000-12-31'
    );
    expect(await drugUpdatePage.getUpdateUserInput()).to.eq('updateUser', 'Expected UpdateUser value to be equals to updateUser');
    expect(await drugUpdatePage.getUpdateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updateDate value to be equals to 2000-12-31'
    );

    await drugUpdatePage.save();
    expect(await drugUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await drugComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Drug', async () => {
    const nbButtonsBeforeDelete = await drugComponentsPage.countDeleteButtons();
    await drugComponentsPage.clickOnLastDeleteButton();

    drugDeleteDialog = new DrugDeleteDialog();
    expect(await drugDeleteDialog.getDialogTitle()).to.eq('supportcareApp.drug.delete.question');
    await drugDeleteDialog.clickOnConfirmButton();

    expect(await drugComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
