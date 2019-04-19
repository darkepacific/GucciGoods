import { Component, OnInit } from '@angular/core';
import { ItemService } from 'src/app/shared/items/item.service';
import { Item } from '../shared/items/item';
import { AccountService } from 'src/app/shared/accounts/account.service';
import { Account } from 'src/app/shared/accounts/account';

@Component({
  selector: 'app-sell-item',
  templateUrl: './sell-item.component.html',
  styleUrls: ['./sell-item.component.css']
})

export class SellItemComponent implements OnInit {
  isCollapsed: boolean = true;
  public id: number;
  public accountid: number;
  public name: string;
  public description: string;
  public tags: string;
  public cost: number;
  public image: string;
  public sold: number;
  public sellItem: Item;
  public userAccount: Account;
  
  constructor(private ItemService: ItemService, private AccountService: AccountService) { }

  ngOnInit() {
    this.ItemService.sell(null,null, null, null, null, null, null, null).subscribe(
      Item=> {
        this.sellItem = Item;
      }
    )
  }

  postItem(): void{
    console.log(this.name);
    this.id = 1;
    let thisaccount = this.AccountService.getAccount();
    this.accountid = thisaccount['id'];
    this.sold = 0;
    this.ItemService.sell(this.id, this.accountid, this.name, this.description, this.tags, this.cost, this.image, this.sold).subscribe(
      item=> {
        this.sellItem = item;
        if(this.sellItem !== null){
          console.log(this.sellItem);
          console.log(item);
          if(this.sellItem.id == null){
            alert("Item NOT added.");
          }
          else{
            alert("Cash Money. Item added.");
            this.toggleCollapse();
          } 
        }
      }
    )
  }

  toggleCollapse(){
    this.isCollapsed = !this.isCollapsed;
  }

}
