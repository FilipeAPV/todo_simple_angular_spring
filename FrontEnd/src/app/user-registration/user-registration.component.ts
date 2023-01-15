import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators
} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {catchError, of, tap, throwError} from "rxjs";
import {Router} from "@angular/router";
import {UserRegistrationService} from "./user-registration.service";
import {UserValidationService} from "./user-validation.service";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  registrationForm!: FormGroup;

  constructor(private http: HttpClient,
              private router: Router,
              private userValidationService: UserValidationService,
              private usrRegistrationService: UserRegistrationService) {

  }

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      "firstName": new FormControl("testFName", [Validators.required, Validators.minLength(2)]),
      "lastName": new FormControl("testLName", Validators.required),
      "email": new FormControl("fl@test.com", {
        validators: [Validators.required, Validators.email, Validators.minLength(7), Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')],
        asyncValidators: [this.userValidationService.nonRepeatedEmailValidator()],
        updateOn:'blur' }),

      "passwords": new FormGroup({
        "password": new FormControl("a", Validators.required),
        "confirmPassword": new FormControl("a",[Validators.required])
      }, {validators: this.userValidationService.passwordMatchingValidator ,
      updateOn:'change'})

    });

    this.registrationForm.valueChanges.subscribe(()=> {
      /*console.log("err: " + this.registrationForm?.getError("emailIsAlreadyInDb"));*/
      /*console.log(this.registrationForm.get('passwords')?.getError('passwordMismatch'))*/
    })
  }

  onSubmit() {
    const userInfo = this.usrRegistrationService.createUserObject(this.registrationForm);
    const url:string = "http://localhost:8080/api/registerUser";
    console.log(userInfo)
    this.http.post(url, userInfo)
      .pipe(
        tap(response => {
          console.log("Successfully registered!");
        }),
        catchError(error => throwError(error))
      )
      .subscribe({
        next: (res) => {
          this.usrRegistrationService.isSuccessfullyRegistered = true;
          this.router.navigate(["/login"]);
        },
        error: (error) => console.error("Registration Failed:", error),
        complete: () => console.log("completed")
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
