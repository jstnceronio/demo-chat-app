import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  // private auth: Auth = inject(Auth);
  username: string = '';

  constructor(private auth: AuthService, private router: Router) {  }

  ngOnInit(): void {}

  enterChat() {
    if (this.username && this.username.trim().length >= 2 && this.username.trim() != 'System') {
      this.auth.username = this.username;
      this.router.navigate(['/feed'])
    }
  }
}
