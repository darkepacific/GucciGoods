import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, pipe, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Account } from '../accounts/account';
import { UrlService } from '../url.service';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private appUrl = this.urlSource.getURL() + '/register';
  private headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
  
  private account: Account;
  
  constructor(private urlSource: UrlService, private http: HttpClient) { }

  register(username: string, password: string, firstname: string, lastname: string, country: string, phone: string, 
    email: string, admin: number, avatar: string): Observable<Account> {
      admin = 0;
      avatar = null;
    console.log(username+' '+password+' '+firstname+' '+lastname+' '+country+' '+phone+' '+email+' '+admin+' '+avatar);
    if(!username || !password || !firstname || !lastname || !country || !phone || !email) {
      return this.http.get(this.appUrl, {withCredentials: true})
        .pipe(map(resp => {
          const account: Account = resp as Account;
          
          this.account = account;

          return account;
        }));
      
    } else {
      const body = `user=${username}&pass=${password}&email=${email}&first=${firstname}&last=${lastname}&admin=${admin}&country=${country}&phone=${phone}`;
      console.log(body);
      return this.http.post(this.appUrl, body, {headers: this.headers, withCredentials: true})
        .pipe(map(resp => {
          const account: Account = resp as Account;

          this.account = account;

          return account;
        }));
    }
  }
}