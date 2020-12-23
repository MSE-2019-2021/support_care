import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'custom-user-info',
  templateUrl: './user-info.component.html',
})
export class UserInfoComponent implements OnInit {
  account!: Account;
  languages = LANGUAGES;
  settingsForm = this.fb.group({
    firstName: [undefined],
    lastName: [undefined],
    email: [undefined],
    langKey: [undefined],
  });

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private fb: FormBuilder,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email,
          langKey: account.langKey,
        });

        this.account = account;
      }
    });
  }
}
