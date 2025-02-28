import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AccountService } from 'src/app/shared/accounts/account.service';
import { Account } from '../shared/accounts/account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  public loggedAccount: Account | null = null;
  public username: string = '';
  public password: string = '';

  constructor(private accountService: AccountService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    // Check if the account is already set in AccountService
    if (this.accountService.isLoggedIn()) {
      this.loggedAccount = this.accountService.getAccount();
    } else {
      // Attempt to fetch the currently logged-in user from the server
      this.accountService.login('', '').subscribe(account => {
        if (account && account.username) {
          this.loggedAccount = account;
          this.cdr.detectChanges(); // Ensure UI updates
        }
      });
    }
  }
  

  login(): void {
    this.accountService.login(this.username, this.password).subscribe(account => {
      if (account && account.username) {
        this.loggedAccount = account;
        this.cdr.detectChanges();
      }
    });
  }

  logout(): void {
    this.accountService.logout().subscribe(() => {
      this.loggedAccount = null;
      this.username = '';
      this.password = '';
      this.cdr.detectChanges();
    });
  }
}
