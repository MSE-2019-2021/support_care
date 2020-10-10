import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProtocolComponentsPage, ProtocolDeleteDialog, ProtocolUpdatePage } from './protocol.page-object';

const expect = chai.expect;

describe('Protocol e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let protocolComponentsPage: ProtocolComponentsPage;
  let protocolUpdatePage: ProtocolUpdatePage;
  let protocolDeleteDialog: ProtocolDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Protocols', async () => {
    await navBarPage.goToEntity('protocol');
    protocolComponentsPage = new ProtocolComponentsPage();
    await browser.wait(ec.visibilityOf(protocolComponentsPage.title), 5000);
    expect(await protocolComponentsPage.getTitle()).to.eq('supportcareApp.protocol.home.title');
    await browser.wait(ec.or(ec.visibilityOf(protocolComponentsPage.entities), ec.visibilityOf(protocolComponentsPage.noResult)), 1000);
  });

  it('should load create Protocol page', async () => {
    await protocolComponentsPage.clickOnCreateButton();
    protocolUpdatePage = new ProtocolUpdatePage();
    expect(await protocolUpdatePage.getPageTitle()).to.eq('supportcareApp.protocol.home.createOrEditLabel');
    await protocolUpdatePage.cancel();
  });

  it('should create and save Protocols', async () => {
    const nbButtonsBeforeCreate = await protocolComponentsPage.countDeleteButtons();

    await protocolComponentsPage.clickOnCreateButton();

    await promise.all([
      protocolUpdatePage.setToxicityDiagnosisInput('toxicityDiagnosis'),
      protocolUpdatePage.setCreateUserInput('createUser'),
      protocolUpdatePage.setCreateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      protocolUpdatePage.setUpdateUserInput('updateUser'),
      protocolUpdatePage.setUpdateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      protocolUpdatePage.therapeuticRegimeSelectLastOption(),
      protocolUpdatePage.outcomeSelectLastOption(),
      protocolUpdatePage.guideSelectLastOption(),
    ]);

    expect(await protocolUpdatePage.getToxicityDiagnosisInput()).to.eq(
      'toxicityDiagnosis',
      'Expected ToxicityDiagnosis value to be equals to toxicityDiagnosis'
    );
    expect(await protocolUpdatePage.getCreateUserInput()).to.eq('createUser', 'Expected CreateUser value to be equals to createUser');
    expect(await protocolUpdatePage.getCreateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createDate value to be equals to 2000-12-31'
    );
    expect(await protocolUpdatePage.getUpdateUserInput()).to.eq('updateUser', 'Expected UpdateUser value to be equals to updateUser');
    expect(await protocolUpdatePage.getUpdateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updateDate value to be equals to 2000-12-31'
    );

    await protocolUpdatePage.save();
    expect(await protocolUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await protocolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Protocol', async () => {
    const nbButtonsBeforeDelete = await protocolComponentsPage.countDeleteButtons();
    await protocolComponentsPage.clickOnLastDeleteButton();

    protocolDeleteDialog = new ProtocolDeleteDialog();
    expect(await protocolDeleteDialog.getDialogTitle()).to.eq('supportcareApp.protocol.delete.question');
    await protocolDeleteDialog.clickOnConfirmButton();

    expect(await protocolComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
