import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Task} from "../dashboard-task/Task";
import {ActivatedRoute, Params} from "@angular/router";
import {DashboardService} from "../dashboard.service";

@Component({
  selector: 'app-dashboard-task-list',
  templateUrl: './dashboard-task-list.component.html',
  styleUrls: ['./dashboard-task-list.component.css']
})
export class DashboardTaskListComponent implements OnInit {

  taskList: Task[] = [];
  isDone: boolean = false;

  constructor(private route: ActivatedRoute,
              private dashboardService: DashboardService) { }

  ngOnInit(): void {
    /*console.log("DashboardTaskListComponent: ngOnInit()")*/
    this.route.queryParams.subscribe((queryParameter: Params) => {
      this.isDone = (queryParameter['isDone'] === 'true');
      console.log("route params have changed")
      console.log(this.isDone)
    })

    this.dashboardService.getTaskList();

    this.dashboardService.updatedArray.subscribe((arr:Task[]) => {
      this.taskList = arr.slice();
    });
  }

  getCurrentTaskList(taskList:Task[]) {
    console.log("getCurrentTaskList(taskList:Task[])")
    console.log(this.isDone)
    return taskList.filter(task => task.done === this.isDone);
  }
}
