import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn} from '@angular/forms';

export const nonRepeatedEmailValidator: ValidatorFn = (control:AbstractControl) : ValidationErrors | null => {

  const emailListTest: string[] = ["a@a.com","b@b.com","a@aba.com"];

  const emailToCheck: string = control.get('email')?.value;
  if (emailListTest.includes(emailToCheck)) {
    return { emailIsAlreadyInDb: true }
  } else {
    return null;
  }

}

export const passwordMatchingValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  /*console.log(password?.value)
  console.log(confirmPassword?.value)*/

  return password && confirmPassword && password.value === confirmPassword.value ? null : { passwordMismatch: true } ;
};




