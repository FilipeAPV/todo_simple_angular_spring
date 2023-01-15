import {Injectable} from "@angular/core";
import {Task} from "./dashboard-task/Task";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Subject, tap} from "rxjs";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  updatedArray = new Subject<Task[]>();

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getTaskList() {
    const url = "http://localhost:8080/api/listTasks";

    this.http.get<Task[]>(url)
      .pipe(
        tap( res => {
          console.log("Call has been made!");

        }))
      .subscribe({
        next: (res: Task[]) => {
          this.updatedArray.next(res.slice());
        },
        error: (err) => console.log("Error Retrieving List")
      })
  }

  persistTask(task: Task) {
    const url = "http://localhost:8080/api/saveTask";

    this.http.post<any>(url, task)
      .pipe(
        tap(
          response => {
            console.log(response);
          }
        )
      )
      .subscribe( {
        next: (response) => {
          console.log(task.id + " " + task.title + " has been persisted");
          this.getTaskList();
          this.router.navigate(["/dashboard/listTask"]);
        },
        error: (err) => console.log("Saving process has failed!")
      });
  }
}
