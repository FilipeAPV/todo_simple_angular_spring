import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Task} from "../dashboard-task/Task";

@Component({
  selector: 'app-dashboard-task-list',
  templateUrl: './dashboard-task-list.component.html',
  styleUrls: ['./dashboard-task-list.component.css']
})
export class DashboardTaskListComponent implements OnInit {

  taskList: Task[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {

  }
}
