import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ItemService } from './shared/items/item.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'gucci-goods';

  constructor(private itemService: ItemService){}

  clear(): void{
    this.itemService.setStored(null);
  }
  
}