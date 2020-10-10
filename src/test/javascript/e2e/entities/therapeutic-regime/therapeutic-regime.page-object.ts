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

  timingInput = element(by.id('field_timing'));
  dietaryInput = element(by.id('field_dietary'));
  sideEffectsInput = element(by.id('field_sideEffects'));
  createUserInput = element(by.id('field_createUser'));
  createDateInput = element(by.id('field_createDate'));
  updateUserInput = element(by.id('field_updateUser'));
  updateDateInput = element(by.id('field_updateDate'));

  drugSelect = element(by.id('field_drug'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTimingInput(timing: string): Promise<void> {
    await this.timingInput.sendKeys(timing);
  }

  async getTimingInput(): Promise<string> {
    return await this.timingInput.getAttribute('value');
  }

  async setDietaryInput(dietary: string): Promise<void> {
    await this.dietaryInput.sendKeys(dietary);
  }

  async getDietaryInput(): Promise<string> {
    return await this.dietaryInput.getAttribute('value');
  }

  async setSideEffectsInput(sideEffects: string): Promise<void> {
    await this.sideEffectsInput.sendKeys(sideEffects);
  }

  async getSideEffectsInput(): Promise<string> {
    return await this.sideEffectsInput.getAttribute('value');
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
