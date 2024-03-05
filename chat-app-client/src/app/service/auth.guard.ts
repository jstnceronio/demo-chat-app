import {ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {map, Observable, take, tap} from "rxjs";
import {AuthService} from "./auth.service";

export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> {
    return this.auth.user$.pipe(
      take(1),
      map((user: any) => !!user), // map to boolean
      tap((loggedIn: any) => {
        if (!loggedIn) {
          console.log('User not logged in');
          this.router.navigate(['/login']);
        }
      })
    );
  }
}