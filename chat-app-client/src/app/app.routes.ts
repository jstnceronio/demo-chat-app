import { Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import {LoginComponent} from "./components/login/login.component";
import {AuthGuard} from "./service/auth.guard";

export const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "feed", component: FeedComponent }
];
