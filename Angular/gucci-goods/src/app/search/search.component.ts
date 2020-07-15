import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CompleterService, CompleterData } from 'ng2-completer';
import { SearchService } from '../shared/search/search.service';
import { Item } from '../shared/items/item';
import {Router} from '@angular/router';
import { ItemService } from '../shared/items/item.service';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})

export class SearchComponent implements OnInit {
  protected query: string;
  public result: Item[] = this.itemService.getStored();
  @Output() resultEvent = new EventEmitter<Item[]>();

  public commonItem: String[];
  commonItems = ["Acer", "Acer Laptop", "AirPods", "Amazon Kindle", "Amiibo", "Apple",
                  "Battery Pack", "Bluetooth Headphones", "Bluetooth Speakers",
                  "Car Phone Mount", "Charging Station", "Cologne", "Crocs", "Cup Holder",
                  "Dash Cam", "Desk Fan", "Disney Phone Case", "Drone",
                  "Earphones", "Echo", "Electric Toothbrush",
                  "Fashion Nova", "Fire Stick", "Fitbit", "Fortnite",
                  "Gift Card", "Glasses", "Gucci", "Gucci Belt",
                  "Harry Potter", "HDMI","Headphones", "Huawei", "Huawei Matebook",
                  "iPhone", "iPhone X", "iPhone 8", "iPhone Screen Protector",
                  "Jenga", "Journal", "Juicer", "Juul",
                  "Kate Spade", "Kindle", "Kindle Fire",
                  "Laptop", "Lego", "Lego Star Wars", "LG TV",
                  "Macbook", "Macbook Air", "Macbook Pro", "Marvel", "Mask", "Monster Hunter",
                  "Nike", "Nintendo Switch", "Nintendo Amiibo", "Note 9",
                  "Office Chair", "Ogx Shampoo", "Otterbox",
                  "Playstation", "Playstation Card", "Pokemon", "Polo",
                  "Qi Wireless Charger", "Queen Bed Frame",
                  "Roku", "Roku Premiere", "Rolex",
                  "Samsung Galaxy", "Spiderman", "Switch",
                  "Tablet", "TV", "TV Mount", "Thank You Cards",
                  "Umbrella", "USB", "Unicorn",
                  "Vans", "Versace", "Versace Cologne", "Victoria's Secret",
                  "Warcraft", "Water Bottle", "Wifi Router", "Wireless Earbuds", "Wyze Camera",
                  "Xbox", "Xbox Card", "Xbox One",
                  "Yankee Candle", "Yoga Mat", "Yeti Tumbler",
                  "Zero Gravity Chair","Zip Ties", "Zippo Lighter"];

  constructor(private completerService: CompleterService, private itemService: ItemService,
     private searchService: SearchService, private router: Router) { }

  ngOnInit() {
    //this.searchService.results.subscribe(results => this.result = results)
    //this.result = this.itemService.getStored();
    //console.log("PRINTING " + this.result);
  }

  search(): void{
    console.log(this.query);
    if(! (this.query == " " || this.query == "  ") ){
 
      this.searchService.search(this.query).subscribe(
        data=> {
          this.result = data;
          console.log(this.result);
          this.itemService.setStored(this.result);
          console.log("STORING:" + this.result);

          this.router.navigateByUrl('home');
          
          //this.resultEvent.emit(this.result);    
          }
      )      
    }
  }
  
  printStored(){
    console.log("THIS " + this.result);
  }
  
   /*search() {
    console.log(this.query)

    if(! (this.query == " " || this.query == "  ") ){
      this.searchService.search(this.query);
    }
    console.log(this.result);
  }*/

   //window.location.href = "http://localhost:4200/home"; 
}
     