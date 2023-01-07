import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-task-list',
  templateUrl: './dashboard-task-list.component.html',
  styleUrls: ['./dashboard-task-list.component.css']
})
export class DashboardTaskListComponent implements OnInit {

  exampleList: {taskTitle: string, taskContent: string}[] = [


  ];


  constructor() { }

  ngOnInit(): void {
  }

}
