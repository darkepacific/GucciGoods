import { Injectable } from '@angular/core';

import { UrlService } from '../url.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, pipe, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Account } from '../accounts/account';
import { Item } from './item';
import { BehaviorSubject } from 'rxjs/BehaviorSubject'


@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private appUrl = this.urlSource.getURL() + '/item';
  private addUrl = this.urlSource.getURL() + '/itemadd';
  private adminUrl = this.urlSource.getURL() + '/admin';
  //private buyUrl = this.urlSource.getURL() + '/buy';
  private headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});

  private item : Item;
  private id: string;
  private items: Item[];
  private stored: Item[];
 

  constructor(private urlSource: UrlService, private http: HttpClient) { }

  sell(id: number, accountid: number, name: string, description: string, tags: string, cost: number, image:string, sold:number): Observable<Item> {
    console.log( accountid+ ' ' + name + ' ' + description + ' '+tags + ' '+cost + ' ' + image + ' ' + sold);
    if(id && accountid && name && description && tags && cost && image) {     
      const body = `id=${id}&accountid=${accountid}&name=${name}&description=${description}&tags=${tags}&cost=${cost}&image=${image}&sold=${sold}`;
      console.log(body);
      return this.http.post(this.addUrl, body, {headers: this.headers, withCredentials: true})
        .pipe(map(resp => {
          const item: Item = resp as Item;
          this.item = item;   
          return item;
        }));
    } else {
      return this.http.get(this.addUrl, {withCredentials: true})
        .pipe(map(resp => {
          const item: Item = resp as Item;
          this.item = item;
          return item;
        }));
    }
  }
 

  getItem( id: string ): Observable<Item> {
    let params1 = new HttpParams().set("id", id);
    return this.http.get(this.appUrl, {withCredentials: true, params: params1})
    .pipe(map(resp => {

      const item: Item = resp as Item;

      this.item = item;
      console.log("Item" + item);
      return item;
    }));
  }

  getAllItems(): Observable<Item[]> {
    return this.http.get(this.appUrl + '/all', {withCredentials: true})
    .pipe(map(resp => {

      const items: Item[] = resp as Item[];

      this.items = items;
      console.log("Items" + items);
      return items;
    }));
  }

  buyItem(id: number, quantity: number, name: string): Observable<Item> {
    if(id) {     
      const body = `id=${id}&quantity=${quantity}&name=${name}`;
      console.log(body);
      return this.http.post(this.appUrl + '/buy', body, {headers: this.headers, withCredentials: true})
        .pipe(map(resp => {
          const item: Item = resp as Item;
          this.item = item;   
          return item;
        }));
    } else {
      return this.http.get(this.appUrl + '/buy', {withCredentials: true})
        .pipe(map(resp => {
          const item: Item = resp as Item;
          this.item = item;
          return item;
        }));
    }
  }

  deleteItem(id: string): Observable<Object> {
    return this.http.delete(this.adminUrl+'/itemId'+id, { withCredentials: true }).pipe(
      map(success=> {
        this.items = null;
        console.log("Item deleted");
        return success;
      })
    );

  }

  //Setters and Getters (Non-Observables)
  //Sets the Current Item ID for easier page viewing
  setCurrentItem(id: string){
    this.id = id;
    localStorage.setItem("currItemID", id);
  }

  getCurrentItem(){
    this.id = localStorage.getItem("currItemID");
    if(this.id == null){
      this.id = "1";
    }
    return this.id;
  }

  setStored(items: Item[]){
    this.stored = items;
  }

  getStored(){
    return this.stored;
  }

}
