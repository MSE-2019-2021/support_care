jest.mock('@angular/router');
jest.mock('ngx-webstorage');
jest.mock('@ngx-translate/core');
jest.mock('app/core/auth/account.service');
jest.mock('app/login/login.service');

import { ComponentFixture, fakeAsync, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { SessionStorageService } from 'ngx-webstorage';
import { TranslateService } from '@ngx-translate/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ProfileInfo } from 'app/layouts/profiles/profile-info.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { LoginService } from 'app/login/login.service';

import { NavbarComponent } from './navbar.component';
import { NavbarLogoutDialogComponent } from './logout/navbar-logout-dialog.component';

export class MockNgbModalRef {
  componentInstance = {
    prompt: undefined,
    title: undefined,
  };
  result: Promise<any> = new Promise(resolve => resolve(true));
}

describe('Component Tests', () => {
  describe('Navbar Component', () => {
    let comp: NavbarComponent;
    let fixture: ComponentFixture<NavbarComponent>;
    let mockAccountService: AccountService;
    let profileService: ProfileService;
    let modalService: NgbModal;
    let mockModalRef: MockNgbModalRef;

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [NavbarComponent],
          providers: [AccountService, SessionStorageService, TranslateService, Router, LoginService],
        })
          .overrideTemplate(NavbarComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(NavbarComponent);
      comp = fixture.componentInstance;
      mockAccountService = TestBed.inject(AccountService);
      profileService = TestBed.inject(ProfileService);
      modalService = TestBed.inject(NgbModal);
      mockModalRef = new MockNgbModalRef();
    });

    it('Should call profileService.getProfileInfo on init', () => {
      // GIVEN
      spyOn(profileService, 'getProfileInfo').and.returnValue(of(new ProfileInfo()));

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(profileService.getProfileInfo).toHaveBeenCalled();
    });

    it('Should call accountService.isAuthenticated on authentication', () => {
      // WHEN
      comp.isAuthenticated();

      // THEN
      expect(mockAccountService.isAuthenticated).toHaveBeenCalled();
    });
  });
});
