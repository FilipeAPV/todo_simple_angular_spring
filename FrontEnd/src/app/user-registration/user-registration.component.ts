import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {catchError, of, tap} from "rxjs";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  registrationForm: FormGroup;

  constructor(private http: HttpClient) {
    this.registrationForm = new FormGroup({
      "firstName": new FormControl(),
      "lastName": new FormControl(),
      "email": new FormControl(),
      "password": new FormControl()
    })
  }

  ngOnInit(): void {

  }

  onSubmit() {
    console.log(this.registrationForm.value);
    const registrationData = this.registrationForm.value;
    this.http.post('http://localhost:8080/registerUser', registrationData)
      .pipe(
        tap(response => console.log("Successfully registered!")),
        catchError(error => of("Registration Failed: " + error.value))
      )
      .subscribe({
        next: response => console.log(response),
        error: error => console.log(error)
      });
  }
}
