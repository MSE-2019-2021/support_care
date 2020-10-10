import { element, by, ElementFinder } from 'protractor';

export class GuideComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-guide div table .btn-danger'));
  title = element.all(by.css('jhi-guide div h2#page-heading span')).first();
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

export class GuideUpdatePage {
  pageTitle = element(by.id('jhi-guide-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  createUserInput = element(by.id('field_createUser'));
  createDateInput = element(by.id('field_createDate'));
  updateUserInput = element(by.id('field_updateUser'));
  updateDateInput = element(by.id('field_updateDate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
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

export class GuideDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-guide-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-guide'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
