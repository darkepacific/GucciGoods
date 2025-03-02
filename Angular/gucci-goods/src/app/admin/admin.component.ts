import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/shared/accounts/account.service';
import { Account } from '../shared/accounts/account';
import { ItemService } from '../shared/items/item.service';
import { Item } from '../shared/items/item';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public loggedAccount: Account | null = null;
  public accountId: string;
  public accs: Account[];
  public items: Item[];
  public itemId: string;

  constructor(private accountService: AccountService, private itemService: ItemService) { 

  }

  ngOnInit() {
    this.getAccounts();
    this.getItems();
    this.loggedAccount = this.accountService.getAccount();
  }

  getAccounts(): void {
    this.accountService.getAccounts().subscribe(
      data=> {
        this.accs = data;
        console.log(data);
        }
    )
  }

  getItems(): void {
    this.itemService.getAllItems().subscribe(
      items=> {
        this.items = items;
        console.log(items);
        }
    )
  }

  deleteAccount(): void {
    console.log("deleting account "+this.accountId)
    this.accountService.deleteAccount(this.accountId).subscribe(
      suc => {
        this.accs.filter(acc => acc.id)
      }
    );
  }

  deleteItem(): void {
    console.log("deleting item "+this.itemId)
    this.itemService.deleteItem(this.itemId).subscribe(
      suc => {

        this.items.filter(item => item.id)
      }
    );
  }
  
  setItem(id: string): void {
      console.log(id);
      this.itemService.setCurrentItem(id);
  }

}
