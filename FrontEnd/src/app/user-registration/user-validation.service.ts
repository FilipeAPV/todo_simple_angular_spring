import {AbstractControl, AsyncValidatorFn, ValidationErrors, ValidatorFn} from '@angular/forms';
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, firstValueFrom, map, Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserValidationService {

    constructor(private http: HttpClient) {
    }

    passwordMatchingValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password');
      const confirmPassword = control.get('confirmPassword');

      /*console.log(password?.value)
      console.log(confirmPassword?.value)*/

      return password && confirmPassword && password.value === confirmPassword.value ? null : {passwordMismatch: true};
    }

  nonRepeatedEmailValidator(): AsyncValidatorFn {

    return (control: AbstractControl): Observable<ValidationErrors | null> => {

      return this.checkIfUserIsUnique(control.value).pipe(
        map(isUnique => {
          if (isUnique) {
            return null;
          } else {
            return {'emailIsAlreadyInDb': true};
          }
        })
      );
    }
  }

  checkIfUserIsUnique(email: string): Observable<boolean> {
    const url:string = "http://localhost:8080/api/verifyIfEmailExists";
    return this.http.get<boolean>(url, {params: {email: email}});
  }

  isEmailValid(control: AbstractControl): boolean {
    /*
    This regular expression checks for the following rules:

    The email address must start with one or more characters that are either letters, digits, _, or -.
    The email address must contain an @ symbol, followed by one or more characters that are either letters or digits.
    The email address must contain a period (.) after the @ symbol, followed by one or more characters that are either letters or digits.
     */

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$/;
    return emailRegex.test(control.get('email')?.value);
  }
}








