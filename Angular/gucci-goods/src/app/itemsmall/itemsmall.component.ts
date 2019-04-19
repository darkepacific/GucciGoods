import { Component, Input, OnInit } from '@angular/core';
import { Item } from '../shared/items/item';
import { ItemService } from '../shared/items/item.service';

@Component({
  selector: 'app-itemsmall',
  templateUrl: './itemsmall.component.html',
  styleUrls: ['./itemsmall.component.css']
})
export class ItemsmallComponent implements OnInit {
  //public item: Item = null;
  public id: string = "2";

  @Input() item2: Item;

  constructor(private itemService: ItemService) { }

  ngOnInit() {
    //this.id = this.itemService.getCurrentItem();
    //this.getItem(this.id);
  }

  // getItem(id: string): void {
  //   this.itemService.getItem(id).subscribe(
  //     data=> {
  //       this.item = data;
  //       console.log(data);
  //       }
  //   )
  // }
}
