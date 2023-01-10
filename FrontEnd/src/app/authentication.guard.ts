import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router) {
  }

  /**
   * The CanActivate interface is a part of the Angular routing module, and is used to protect routes from unauthorized access.
   * When a user navigates to a route that is protected by an AuthenticationGuard, the guard's canActivate() method is called
   * to determine whether the route should be activated or not.
   * @param route
   * @param state
   */

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    // Checks whether the user is trying to navigate to the /login route
    if (state.url == '/login') {
      return true;
    }

    // Checks the sessionStorage for a token
    let token = sessionStorage.getItem('token');

    // If the token is not found, it redirects the user to the /login route
      //  router.parseUrl('/login') is used in this code to return the UrlTree that represents the /login route
      // This allows the canActivate() method to return a value of the UrlTree type
    if (!token) {
      return this.router.parseUrl('/login');
    }

    // Otherwise allows the user to access the route.
    return true;
  }

}
