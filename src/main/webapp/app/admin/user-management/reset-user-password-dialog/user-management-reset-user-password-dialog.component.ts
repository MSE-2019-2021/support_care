import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  templateUrl: './user-management-reset-user-password-dialog.component.html',
})
export class UserManagementResetUserPasswordDialogComponent {
  user?: User;

  constructor(private userService: UserService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmReset(user: IUser): void {
    user.password = '';
    this.userService.changePassword(user).subscribe(() => {
      this.activeModal.close('reset');
    });
  }
}
