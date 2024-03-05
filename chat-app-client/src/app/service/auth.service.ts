import {Injectable} from '@angular/core';
import {AngularFireAuth} from "@angular/fire/compat/auth";
import {Observable} from "rxjs";
import {User} from "../model/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authState: any = null;
  username: string = '';
  user$: Observable<User | null | undefined>;
  constructor() { }

  /*
  async googleSignIn() {
    const provider = new GoogleAuthProvider();
    try {
      return await this.afAuth.signInWithPopup(provider);
    } catch (error) {
      console.error(error);
      return null;
    }
  }

  async signOut() {
    await this.afAuth.signOut();
  }

  signUp(email: string, password: string) {
    this.afAuth.createUserWithEmailAndPassword(email, password)
      .then(() => {
        // Sign up successful
        console.log('welcome senior')
      })
      .catch((error) => {
        // An error occurred
      });
  }*/
}
