import { Component, OnInit } from '@angular/core';
import { CurrentUser } from 'src/app/shared/accounts/current-user';
import { AccountService } from 'src/app/shared/accounts/account.service';
import { Account } from '../shared/accounts/account';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  public loggedAccount: Account;
  public username: string;
  public password: string;

  constructor(private accountService: AccountService) { }

  ngOnInit() {
    this.accountService.login(null,null).subscribe(
      account=> {
        this.loggedAccount = account;
      }
    )
  }

  login(): void {
    this.accountService.login(this.username, this.password).subscribe(
      account=> {
        this.loggedAccount = account;
        if(this.loggedAccount !== null){
          this.loggedAccount.username = account['username'];
          console.log(this.loggedAccount.username);
          console.log(account);
        }
      }
    )
  }

  logout(): void {
    this.accountService.logout().subscribe();
    this.loggedAccount=null;
    this.username=null;
    this.password=null;
  }

  // @HostListener('click')
  sendAccount(): void{
    
   }
}
