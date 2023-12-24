import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Socket } from 'socket.io';
import io from 'socket.io-client';

@Injectable({
  providedIn: 'root'
})
export class SocketService {

private socket = io('http://localhost:8080');

sendMessage(message: string){
  this.socket.emit('websocket', message);
}

getMessages() {
  let observable = new Observable<{ user: String, message: String }>(observer => {
    this.socket.on('websocket', (data) => {
      observer.next(data);
    });
    return () => { this.socket.disconnect(); };  
  });
  return observable;
}
}
