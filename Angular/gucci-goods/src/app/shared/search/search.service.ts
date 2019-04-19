import { Injectable } from '@angular/core';

import { UrlService } from '../url.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, pipe, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Account } from '../accounts/account';
import { Item } from '../items/item';
import { BehaviorSubject } from 'rxjs/BehaviorSubject'

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
  private appUrl = this.urlSource.getURL() + '/item';

  //private results = new BehaviorSubject<Item[]>(null);
  //currentResult = this.results.asObservable(); 
  private results: Item[];

  
  constructor(private urlSource: UrlService, private http: HttpClient) { }

  search(query:string): Observable<Item[]> {
    const body = `query=${query}`;
    console.log(body);
    return this.http.post(this.appUrl + '/search', body, {headers: this.headers, withCredentials: true})
    .pipe(map(resp => {

      const items: Item[] = resp as Item[];

      this.results = items;
      console.log("Items" + this.results);
      return items;
    }));
  }

  getResults(): Item[]{
    return this.results;
  }
  

   /*search(query:string) {
    const body = `query=${query}`;
    console.log(body);
    return this.http.post(this.appUrl + '/search', body, {headers: this.headers, withCredentials: true})
    .pipe(map(resp => {

      const items: Item[] = resp as Item[];
      console.log("IT GOT HERE");
      this.results.next(items);
      console.log("Items" + this.results);
    }));
  }*/
}
