import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, tap} from "rxjs";

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});
  sessionId: string = "";

  constructor(private router: Router,
              private http: HttpClient) { }

  ngOnInit(): void {
    this.loginForm.addControl(
      "email", new FormControl("flaaaa@test.com", [Validators.required, Validators.email])
    );
    this.loginForm.addControl(
      "password", new FormControl("a", Validators.required)
    );
  }

  onSubmit() {

    const credentials = {
      username: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value
    }

    const url = 'http://localhost:8080/api/login';

    this.http.post<any>(url, credentials)
      .pipe(
        tap( res => {
          console.log(res)
          console.log("Login Successful")
        }))
      .subscribe({
        next: (res) => {
          this.sessionId = res.sessionId;

          sessionStorage.setItem('token', this.sessionId);

          this.router.navigate(["/dashboard/addTask"]);
        },
        error: (error) => console.log("Login Failed: " , error)
      })
  }
}
