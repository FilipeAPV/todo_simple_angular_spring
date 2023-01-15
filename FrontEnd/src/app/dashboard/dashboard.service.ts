import {Injectable} from "@angular/core";
import {Task} from "./dashboard-task/Task";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Subject, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  updatedArray = new Subject<Task[]>();

  constructor(private http: HttpClient) {
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
}
