import { element, by, ElementFinder } from 'protractor';

export class ProtocolComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-protocol div table .btn-danger'));
  title = element.all(by.css('jhi-protocol div h2#page-heading span')).first();
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

export class ProtocolUpdatePage {
  pageTitle = element(by.id('jhi-protocol-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  toxicityDiagnosisInput = element(by.id('field_toxicityDiagnosis'));
  createUserInput = element(by.id('field_createUser'));
  createDateInput = element(by.id('field_createDate'));
  updateUserInput = element(by.id('field_updateUser'));
  updateDateInput = element(by.id('field_updateDate'));

  therapeuticRegimeSelect = element(by.id('field_therapeuticRegime'));
  outcomeSelect = element(by.id('field_outcome'));
  guideSelect = element(by.id('field_guide'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setToxicityDiagnosisInput(toxicityDiagnosis: string): Promise<void> {
    await this.toxicityDiagnosisInput.sendKeys(toxicityDiagnosis);
  }

  async getToxicityDiagnosisInput(): Promise<string> {
    return await this.toxicityDiagnosisInput.getAttribute('value');
  }

  async setCreateUserInput(createUser: string): Promise<void> {
    await this.createUserInput.sendKeys(createUser);
  }

  async getCreateUserInput(): Promise<string> {
    return await this.createUserInput.getAttribute('value');
  }

  async setCreateDateInput(createDate: string): Promise<void> {
    await this.createDateInput.sendKeys(createDate);
  }

  async getCreateDateInput(): Promise<string> {
    return await this.createDateInput.getAttribute('value');
  }

  async setUpdateUserInput(updateUser: string): Promise<void> {
    await this.updateUserInput.sendKeys(updateUser);
  }

  async getUpdateUserInput(): Promise<string> {
    return await this.updateUserInput.getAttribute('value');
  }

  async setUpdateDateInput(updateDate: string): Promise<void> {
    await this.updateDateInput.sendKeys(updateDate);
  }

  async getUpdateDateInput(): Promise<string> {
    return await this.updateDateInput.getAttribute('value');
  }

  async therapeuticRegimeSelectLastOption(): Promise<void> {
    await this.therapeuticRegimeSelect.all(by.tagName('option')).last().click();
  }

  async therapeuticRegimeSelectOption(option: string): Promise<void> {
    await this.therapeuticRegimeSelect.sendKeys(option);
  }

  getTherapeuticRegimeSelect(): ElementFinder {
    return this.therapeuticRegimeSelect;
  }

  async getTherapeuticRegimeSelectedOption(): Promise<string> {
    return await this.therapeuticRegimeSelect.element(by.css('option:checked')).getText();
  }

  async outcomeSelectLastOption(): Promise<void> {
    await this.outcomeSelect.all(by.tagName('option')).last().click();
  }

  async outcomeSelectOption(option: string): Promise<void> {
    await this.outcomeSelect.sendKeys(option);
  }

  getOutcomeSelect(): ElementFinder {
    return this.outcomeSelect;
  }

  async getOutcomeSelectedOption(): Promise<string> {
    return await this.outcomeSelect.element(by.css('option:checked')).getText();
  }

  async guideSelectLastOption(): Promise<void> {
    await this.guideSelect.all(by.tagName('option')).last().click();
  }

  async guideSelectOption(option: string): Promise<void> {
    await this.guideSelect.sendKeys(option);
  }

  getGuideSelect(): ElementFinder {
    return this.guideSelect;
  }

  async getGuideSelectedOption(): Promise<string> {
    return await this.guideSelect.element(by.css('option:checked')).getText();
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

export class ProtocolDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-protocol-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-protocol'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
