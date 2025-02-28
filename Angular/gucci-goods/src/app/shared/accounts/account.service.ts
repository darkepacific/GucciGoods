import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
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
  private headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

  private accountSubject = new BehaviorSubject<Account | null>(this.loadAccountFromStorage()); // 🔹 Restores from localStorage
  public account$ = this.accountSubject.asObservable(); // 🔹 Observable for UI updates

  private sellerAccount: Account | null = null;
  private accs: Account[] = [];

  constructor(private urlSource: UrlService, private http: HttpClient, private router: Router) {}

  // 🔹 LOGIN
  login(username: string, password: string): Observable<Account> {
    console.log(`${username} ${password}`);
    if (username && password) {
      const body = `user=${username}&pass=${password}`;
      console.log(body);
      return this.http.post(this.appUrl, body, { headers: this.headers, withCredentials: true }).pipe(
        map(resp => {
          const account: Account = resp as Account;
          this.accountSubject.next(account); // 🔹 Update login state
          this.saveAccountToStorage(account); // 🔹 Save to localStorage
          return account;
        })
      );
    } else {
      return this.http.get(this.appUrl, { withCredentials: true }).pipe(
        map(resp => {
          const account: Account = resp as Account;
          this.accountSubject.next(account); // 🔹 Update login state
          this.saveAccountToStorage(account); // 🔹 Save to localStorage
          return account;
        })
      );
    }
  }

  // 🔹 LOGOUT
  logout(): Observable<Object> {
    return this.http.delete(this.appUrl, { withCredentials: true }).pipe(
      map(success => {
        this.accountSubject.next(null); // 🔹 Clear login state
        this.clearAccountFromStorage(); // 🔹 Remove from localStorage
        console.log("Logged out");
        return success;
      })
    );
  }

  // 🔹 CHECK LOGIN STATUS
  getAccount(): Account | null {
    return this.accountSubject.value;
  }

  isLoggedIn(): boolean {
    return this.accountSubject.value !== null;
  }

  // 🔹 PERSIST LOGIN STATE
  private saveAccountToStorage(account: Account): void {
    localStorage.setItem('loggedAccount', JSON.stringify(account));
  }

  private loadAccountFromStorage(): Account | null {
    const storedAccount = localStorage.getItem('loggedAccount');
    return storedAccount ? JSON.parse(storedAccount) : null;
  }

  private clearAccountFromStorage(): void {
    localStorage.removeItem('loggedAccount');
  }

  // 🔹 ACCOUNT MANAGEMENT

  getAccounts(): Observable<Account[]> {
    return this.http.get(this.adminUrl, { withCredentials: true }).pipe(
      map(resp => {
        this.accs = resp as Account[];
        console.log("Accounts fetched:", this.accs);
        return this.accs;
      })
    );
  }

  deleteAccount(id: string): Observable<Object> {
    return this.http.delete(`${this.adminUrl}/${id}`, { withCredentials: true }).pipe(
      map(success => {
        console.log("Account deleted");
        return success;
      })
    );
  }

  getSellerAccount(id: string): Observable<Account> {
    return this.http.get(`${this.appUrl}/${id}`, { withCredentials: true }).pipe(
      map(resp => {
        this.sellerAccount = resp as Account;
        console.log("Grabbed seller account:", this.sellerAccount);
        this.router.navigateByUrl('seller-profile');
        return this.sellerAccount;
      })
    );
  }

  updateEmail(newEmail: string, id: number): Observable<string> {
    const body = `newemail=${newEmail}&id=${id}`;
    console.log("Updating email:", body);
    return this.http.post(this.emailUrl, body, { headers: this.headers, withCredentials: true }).pipe(
      map(resp => {
        const email = resp as string;
        if (this.accountSubject.value) {
          this.accountSubject.value.email = email;
          this.saveAccountToStorage(this.accountSubject.value); // 🔹 Update localStorage
        }
        return email;
      })
    );
  }

  updateLocation(newLocation: string, id: number): Observable<string> {
    const body = `newlocation=${newLocation}&id=${id}`;
    console.log("Updating location:", body);
    return this.http.post(this.locationUrl, body, { headers: this.headers, withCredentials: true }).pipe(
      map(resp => {
        const location = resp as string;
        if (this.accountSubject.value) {
          this.accountSubject.value.location = location;
          this.saveAccountToStorage(this.accountSubject.value); // 🔹 Update localStorage
        }
        return location;
      })
    );
  }

  updatePhone(newPhone: string, id: number): Observable<string> {
    const body = `newphone=${newPhone}&id=${id}`;
    console.log("Updating phone:", body);
    return this.http.post(this.phoneUrl, body, { headers: this.headers, withCredentials: true }).pipe(
      map(resp => {
        const phone = resp as string;
        if (this.accountSubject.value) {
          this.accountSubject.value.phone = phone;
          this.saveAccountToStorage(this.accountSubject.value); // 🔹 Update localStorage
        }
        return phone;
      })
    );
  }

  updateDescription(newDescription: string, id: number): Observable<string> {
    const body = `newdescription=${newDescription}&id=${id}`;
    console.log("Updating description:", body);
    return this.http.post(this.descriptionUrl, body, { headers: this.headers, withCredentials: true }).pipe(
      map(resp => {
        const description = resp as string;
        if (this.accountSubject.value) {
          this.accountSubject.value.description = description;
          this.saveAccountToStorage(this.accountSubject.value); // 🔹 Update localStorage
        }
        return description;
      })
    );
  }

  // 🔹 SELLER ACCOUNT HELPERS

  getSeller(): Account | null {
    return this.sellerAccount;
  }

  clearSeller(): void {
    this.sellerAccount = null;
  }
}
