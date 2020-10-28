import { element, by, ElementFinder } from 'protractor';

export class DrugComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-drug div table .btn-danger'));
  title = element.all(by.css('jhi-drug div h2#page-heading span')).first();
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

export class DrugUpdatePage {
  pageTitle = element(by.id('jhi-drug-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));

  administrationSelect = element(by.id('field_administration'));
  therapeuticRegimeSelect = element(by.id('field_therapeuticRegime'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async administrationSelectLastOption(): Promise<void> {
    await this.administrationSelect.all(by.tagName('option')).last().click();
  }

  async administrationSelectOption(option: string): Promise<void> {
    await this.administrationSelect.sendKeys(option);
  }

  getAdministrationSelect(): ElementFinder {
    return this.administrationSelect;
  }

  async getAdministrationSelectedOption(): Promise<string> {
    return await this.administrationSelect.element(by.css('option:checked')).getText();
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

export class DrugDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-drug-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-drug'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}