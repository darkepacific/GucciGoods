import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { ItemService } from './shared/items/item.service';
import { AccountService } from './shared/accounts/account.service';
import { Account } from './shared/accounts/account';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnChanges {
  title = 'gucci-goods';
  public account: Account;
  public x: boolean;

  ngOnInit(){
    this.account = null;//this.accountService.getAccount();
    this.x = false;
  }

  constructor(private itemService: ItemService, private accountService: AccountService){
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.account = this.accountService.getAccount();
  }

  clear(): void{
    this.itemService.setStored(null);
  }
  
}