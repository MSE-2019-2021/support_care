import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TherapeuticRegimeComponentsPage,
  TherapeuticRegimeDeleteDialog,
  TherapeuticRegimeUpdatePage,
} from './therapeutic-regime.page-object';

const expect = chai.expect;

describe('TherapeuticRegime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let therapeuticRegimeComponentsPage: TherapeuticRegimeComponentsPage;
  let therapeuticRegimeUpdatePage: TherapeuticRegimeUpdatePage;
  let therapeuticRegimeDeleteDialog: TherapeuticRegimeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TherapeuticRegimes', async () => {
    await navBarPage.goToEntity('therapeutic-regime');
    therapeuticRegimeComponentsPage = new TherapeuticRegimeComponentsPage();
    await browser.wait(ec.visibilityOf(therapeuticRegimeComponentsPage.title), 5000);
    expect(await therapeuticRegimeComponentsPage.getTitle()).to.eq('supportcareApp.therapeuticRegime.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(therapeuticRegimeComponentsPage.entities), ec.visibilityOf(therapeuticRegimeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TherapeuticRegime page', async () => {
    await therapeuticRegimeComponentsPage.clickOnCreateButton();
    therapeuticRegimeUpdatePage = new TherapeuticRegimeUpdatePage();
    expect(await therapeuticRegimeUpdatePage.getPageTitle()).to.eq('supportcareApp.therapeuticRegime.home.createOrEditLabel');
    await therapeuticRegimeUpdatePage.cancel();
  });

  it('should create and save TherapeuticRegimes', async () => {
    const nbButtonsBeforeCreate = await therapeuticRegimeComponentsPage.countDeleteButtons();

    await therapeuticRegimeComponentsPage.clickOnCreateButton();

    await promise.all([
      therapeuticRegimeUpdatePage.setTimingInput('timing'),
      therapeuticRegimeUpdatePage.setDietaryInput('dietary'),
      therapeuticRegimeUpdatePage.setSideEffectsInput('sideEffects'),
      therapeuticRegimeUpdatePage.setCreateUserInput('createUser'),
      therapeuticRegimeUpdatePage.setCreateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      therapeuticRegimeUpdatePage.setUpdateUserInput('updateUser'),
      therapeuticRegimeUpdatePage.setUpdateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      therapeuticRegimeUpdatePage.drugSelectLastOption(),
    ]);

    expect(await therapeuticRegimeUpdatePage.getTimingInput()).to.eq('timing', 'Expected Timing value to be equals to timing');
    expect(await therapeuticRegimeUpdatePage.getDietaryInput()).to.eq('dietary', 'Expected Dietary value to be equals to dietary');
    expect(await therapeuticRegimeUpdatePage.getSideEffectsInput()).to.eq(
      'sideEffects',
      'Expected SideEffects value to be equals to sideEffects'
    );
    expect(await therapeuticRegimeUpdatePage.getCreateUserInput()).to.eq(
      'createUser',
      'Expected CreateUser value to be equals to createUser'
    );
    expect(await therapeuticRegimeUpdatePage.getCreateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createDate value to be equals to 2000-12-31'
    );
    expect(await therapeuticRegimeUpdatePage.getUpdateUserInput()).to.eq(
      'updateUser',
      'Expected UpdateUser value to be equals to updateUser'
    );
    expect(await therapeuticRegimeUpdatePage.getUpdateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updateDate value to be equals to 2000-12-31'
    );

    await therapeuticRegimeUpdatePage.save();
    expect(await therapeuticRegimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await therapeuticRegimeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TherapeuticRegime', async () => {
    const nbButtonsBeforeDelete = await therapeuticRegimeComponentsPage.countDeleteButtons();
    await therapeuticRegimeComponentsPage.clickOnLastDeleteButton();

    therapeuticRegimeDeleteDialog = new TherapeuticRegimeDeleteDialog();
    expect(await therapeuticRegimeDeleteDialog.getDialogTitle()).to.eq('supportcareApp.therapeuticRegime.delete.question');
    await therapeuticRegimeDeleteDialog.clickOnConfirmButton();

    expect(await therapeuticRegimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
