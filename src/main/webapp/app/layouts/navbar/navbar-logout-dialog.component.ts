import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService } from 'app/login/login.service';
import { Router } from '@angular/router';

@Component({
  templateUrl: './navbar-logout-dialog.component.html',
  styleUrls: ['navbar.scss'],
})
export class NavbarLogoutDialogComponent {
  constructor(
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private _location: Location,
    private loginService: LoginService,
    private router: Router
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  logout(): void {
    this.cancel();
    this.loginService.logout();
    this.router.navigate(['/']);
  }
}
