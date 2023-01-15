import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Router} from "@angular/router";
import {DashboardService} from "../dashboard.service";
import {Task} from "../dashboard-task/Task";

@Component({
  selector: 'app-dashboard-menu',
  templateUrl: './dashboard-menu.component.html',
  styleUrls: ['./dashboard-menu.component.css']
})
export class DashboardMenuComponent implements OnInit {

  numberOfNonDoneTasks: number = 0;
  numberOfDoneTasks: number = 0;

  constructor(private http:HttpClient,
              private router: Router,
              private dashBoardService: DashboardService) { }

  ngOnInit(): void {
    this.dashBoardService.updatedArray.subscribe( (arr: Task[]) =>
      {
        console.log("Result has been received!");
        this.numberOfNonDoneTasks = arr.filter(task => !task.done).length;
        this.numberOfDoneTasks = arr.filter(task => task.done).length;
      }
    );
  }

  logout() {
    const url = 'http://localhost:8080/api/logout';

    this.http.get(url, {}).pipe(
      tap(res => {
        console.log(res)
        console.log("Logout Successfull")
      }))
      .subscribe({
        next: () => {
          this.router.navigate(["/login"])
        },
        error: (error) => console.log("Logout Failed: ", error)
      });
  }
}
