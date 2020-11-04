import { element, by, ElementFinder } from 'protractor';

export class TherapeuticRegimeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-therapeutic-regime div table .btn-danger'));
  title = element.all(by.css('jhi-therapeutic-regime div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TherapeuticRegimeUpdatePage {
  pageTitle = element(by.id('jhi-therapeutic-regime-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  acronymInput = element(by.id('field_acronym'));
  purposeInput = element(by.id('field_purpose'));
  conditionInput = element(by.id('field_condition'));
  timingInput = element(by.id('field_timing'));
  indicationInput = element(by.id('field_indication'));
  criteriaInput = element(by.id('field_criteria'));
  noticeInput = element(by.id('field_notice'));

  drugSelect = element(by.id('field_drug'));
  treatmentSelect = element(by.id('field_treatment'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setAcronymInput(acronym: string): Promise<void> {
    await this.acronymInput.sendKeys(acronym);
  }

  async getAcronymInput(): Promise<string> {
    return await this.acronymInput.getAttribute('value');
  }

  async setPurposeInput(purpose: string): Promise<void> {
    await this.purposeInput.sendKeys(purpose);
  }

  async getPurposeInput(): Promise<string> {
    return await this.purposeInput.getAttribute('value');
  }

  async setConditionInput(condition: string): Promise<void> {
    await this.conditionInput.sendKeys(condition);
  }

  async getConditionInput(): Promise<string> {
    return await this.conditionInput.getAttribute('value');
  }

  async setTimingInput(timing: string): Promise<void> {
    await this.timingInput.sendKeys(timing);
  }

  async getTimingInput(): Promise<string> {
    return await this.timingInput.getAttribute('value');
  }

  async setIndicationInput(indication: string): Promise<void> {
    await this.indicationInput.sendKeys(indication);
  }

  async getIndicationInput(): Promise<string> {
    return await this.indicationInput.getAttribute('value');
  }

  async setCriteriaInput(criteria: string): Promise<void> {
    await this.criteriaInput.sendKeys(criteria);
  }

  async getCriteriaInput(): Promise<string> {
    return await this.criteriaInput.getAttribute('value');
  }

  async setNoticeInput(notice: string): Promise<void> {
    await this.noticeInput.sendKeys(notice);
  }

  async getNoticeInput(): Promise<string> {
    return await this.noticeInput.getAttribute('value');
  }

  async drugSelectLastOption(): Promise<void> {
    await this.drugSelect.all(by.tagName('option')).last().click();
  }

  async drugSelectOption(option: string): Promise<void> {
    await this.drugSelect.sendKeys(option);
  }

  getDrugSelect(): ElementFinder {
    return this.drugSelect;
  }

  async getDrugSelectedOption(): Promise<string> {
    return await this.drugSelect.element(by.css('option:checked')).getText();
  }

  async treatmentSelectLastOption(): Promise<void> {
    await this.treatmentSelect.all(by.tagName('option')).last().click();
  }

  async treatmentSelectOption(option: string): Promise<void> {
    await this.treatmentSelect.sendKeys(option);
  }

  getTreatmentSelect(): ElementFinder {
    return this.treatmentSelect;
  }

  async getTreatmentSelectedOption(): Promise<string> {
    return await this.treatmentSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TherapeuticRegimeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-therapeuticRegime-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-therapeuticRegime'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
