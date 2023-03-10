import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Task} from "./Task";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {tap} from "rxjs";
import {DashboardService} from "../dashboard.service";

@Component({
  selector: 'app-dashboard-task',
  templateUrl: './dashboard-task.component.html',
  styleUrls: ['./dashboard-task.component.css']
})
export class DashboardTaskComponent implements OnInit {

  taskForm: FormGroup = new FormGroup({});

  constructor(private http:HttpClient,
              private router:Router,
              private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.taskForm.addControl(
      "taskTitle", new FormControl("Test Task Title", [Validators.required])
    );
    this.taskForm.addControl(
      "taskContent", new FormControl("Test Task Content", [Validators.required])
    );
  }

  onSubmit(): void {
    const task = new Task(0, this.taskForm.get('taskTitle')?.value, this.taskForm.get('taskContent')?.value, false);
    this.dashboardService.persistTask(task);
    console.log(task);
  }
}
