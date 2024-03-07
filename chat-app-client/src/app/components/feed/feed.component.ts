import {Component, OnDestroy, OnInit} from '@angular/core';
import { Message } from '../../model/message';
import { ChatService } from '../../service/chat.service';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HttpClientModule, FormsModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit, OnDestroy {
  messages: any[] = [];
  messageContent: string = '';
  username: string = 'User' + Math.floor(Math.random() * 1000);
  lastMessage: number = 0;

  constructor(private webSocketService: ChatService, private authService: AuthService) {}

  ngOnInit(): void {
    this.username = this.authService.username;
    this.connect();
  }

  ngOnDestroy(): void {
    this.webSocketService.close();
  }
  // ws://localhost:8080/websocket
  connect(): void {
    //this.webSocketService.connect('wss://demo-chat-app-ch5j.onrender.com/websocket', this.username)
    this.username = this.username.substring(0, 30)
    this.webSocketService.connect('ws://localhost:8080/websocket', this.username);
    this.webSocketService.messages.subscribe((message) => {
      this.messages.unshift(message);
    });
  }

  sendMessage(): void {
    if( (Date.now() - this.lastMessage) > 2000){
      this.messageContent = this.messageContent.substring(0, 200)
      this.webSocketService.sendMessage(this.messageContent);
      this.messageContent = '';
      this.lastMessage = Date.now()
    }
  }

  restrictInput(): void {
    this.messageContent = this.messageContent.substring(0, 200)
  }
}
