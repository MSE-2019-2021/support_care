import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

jest.mock('@angular/router');
jest.mock('ngx-webstorage');
jest.mock('ng-jhipster');
jest.mock('app/core/auth/account.service');
jest.mock('app/login/login.service');

import { ComponentFixture, fakeAsync, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { SessionStorageService } from 'ngx-webstorage';
import { JhiLanguageService } from 'ng-jhipster';
import { MockNgbModalRef } from '../../entities/therapeutic-regime/therapeutic-regime-update.component.spec';

import { ProfileInfo } from 'app/layouts/profiles/profile-info.model';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { LoginService } from 'app/login/login.service';
import { NavbarLogoutDialogComponent } from 'app/layouts/navbar/navbar-logout-dialog.component';

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
          declarations: [NavbarComponent, NavbarLogoutDialogComponent],
          providers: [AccountService, SessionStorageService, JhiLanguageService, Router, LoginService],
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

    describe('logout', () => {
      it('should open modal', fakeAsync(() => {
        // GIVEN
        spyOn(modalService, 'open').and.returnValue(mockModalRef);

        // WHEN
        comp.logout();
        tick(); // simulate async

        // THEN
        expect(modalService.open).toHaveBeenCalledWith(NavbarLogoutDialogComponent, {
          centered: true,
          size: 'md',
          backdrop: 'static',
        });
      }));
    });
  });
});
