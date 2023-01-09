import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {FormGroup} from "@angular/forms";
import {User} from "./User";

@Injectable({
    providedIn: 'root'
  })
export class UserRegistrationService {

  constructor(private http: HttpClient) {
  }

  createUserObject(formGroup: FormGroup) : User {
    return new User(
      formGroup.get('firstName')?.value,
      formGroup.get('lastName')?.value,
      formGroup.get('email')?.value,
      formGroup.get('passwords.password')?.value
    )
  }

}
