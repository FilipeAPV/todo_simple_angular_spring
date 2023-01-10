import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-dashboard-menu',
  templateUrl: './dashboard-menu.component.html',
  styleUrls: ['./dashboard-menu.component.css']
})
export class DashboardMenuComponent implements OnInit {

  constructor(private http:HttpClient,
              private router: Router) { }

  ngOnInit(): void {
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
