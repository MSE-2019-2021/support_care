import { element, by, ElementFinder } from 'protractor';

export class NoticeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-notice div table .btn-danger'));
  title = element.all(by.css('jhi-notice div h2#page-heading span')).first();
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

export class NoticeUpdatePage {
  pageTitle = element(by.id('jhi-notice-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  evaluationInput = element(by.id('field_evaluation'));
  interventionInput = element(by.id('field_intervention'));

  drugSelect = element(by.id('field_drug'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setEvaluationInput(evaluation: string): Promise<void> {
    await this.evaluationInput.sendKeys(evaluation);
  }

  async getEvaluationInput(): Promise<string> {
    return await this.evaluationInput.getAttribute('value');
  }

  async setInterventionInput(intervention: string): Promise<void> {
    await this.interventionInput.sendKeys(intervention);
  }

  async getInterventionInput(): Promise<string> {
    return await this.interventionInput.getAttribute('value');
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

export class NoticeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notice-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notice'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
