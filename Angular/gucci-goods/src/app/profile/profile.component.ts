import { Component, OnInit, Input, HostBinding } from '@angular/core';
import { Account } from '../shared/accounts/account';
import { LoginComponent } from '../login/login.component';
import { AccountService } from '../shared/accounts/account.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Item } from '../shared/items/item';
import { ItemService } from 'src/app/shared/items/item.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  public account: Account;
  public loggedAccount: LoginComponent;
  public email: string;
  public location:string;
  public phone:string;
  public description: string;
  public sellerAccount: Account;
  public avatar: string;
  public items: Item[];

  emailCollapsed: boolean = true;
  locationCollapsed:boolean = true;
  phoneCollapsed:boolean = true;
  descriptionCollapsed:boolean = true;
  avatarCollapsed: boolean = true;

  constructor(private accountService: AccountService, private router: Router, private pageLocation: Location, private itemService: ItemService) { }

  ngOnInit() {
    this.account = this.accountService.getAccount();
    console.log(this.account);
    
    if(!this.account){
      this.router.navigate(['/login']);
      this.account = new Account();
      
    }
    this.email = this.account.email;
    
    this.location = this.account.location;
    
    if(this.account['location']){
      this.location = this.account['location'];
    }

    this.phone = this.account.phone;
    this.description = this.account.description;
    this.sellerAccount = this.accountService.getSeller();
    this.avatar = this.account.avatar || '';
    console.log(this.sellerAccount);
    
    // Fetch user's items
    this.itemService.getItemsByUser(this.account.id).subscribe(
      data => {
        this.items = data;
        console.log("User's Items:", this.items);
      }
    );

    if(this.sellerAccount){
      console.log("this is not your profile");
      console.log(this.sellerAccount);
      this.account = this.sellerAccount;
      this.email = this.sellerAccount.email;
      this.location = this.sellerAccount.location;
      this.phone = this.sellerAccount.phone;
      this.description = this.sellerAccount.description;
      this.accountService.clearSeller();
    }

  }

  emailCollapse(){
    this.emailCollapsed = !this.emailCollapsed;
  }
  
  locationCollapse(){
    this.locationCollapsed = !this.locationCollapsed;
  }

  phoneCollapse(){
    this.phoneCollapsed = !this.phoneCollapsed;
  }

  descriptionCollapse(){
    this.descriptionCollapsed = !this.descriptionCollapsed;
  }

  emailSave(){
    console.log(this.email);
    this.account.email = this.email;
    this.accountService.updateEmail(this.account.email, this.account.id).subscribe(
      email=> {
        this.email = email.toString();
      }
    )
    this.emailCollapsed = !this.emailCollapsed;
  }

  locationSave(){
    console.log(this.location);
    this.account.location = this.location; 
    this.accountService.updateLocation(this.account.location, this.account.id).subscribe(
      $location=> {
        console.log($location.toString());
        this.location = $location.toString();
      }
    )
    this.locationCollapsed = !this.locationCollapsed;
  }

  phoneSave(){
    console.log(this.phone);
    this.account.phone = this.phone;
    this.accountService.updatePhone(this.account.phone, this.account.id).subscribe(
      phone=> {
        this.phone = phone.toString();
      }
    )
    this.phoneCollapsed = !this.phoneCollapsed;
  }

  descriptionSave(){
    console.log(this.description);
    this.account.description = this.description;
    this.accountService.updateDescription(this.account.description, this.account.id).subscribe(
      description=> {
        this.description = description.toString();
      }
    )
    this.descriptionCollapsed = !this.descriptionCollapsed;
  }

  avatarCollapse() {
    this.avatarCollapsed = !this.avatarCollapsed;
  }

  avatarSave() {
    console.log("New avatar URL:", this.avatar);
    this.accountService.updateAvatar(this.avatar, this.account.id).subscribe(
      avatar => {
        this.avatar = avatar;
      }
    );
    this.avatarCollapsed = true;
  }

  refreshItems(): void {
    console.log("Refreshing items...");
    this.itemService.getItemsByUser(this.account.id).subscribe(
      data => {
        this.items = data;
        console.log("Updated User's Items:", this.items);
      }
    );
  }
}