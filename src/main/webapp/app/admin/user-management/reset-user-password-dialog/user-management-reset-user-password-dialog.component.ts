import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  templateUrl: './user-management-reset-user-password-dialog.component.html',
})
export class UserManagementResetUserPasswordDialogComponent {
  user?: User;
  passwordForm = this.fb.group({
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
  });

  constructor(private userService: UserService, public activeModal: NgbActiveModal, private fb: FormBuilder) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmReset(user: IUser): void {
    user.password = this.passwordForm.get(['password'])!.value;
    this.userService.changePassword(user).subscribe(() => {
      this.activeModal.close('reset');
    });
  }
}
