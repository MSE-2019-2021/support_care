jest.mock('ng-jhipster');
jest.mock('app/core/auth/account.service');

import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { throwError, of } from 'rxjs';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { UserInfoComponent } from 'app/account/user-info/user-info.component';

describe('Component Tests', () => {
  describe('UserInfoComponent', () => {
    let comp: UserInfoComponent;
    let fixture: ComponentFixture<UserInfoComponent>;
    let mockAccountService: AccountService;
    const account: Account = {
      firstName: 'John',
      lastName: 'Doe',
      activated: true,
      email: 'john.doe@mail.com',
      langKey: 'pt-pt',
      login: 'john',
      authorities: [],
      imageUrl: '',
    };

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          declarations: [UserInfoComponent],
          providers: [FormBuilder, JhiLanguageService, AccountService],
        })
          .overrideTemplate(UserInfoComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(UserInfoComponent);
      comp = fixture.componentInstance;
      mockAccountService = TestBed.inject(AccountService);
      mockAccountService.identity = jest.fn(() => of(account));
      mockAccountService.getAuthenticationState = jest.fn(() => of(account));
    });

    it('should send the current identity when showing', () => {
      // GIVEN
      const settingsFormValues = {
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@mail.com',
        langKey: 'pt-pt',
      };

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(mockAccountService.identity).toHaveBeenCalled();
      expect(comp.settingsForm.value).toEqual(settingsFormValues);
    });
  });
});
