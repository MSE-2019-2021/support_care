import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GuideComponentsPage, GuideDeleteDialog, GuideUpdatePage } from './guide.page-object';

const expect = chai.expect;

describe('Guide e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let guideComponentsPage: GuideComponentsPage;
  let guideUpdatePage: GuideUpdatePage;
  let guideDeleteDialog: GuideDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Guides', async () => {
    await navBarPage.goToEntity('guide');
    guideComponentsPage = new GuideComponentsPage();
    await browser.wait(ec.visibilityOf(guideComponentsPage.title), 5000);
    expect(await guideComponentsPage.getTitle()).to.eq('supportcareApp.guide.home.title');
    await browser.wait(ec.or(ec.visibilityOf(guideComponentsPage.entities), ec.visibilityOf(guideComponentsPage.noResult)), 1000);
  });

  it('should load create Guide page', async () => {
    await guideComponentsPage.clickOnCreateButton();
    guideUpdatePage = new GuideUpdatePage();
    expect(await guideUpdatePage.getPageTitle()).to.eq('supportcareApp.guide.home.createOrEditLabel');
    await guideUpdatePage.cancel();
  });

  it('should create and save Guides', async () => {
    const nbButtonsBeforeCreate = await guideComponentsPage.countDeleteButtons();

    await guideComponentsPage.clickOnCreateButton();

    await promise.all([
      guideUpdatePage.setDescriptionInput('description'),
      guideUpdatePage.setCreateUserInput('createUser'),
      guideUpdatePage.setCreateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      guideUpdatePage.setUpdateUserInput('updateUser'),
      guideUpdatePage.setUpdateDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await guideUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await guideUpdatePage.getCreateUserInput()).to.eq('createUser', 'Expected CreateUser value to be equals to createUser');
    expect(await guideUpdatePage.getCreateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected createDate value to be equals to 2000-12-31'
    );
    expect(await guideUpdatePage.getUpdateUserInput()).to.eq('updateUser', 'Expected UpdateUser value to be equals to updateUser');
    expect(await guideUpdatePage.getUpdateDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected updateDate value to be equals to 2000-12-31'
    );

    await guideUpdatePage.save();
    expect(await guideUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await guideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Guide', async () => {
    const nbButtonsBeforeDelete = await guideComponentsPage.countDeleteButtons();
    await guideComponentsPage.clickOnLastDeleteButton();

    guideDeleteDialog = new GuideDeleteDialog();
    expect(await guideDeleteDialog.getDialogTitle()).to.eq('supportcareApp.guide.delete.question');
    await guideDeleteDialog.clickOnConfirmButton();

    expect(await guideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
