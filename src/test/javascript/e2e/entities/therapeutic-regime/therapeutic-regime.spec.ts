import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TherapeuticRegimeComponentsPage,
  /* TherapeuticRegimeDeleteDialog, */
  TherapeuticRegimeUpdatePage,
} from './therapeutic-regime.page-object';

const expect = chai.expect;

describe('TherapeuticRegime e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let therapeuticRegimeComponentsPage: TherapeuticRegimeComponentsPage;
  let therapeuticRegimeUpdatePage: TherapeuticRegimeUpdatePage;
  /* let therapeuticRegimeDeleteDialog: TherapeuticRegimeDeleteDialog; */

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
    expect(await therapeuticRegimeComponentsPage.getTitle()).to.eq('supportivecareApp.therapeuticRegime.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(therapeuticRegimeComponentsPage.entities), ec.visibilityOf(therapeuticRegimeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TherapeuticRegime page', async () => {
    await therapeuticRegimeComponentsPage.clickOnCreateButton();
    therapeuticRegimeUpdatePage = new TherapeuticRegimeUpdatePage();
    expect(await therapeuticRegimeUpdatePage.getPageTitle()).to.eq('supportivecareApp.therapeuticRegime.home.createOrEditLabel');
    await therapeuticRegimeUpdatePage.cancel();
  });

  /* it('should create and save TherapeuticRegimes', async () => {
        const nbButtonsBeforeCreate = await therapeuticRegimeComponentsPage.countDeleteButtons();

        await therapeuticRegimeComponentsPage.clickOnCreateButton();

        await promise.all([
            therapeuticRegimeUpdatePage.setNameInput('name'),
            therapeuticRegimeUpdatePage.setAcronymInput('acronym'),
            therapeuticRegimeUpdatePage.setPurposeInput('purpose'),
            therapeuticRegimeUpdatePage.setConditionInput('condition'),
            therapeuticRegimeUpdatePage.setTimingInput('timing'),
            therapeuticRegimeUpdatePage.setIndicationInput('indication'),
            therapeuticRegimeUpdatePage.setCriteriaInput('criteria'),
            therapeuticRegimeUpdatePage.setNoticeInput('notice'),
            // therapeuticRegimeUpdatePage.drugSelectLastOption(),
            therapeuticRegimeUpdatePage.treatmentSelectLastOption(),
        ]);

        expect(await therapeuticRegimeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
        expect(await therapeuticRegimeUpdatePage.getAcronymInput()).to.eq('acronym', 'Expected Acronym value to be equals to acronym');
        expect(await therapeuticRegimeUpdatePage.getPurposeInput()).to.eq('purpose', 'Expected Purpose value to be equals to purpose');
        expect(await therapeuticRegimeUpdatePage.getConditionInput()).to.eq('condition', 'Expected Condition value to be equals to condition');
        expect(await therapeuticRegimeUpdatePage.getTimingInput()).to.eq('timing', 'Expected Timing value to be equals to timing');
        expect(await therapeuticRegimeUpdatePage.getIndicationInput()).to.eq('indication', 'Expected Indication value to be equals to indication');
        expect(await therapeuticRegimeUpdatePage.getCriteriaInput()).to.eq('criteria', 'Expected Criteria value to be equals to criteria');
        expect(await therapeuticRegimeUpdatePage.getNoticeInput()).to.eq('notice', 'Expected Notice value to be equals to notice');

        await therapeuticRegimeUpdatePage.save();
        expect(await therapeuticRegimeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await therapeuticRegimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last TherapeuticRegime', async () => {
        const nbButtonsBeforeDelete = await therapeuticRegimeComponentsPage.countDeleteButtons();
        await therapeuticRegimeComponentsPage.clickOnLastDeleteButton();

        therapeuticRegimeDeleteDialog = new TherapeuticRegimeDeleteDialog();
        expect(await therapeuticRegimeDeleteDialog.getDialogTitle())
            .to.eq('supportivecareApp.therapeuticRegime.delete.question');
        await therapeuticRegimeDeleteDialog.clickOnConfirmButton();

        expect(await therapeuticRegimeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
