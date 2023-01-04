import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {catchError, of, tap} from "rxjs";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  registrationForm!: FormGroup;

  constructor(private http: HttpClient) {

  }

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      "firstName": new FormControl("fNameTest", Validators.required),
      "lastName": new FormControl("lNameTest", Validators.required),
      "email": new FormControl("", [Validators.required, Validators.email]),
      "password": new FormControl("a", Validators.required)
    })

/*    this.http.get('http://localhost:8080/users').subscribe(
      data => { console.log(data) }
    );*/
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
