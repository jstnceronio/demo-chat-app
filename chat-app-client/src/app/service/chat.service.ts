import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private apiUrl = 'http://localhost:8080/messages';

  constructor(private http: HttpClient) { }

  getMessages(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  sendMessage(message: any): Observable<any> {
    return this.http.post(this.apiUrl, message);
  }
}