import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import {Message} from "../model/message";

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private webSocket: WebSocketSubject<any>;
  private messageSubject = new Subject<any>();
  public messages: Observable<any> = this.messageSubject.asObservable();

  constructor() {}

  public connect(url: string, username: string, color: string): void {
    this.webSocket = webSocket({
      url: `${url}?username=${username}?color=${color}`,
      deserializer: msg => msg.data,
    });

    this.webSocket.subscribe(
      (msg) => {
        console.log(msg);
        const messageObj = JSON.parse(msg);
        console.log('Received message:', messageObj);
        this.messageSubject.next(messageObj);
      },
      (err) => console.error(err),
      () => console.warn('Completed!')
    );
  }

  sendMessage(message: string): void {
    this.webSocket.next(message);
  }

  close(): void {
    this.webSocket.complete();
    this.messageSubject.complete();
  }
}
