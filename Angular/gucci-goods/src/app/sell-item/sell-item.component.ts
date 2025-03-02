import { Component, OnInit, Output, EventEmitter } from '@angular/core';
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
  @Output() itemAdded = new EventEmitter<void>();
  
  constructor(private ItemService: ItemService, private AccountService: AccountService) { }

  ngOnInit() {
    this.ItemService.sell(null,null, null, null, null, null, null, null).subscribe(
      Item=> {
        this.sellItem = Item;
      }
    )
  }

  postItem(): void {
    console.log("Original Inputs:", this.name, this.tags, this.cost, this.image, this.description);

    this.id = 1;
    let thisaccount = this.AccountService.getAccount();
    this.accountid = thisaccount['id'];
    this.sold = 0;

    // 🔹 Sanitize all fields
    this.name = this.sanitizeInput(this.name);
    this.tags = this.sanitizeInput(this.tags);
    this.description = encodeURIComponent(this.sanitizeInput(this.description)); // 🔹 Encode special characters
    this.image = this.sanitizeInput(this.image);
    this.cost = this.sanitizeCost(this.cost.toString()); // Ensure cost is a number

    console.log("Sanitized & Encoded Inputs:", this.name, this.tags, this.cost, this.image, this.description);

    this.ItemService.sell(this.id, this.accountid, this.name, this.description, this.tags, this.cost, this.image, this.sold)
    .subscribe(item => {
        this.sellItem = item;

        if (this.sellItem !== null) {
            console.log(this.sellItem);
            console.log(item);
            
            if (this.sellItem.id == null) {
                alert("Item NOT added.");
            } else {
                alert("Cash Money. Item added.");
                this.itemAdded.emit();  // 🔹 Notify parent component to refresh items
                this.toggleCollapse();
            } 
        }
    });
  }

  toggleCollapse(){
    this.isCollapsed = !this.isCollapsed;
  }
  
  sanitizeInput(input: string): string {
    if (!input) return ""; // Handle empty input gracefully
    return input.replace("&", "and").trim();
  }


  sanitizeCost(cost: string): number {
    if (!cost) return 0; // Default to 0 if empty
    return parseFloat(cost.replace(/[^0-9.]/g, "")); // Remove all non-numeric except .
  }

}
