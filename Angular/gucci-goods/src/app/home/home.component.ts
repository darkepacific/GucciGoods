import { Component, OnInit, OnDestroy } from '@angular/core';
import { Item } from '../shared/items/item';
import { SearchService } from '../shared/search/search.service';
import { ItemService } from '../shared/items/item.service';
import 'rxjs/add/observable/interval';
import { Observable } from 'rxjs';
import { timer } from 'rxjs';
import 'rxjs/add/observable/timer';
import { ItemsmallComponent } from '../itemsmall/itemsmall.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private timer;
  private flag: boolean;
  public items: Item[] = this.itemService.getStored();
  public item : Item;
  public item2 : Item;
  public item3 : Item;

  constructor(private searchService: SearchService, private itemService: ItemService) { }

  ngOnInit() {
    this.getItem("2");
    this.getItem2("5");
    this.getItem3("1");
    this.flag = true;
    this.items = this.itemService.getStored();
    this.timer = Observable.timer(0,55);
    this.timer.subscribe((t) => this.onTimeOut());
  }

  ngOnDestroy(): void {
    this.flag = false;
  }

  onTimeOut() {
    if(this.flag){
      this.items = this.itemService.getStored();
      if(this.items == undefined || this.items.length == 0){
        if(this.item3 == undefined){
          this.getItem("2");
          this.getItem2("5");
          this.getItem3("1");
        }
       
      }
      else{
        //this.item = this.items[0];
        //let tempId = this.items[0].id as any;
        //this.setItem(tempId as string);
      }
      
    }
  }

  printStored(){
    console.log("THIS " + this.items);
  }

  receiveResult($event){
    this.items = $event;
    console.log("THIS " + this.items + "Test");
  }

  setItem(id: string): void {
    console.log(id);
    this.itemService.setCurrentItem(id);
  }

 getItem(id: string): void {
    this.itemService.getItem(id).subscribe(
      data=> {
        this.item = data;
        console.log(data);
        }
    )
    
  }

  getItem2(id: string): void {
    this.itemService.getItem(id).subscribe(
      data=> {
        this.item2 = data;
        console.log(data);
        }
    )
    
  }

  getItem3(id: string): void {
    this.itemService.getItem(id).subscribe(
      data=> {
        this.item3 = data;
        console.log(data);
        }
    )
    
  }
  

}
