import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, pipe, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Admin } from './admin';
import { CurrentUser } from './current-user';
import { Account } from './account';
import { UrlService } from '../url.service';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private appUrl = this.urlSource.getURL() + '/login';
  private adminUrl = this.urlSource.getURL() + '/admin';
  private emailUrl = this.appUrl + '/email';
  private locationUrl = this.appUrl + '/location';
  private phoneUrl = this.appUrl + '/phone';
  private descriptionUrl = this.appUrl + '/description';
  private headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
  // private admin: Admin;
  private account: Account;
  private sellerAccount: Account;
  private accs: Account[];
  
  constructor(private urlSource: UrlService, private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<Account> {
    console.log(username+' '+password);
    if(username && password) {
      
      const body = `user=${username}&pass=${password}`;
      console.log(body);
      return this.http.post(this.appUrl, body, {headers: this.headers, withCredentials: true})
        .pipe(map(resp => {
          const account: Account = resp as Account;

          this.account = account;

          return account;
        }));
    } else {
      return this.http.get(this.appUrl, {withCredentials: true})
        .pipe(map(resp => {
          const account: Account = resp as Account;
          this.account = account;
          return account;
        }));
    }
  }

  logout(): Observable<Object> {
    return this.http.delete(this.appUrl, { withCredentials: true }).pipe(
      map(success=> {
        this.account = null;
        console.log("Logged out");
        return success;
      })
    );
  }

  getSellerAccount(id: string): Observable<Account>{
    return this.http.get(this.appUrl+'/'+id, { withCredentials: true }).pipe(
      map(resp=> {
        const sellerAccount = resp as Account;
        this.sellerAccount = sellerAccount;
        console.log("Grabbed seller account");
        console.log(this.sellerAccount);
        this.router.navigateByUrl('seller-profile');
        return this.sellerAccount;
      })
    );
  }

  updateEmail( newemail : string, id: number ): Observable<String>{
    console.log("In updateEmail in account.service.");
    const body = `newemail=${newemail}&id=${id}`;
    console.log(body);
    return this.http.post(this.emailUrl, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
        const email: string = resp as string;
        console.log("IN UPDATEEMAIL response" + email);
        this.account.email = email;
        return email;
      }));
  }

  updateLocation( newlocation : string, id: number ): Observable<String>{
    console.log("In updateEmail in account.service.");
    const body = `newlocation=${newlocation}&id=${id}`;
    console.log(body);
    return this.http.post(this.locationUrl, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
        const location: string = resp as string;
        console.log("IN UPDATEEMAIL response" + location);
        this.account.location = location;
        return location;
      }));
  }

  updatePhone( newphone : string, id: number ): Observable<String>{
    console.log("In updateEmail in account.service.");
    const body = `newphone=${newphone}&id=${id}`;
    console.log(body);
    return this.http.post(this.phoneUrl, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
        const phone: string = resp as string;
        console.log("IN UPDATEEMAIL response" + phone);
        this.account.phone = phone;
        return phone;
      }));
  }

  updateDescription( newdescription : string, id: number ): Observable<String>{
    console.log("In updateEmail in account.service.");
    const body = `newdescription=${newdescription}&id=${id}`;
    console.log(body);
    return this.http.post(this.descriptionUrl, body, {headers: this.headers, withCredentials: true})
      .pipe(map(resp => {
        const description: string = resp as string;
        console.log("IN UPDATEEMAIL response" + description);
        this.account.description = description;
        return description;
      }));
  }

  getAccounts(): Observable<Account[]> {
    return this.http.get(this.adminUrl, {withCredentials: true})
    .pipe(map(resp => {
      const accs: Account[] = resp as Account[];
      this.accs = accs;
      console.log("Accs" + accs);
      return accs;
    }));
  }

  deleteAccount(id: string): Observable<Object> {
    return this.http.delete(this.adminUrl+'/'+id, { withCredentials: true }).pipe(
      map(success=> {
        this.account = null;
        console.log("Account deleted");
        return success;
      })
    );
  }
  
  getSeller(): Account {
    return this.sellerAccount;
  }
  clearSeller(): void {
    this.sellerAccount = null;
  }
  getAccount(): Account {
    return this.account;
  }

  isAccount(): boolean {
    return (this.account!== undefined && this.account!== null);
  }
}
