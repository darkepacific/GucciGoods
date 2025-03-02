import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Item } from '../shared/items/item';
import { ItemService } from '../shared/items/item.service';
import { SearchService } from '../shared/search/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  public items: Item[] = [];
  public query: string = '';

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private searchService: SearchService
  ) {}

  ngOnInit() {
    // 1) Read from itemService by default
    this.items = this.itemService.getStored() || [];

    // 2) Optionally read query param & re-run search
    this.route.queryParamMap.subscribe(params => {
      const q = params.get('q') || '';
      this.query = q.trim();

      // If user reloaded the page on /search?q=..., we can re-fetch
      if (this.query) {
        this.searchService.search(this.query).subscribe(data => {
          this.items = data;
          this.itemService.setStored(data);
        });
      }
    });
  }

  setItem(id: string): void {
    // If you need to store the "current item" for detail page
    this.itemService.setCurrentItem(id);
  }
}
