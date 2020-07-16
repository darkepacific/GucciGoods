import { Component, OnInit, Input, HostBinding } from '@angular/core';
import { Account } from '../shared/accounts/account';
import { LoginComponent } from '../login/login.component';
import { AccountService } from '../shared/accounts/account.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-seller-profile',
  templateUrl: './seller-profile.component.html',
  styleUrls: ['./seller-profile.component.css']
})
export class SellerProfileComponent implements OnInit {

  public account: Account;
  public loggedAccount: LoginComponent;
  public email: string;
  public location:string;
  public phone:string;
  public description: string;
  public sellerAccount: Account;

  emailCollapsed: boolean = true;
  locationCollapsed:boolean = true;
  phoneCollapsed:boolean = true;
  descriptionCollapsed:boolean = true;

  constructor(private accountService: AccountService, private router: Router) { }

  ngOnInit() {
    this.account = this.accountService.getAccount();
    console.log(this.account);
    this.email = this.account.email;
    this.location = this.account.location;
    this.phone = this.account.phone;
    this.description = this.account.description;
    this.sellerAccount = this.accountService.getSeller();
    console.log(this.sellerAccount);
    
    if(this.sellerAccount){
      console.log("this is not your profile");
      console.log(this.sellerAccount);
      this.account = this.sellerAccount;
      this.email = this.sellerAccount.email;
      this.location = this.sellerAccount.location;
      this.phone = "XXX-XXXX" + this.sellerAccount.phone.substring(7,12);
      this.description = this.sellerAccount.description;
      this.accountService.clearSeller();
    }

  }

}
