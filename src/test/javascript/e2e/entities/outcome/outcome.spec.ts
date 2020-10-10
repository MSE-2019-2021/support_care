import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OutcomeComponentsPage, OutcomeDeleteDialog, OutcomeUpdatePage } from './outcome.page-object';

const expect = chai.expect;

describe('Outcome e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let outcomeComponentsPage: OutcomeComponentsPage;
  let outcomeUpdatePage: OutcomeUpdatePage;
  let outcomeDeleteDialog: OutcomeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Outcomes', async () => {
    await navBarPage.goToEntity('outcome');
    outcomeComponentsPage = new OutcomeComponentsPage();
    await browser.wait(ec.visibilityOf(outcomeComponentsPage.title), 5000);
    expect(await outcomeComponentsPage.getTitle()).to.eq('supportcareApp.outcome.home.title');
    await browser.wait(ec.or(ec.visibilityOf(outcomeComponentsPage.entities), ec.visibilityOf(outcomeComponentsPage.noResult)), 1000);
  });

  it('should load create Outcome page', async () => {
    await outcomeComponentsPage.clickOnCreateButton();
    outcomeUpdatePage = new OutcomeUpdatePage();
    expect(await outcomeUpdatePage.getPageTitle()).to.eq('supportcareApp.outcome.home.createOrEditLabel');
    await outcomeUpdatePage.cancel();
  });

  it('should create and save Outcomes', async () => {
    const nbButtonsBeforeCreate = await outcomeComponentsPage.countDeleteButtons();

    await outcomeComponentsPage.clickOnCreateButton();

    await promise.all([
      outcomeUpdatePage.setDescriptionInput('description'),
      outcomeUpdatePage.setScoreInput('5'),
      outcomeUpdatePage.setCreateUserInput('createUser'),
      outcomeUpdatePage.setCreateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      outcomeUpdatePage.setUpdateUserInput('updateUser'),
      outcomeUpdatePage.setUpdateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await outcomeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await outcomeUpdatePage.getScoreInput()).to.eq('5', 'Expected score value to be equals to 5');
    expect(await outcomeUpdatePage.getCreateUserInput()).to.eq('createUser', 'Expected CreateUser value to be equals to createUser');
    expect(await outcomeUpdatePage.getCreateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createDate value to be equals to 2000-12-31'
    );
    expect(await outcomeUpdatePage.getUpdateUserInput()).to.eq('updateUser', 'Expected UpdateUser value to be equals to updateUser');
    expect(await outcomeUpdatePage.getUpdateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updateDate value to be equals to 2000-12-31'
    );

    await outcomeUpdatePage.save();
    expect(await outcomeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await outcomeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Outcome', async () => {
    const nbButtonsBeforeDelete = await outcomeComponentsPage.countDeleteButtons();
    await outcomeComponentsPage.clickOnLastDeleteButton();

    outcomeDeleteDialog = new OutcomeDeleteDialog();
    expect(await outcomeDeleteDialog.getDialogTitle()).to.eq('supportcareApp.outcome.delete.question');
    await outcomeDeleteDialog.clickOnConfirmButton();

    expect(await outcomeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
