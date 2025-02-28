import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from './shared/accounts/account.service';
import { Account } from './shared/accounts/account';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'gucci-goods';
  public account: Account | null = null;

  constructor(
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit() {
    // 🔹 Subscribe to account$ to automatically update UI when login state changes
    this.accountService.account$.subscribe(account => {
      this.account = account;
    });
  }

  logout(): void {
    this.accountService.logout().subscribe(() => {
      this.router.navigate(['/home']); // 🔹 Redirect on logout
    });
  }
}
