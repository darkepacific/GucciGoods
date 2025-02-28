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
  public loginError: string = '';  // <-- Add this property for error messages

  constructor(private accountService: AccountService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    if (this.accountService.isLoggedIn()) {
      this.loggedAccount = this.accountService.getAccount();
    } else {
      this.accountService.login('', '').subscribe(account => {
        if (account && account.username) {
          this.loggedAccount = account;
          this.cdr.detectChanges();
        }
      });
    }
  }

  login(): void {
    this.loginError = ''; // Reset error message before attempting login

    this.accountService.login(this.username, this.password).subscribe(
      account => {
        if (account && account.username) {
          this.loggedAccount = account;
          this.cdr.detectChanges();
        } else {
          this.loginError = 'Incorrect username or password.'; // Set error message if login fails
        }
      },
      error => {
        this.loginError = 'Incorrect username or password.'; // Handle API errors
      }
    );
  }

  logout(): void {
    this.accountService.logout().subscribe(() => {
      this.loggedAccount = null;
      this.username = '';
      this.password = '';
      this.loginError = ''; // Clear error on logout
      this.cdr.detectChanges();
    });
  }
}
