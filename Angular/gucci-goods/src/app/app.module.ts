import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes, ExtraOptions } from '@angular/router';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';

import { AppComponent } from './app.component';
import { ProfileComponent } from './profile/profile.component';
import { ItemComponent } from './item/item.component';
import { SearchComponent } from './search/search.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AdminComponent } from './admin/admin.component';
import { SellItemComponent  } from './sell-item/sell-item.component';
import { UrlService } from './shared/url.service';
import { AccountService } from './shared/accounts/account.service';
import { ItemService } from './shared/items/item.service';
import { HomeComponent } from './home/home.component';
import { Ng2CompleterModule } from 'ng2-completer';
import { ItemsmallComponent } from './itemsmall/itemsmall.component';

import { SellerProfileComponent } from './seller-profile/seller-profile.component';
import { SearchService } from './shared/search/search.service';


//import { PageNotFoundComponent } from '';

const appRoutes: Routes = [
  { path: 'search', component: SearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: '', component: HomeComponent},   
  { path: 'home', component: HomeComponent},  
  { path: 'item', component: ItemComponent},
  { path: 'seller-profile', component: SellerProfileComponent},
  { path: '**', component: HomeComponent}
  
  //{ path: '**', component: AppComponent } //make page not found component
];

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    ItemComponent,
    SearchComponent,
    LoginComponent,
    RegisterComponent,
    AdminComponent,
    SellItemComponent,
    HomeComponent,
    ItemsmallComponent,
    SellerProfileComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(
      appRoutes
    ),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    Ng2CompleterModule 
  ],
  providers: [UrlService, AccountService, ItemService, SearchService],
  bootstrap: [AppComponent]
})
export class AppModule { }
