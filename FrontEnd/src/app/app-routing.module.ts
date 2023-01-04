import {RouterModule, Routes} from "@angular/router";
import {UserLoginComponent} from "./user-login/user-login.component";
import {NgModule} from "@angular/core";
import {UserRegistrationComponent} from "./user-registration/user-registration.component";

const appRoutes: Routes = [
  {path : '', component: UserLoginComponent},
  {path : 'register', component: UserRegistrationComponent}
]

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports : [RouterModule]
})
export class AppRoutingModule {

}
