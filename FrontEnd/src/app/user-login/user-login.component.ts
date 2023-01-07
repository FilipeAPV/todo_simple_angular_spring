import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({});

  constructor(private router:Router) { }

  ngOnInit(): void {
    this.loginForm.addControl(
      "email", new FormControl("", [Validators.required, Validators.email])
    );
    this.loginForm.addControl(
      "password", new FormControl("", Validators.required)
    );
  }

  onSubmit() {
    this.router.navigate(['/dashboard/addTask']);
  }
}
