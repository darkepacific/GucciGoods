import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/shared/items/item';
import { ItemService } from 'src/app/shared/items/item.service';
import { AccountService } from 'src/app/shared/accounts/account.service';
import { Account } from 'src/app/shared/accounts/account';
import { ItemReviewService } from 'src/app/shared/ItemReview/ItemReview.service';
import { ItemReview } from 'src/app/shared/ItemReview/ItemReview';


@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  public item: Item;
  public id: string;
  public quantity: number;
  public ItemReview : string;
  public account: Account;
  public sellerAccount: Account;
  public accountid: number;
  public getItemReviews: ItemReview[] = [];

  constructor(private itemService: ItemService, private accountService: AccountService, private itemReviewService: ItemReviewService) { }

  ngOnInit() {
    
    this.id = this.itemService.getCurrentItem();
    this.item = new Item();
    this.getItem(this.id);
    this.setItem(this.id);
    this.getReviews();
    this.quantity = 1;
    
    this.account = this.accountService.getAccount();
    
    // this.setItem(this.id);
    console.log(this.item);
  }

  getItem(id: string): void {
    this.itemService.getItem(id).subscribe(
      data=> {
        this.item = data;
        console.log(data);
        }
    )
    
  }

  // setItem(id: string): void {
  //   this.itemService.getItem(this.id).subscribe(
  //     data=> {
  //       this.item = data;
  //       console.log(data);
  //       }
  //   )
  // }

 setItem(id: string): void {
    this.itemService.setCurrentItem(id);
  }

  getSellerProfile() {
    console.log(this.item);
    this.accountService.getSellerAccount(this.item['seller']['id'].toString()).subscribe(
      data=> {
        this.sellerAccount = data;
        
      }
    );
  }

  buyItem(): void {
    if(!this.quantity){
      this.quantity = 1;
    }
    this.itemService.buyItem(this.item['id'], this.quantity, this.item['name']).subscribe(
      data=> {
        this.item = data;
        if(!this.item){
          alert("Not enough in stock");
        }
        console.log(data);
        }
    )
  }

  getReviews(){
    console.log("In reviews");
    this.itemReviewService.getReviews(this.id).subscribe(
      reviews=> {
        this.getItemReviews = reviews;
        console.log(reviews);
      }
    )
  }

  postReview(){ //accountid, itemid, description
    console.log(this.ItemReview); 
    if(!this.account){
      this.accountid = 1;
    }else{
      this.accountid = this.account.id;
    }
    this.itemReviewService.makeReview(this.accountid, this.item.id, this.ItemReview).subscribe(
      newlist=> {
        this.getItemReviews = newlist;
        console.log(newlist);
        this.ItemReview = "";
      }
    )
  }
  
}
