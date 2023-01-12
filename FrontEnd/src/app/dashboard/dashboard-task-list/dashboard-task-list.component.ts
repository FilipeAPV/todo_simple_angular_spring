import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";

@Component({
  selector: 'app-dashboard-task-list',
  templateUrl: './dashboard-task-list.component.html',
  styleUrls: ['./dashboard-task-list.component.css']
})
export class DashboardTaskListComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    const url = "http://localhost:8080/api/listTasks";

    this.http.get(url)
      .pipe(
        tap( res => console.log(res)))
      .subscribe({
        next: res => {
        },
        error: (err) => console.log("Error Retrieving List")
      })

  }

}
