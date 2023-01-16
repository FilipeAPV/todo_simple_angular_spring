import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import {ReactiveFormsModule} from "@angular/forms";
import { UserLoginComponent } from './user-login/user-login.component';
import {AppRoutingModule} from "./app-routing.module";
import { FieldValidationDirective } from './user-registration/field-validation.directive';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DashboardTaskComponent } from './dashboard/dashboard-task/dashboard-task.component';
import { DashboardMenuComponent } from './dashboard/dashboard-menu/dashboard-menu.component';
import { DashboardTaskListComponent } from './dashboard/dashboard-task-list/dashboard-task-list.component';
import {AuthenticationGuard} from "./authentication.guard";
import {RequestInterceptor} from "./request.interceptor";
import { UsersListComponent } from './dashboard/users-list/users-list.component';

@NgModule({
  declarations: [
    AppComponent,
    UserRegistrationComponent,
    UserLoginComponent,
    FieldValidationDirective,
    DashboardComponent,
    DashboardTaskComponent,
    DashboardMenuComponent,
    DashboardTaskListComponent,
    UsersListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [
    AuthenticationGuard,
    { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
