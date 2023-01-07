import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators
} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {catchError, of, tap} from "rxjs";
import {nonRepeatedEmailValidator, passwordMatchingValidator} from "./user-validation";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  registrationForm!: FormGroup;

  constructor(private http: HttpClient,
              private router: Router) {

  }

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      "firstName": new FormControl("", [Validators.required, Validators.minLength(2)]),
      "lastName": new FormControl("", Validators.required),
      "email": new FormControl("", [Validators.required,
                  Validators.email, Validators.minLength(7)]),

      "passwords": new FormGroup({
        "password": new FormControl("", Validators.required),
        "confirmPassword": new FormControl("",[Validators.required])
      }, {validators: passwordMatchingValidator })

    }, {validators: nonRepeatedEmailValidator});

    this.registrationForm.valueChanges.subscribe(()=> {
 /*     console.log("err: " + this.registrationForm?.getError("emailIsAlreadyInDb"));
      console.log(this.registrationForm.get('passwords')?.getError('passwordMismatch'))*/
    })

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

  isNotEmpty(input: AbstractControl) {
    return (input.value !== "" && input.value !== null && input.value !== undefined);
  }

  getGenericInputClasses(formControlName: string): { [key: string]: boolean | undefined } {
    const formControl = this.registrationForm.get(formControlName);
    return {
      'is-invalid' : (formControl?.invalid && formControl?.touched && this.isNotEmpty(formControl))
    }
  }

  getEmailInputClasses(): { [key: string]: boolean | undefined } {

    const formControl = this.registrationForm.get('email');

    return {
      'is-invalid' : formControl?.invalid && formControl?.touched && this.isNotEmpty(formControl)|| this.registrationForm.getError('emailIsAlreadyInDb'),
      'is-valid' : formControl?.valid && !this.registrationForm.getError('emailIsAlreadyInDb')
    }
  }

  getConfirmPasswordInputClasses(): {[key: string] : boolean | undefined} {

    const formControl = this.registrationForm.get('passwords.confirmPassword');

    return {
      'is-invalid' : (
        formControl?.invalid && formControl?.touched && this.isNotEmpty(formControl))
        ||
        (this.registrationForm.get('passwords')?.getError('passwordMismatch') && formControl?.touched
        )}
  }

}
