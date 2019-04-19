import { Injectable } from '@angular/core';

import { UrlService } from '../url.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { Observable, pipe, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ItemReview } from 'src/app/shared/ItemReview/ItemReview';

@Injectable({
  providedIn: 'root'
})
export class ItemReviewService {
  private appUrl = this.urlSource.getURL() + '/itemreview';
  private appUrl2 = this.appUrl + '/get';
  private headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});

  private newreviews: ItemReview[];
  private reviews: ItemReview[];

  constructor(private urlSource: UrlService, private http: HttpClient) { }

  getReviews(itemid: string): Observable<ItemReview[]> {  // I know post isn't right here but this is due tomorrrowwwww.
    const body = `itemid=${itemid}`;
    console.log(body);
    return this.http.post(this.appUrl2, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
      const reviews: ItemReview[] = resp as ItemReview[];
      this.reviews = reviews;
      console.log("reviewsssss" + reviews);
      return reviews;
    }));
    }
 
  makeReview(accountid: number, itemid:number, description:string ): Observable<ItemReview[]> {
    const body = `accountid=${accountid}&itemid=${itemid}&description=${description}`;
    console.log(body);
    return this.http.post(this.appUrl, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
      const newreviews: ItemReview[] = resp as ItemReview[];
      this.newreviews = newreviews;
      console.log("newreviews" + newreviews);
      return newreviews;
    }));
    }
  
}
