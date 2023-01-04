import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import { UserLoginComponent } from './user-login/user-login.component';
import {AppRoutingModule} from "./app-routing.module";
import { FieldValidationDirective } from './user-registration/field-validation.directive';

@NgModule({
  declarations: [
    AppComponent,
    UserRegistrationComponent,
    UserLoginComponent,
    FieldValidationDirective
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
