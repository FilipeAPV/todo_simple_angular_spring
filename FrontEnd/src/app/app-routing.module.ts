import {RouterModule, Routes} from "@angular/router";
import {UserLoginComponent} from "./user-login/user-login.component";
import {NgModule} from "@angular/core";
import {UserRegistrationComponent} from "./user-registration/user-registration.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {DashboardTaskComponent} from "./dashboard/dashboard-task/dashboard-task.component";
import {DashboardTaskListComponent} from "./dashboard/dashboard-task-list/dashboard-task-list.component";
import {AuthenticationGuard} from "./authentication.guard";

const appRoutes: Routes = [
  {path : '', canActivate : [AuthenticationGuard], children: [
      {path: '', component: DashboardComponent},
      {path: 'login', component: UserLoginComponent},
      {path : 'register', component: UserRegistrationComponent},
      {path : 'dashboard', component: DashboardComponent, children: [
          {path: 'addTask', component: DashboardTaskComponent},
          {path: 'listTask', component: DashboardTaskListComponent}
        ]},
      {path : '**', redirectTo: ''}

    ] },

/*  {path : 'login', component: UserLoginComponent},
  {path : 'register', component: UserRegistrationComponent},
  {path : 'dashboard', component: DashboardComponent, children: [
      {path: 'addTask', component: DashboardTaskComponent},
      {path: 'listTask', component: DashboardTaskListComponent}
    ]}*/
]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports : [RouterModule]
})
export class AppRoutingModule {

}
