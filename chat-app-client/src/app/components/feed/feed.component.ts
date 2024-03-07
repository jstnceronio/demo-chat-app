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
  colors: string[] = ['fee2e2', 'fef3c7', '3357FF', 'ecfccb', 'ccfbf1', 'e0e7ff', 'f3e8ff', 'fce7f3'];
  color: string = '';

  username: string = 'User' + Math.floor(Math.random() * 1000);
  lastMessage: number = 0;

  constructor(private webSocketService: ChatService, private authService: AuthService) {}

  ngOnInit(): void {
    let customName = this.authService.username;
    if (customName) {
      this.username = customName;
    }
    this.connect();
  }

  ngOnDestroy(): void {
    this.webSocketService.close();
  }
  // ws://localhost:8080/websocket, wss://demo-chat-app-ch5j.onrender.com/websocket
  connect(): void {
    this.color = this.getRandomColor();
    this.webSocketService.connect('wss://demo-chat-app-ch5j.onrender.com/websocket', this.username, this.color)
    //this.webSocketService.connect('ws://localhost:8080/websocket', this.username);
    this.username = this.username.substring(0, 30)
    this.webSocketService.messages.subscribe((message) => {
      this.messages.unshift(message);
    });
  }

  sendMessage(): void {
    if((Date.now() - this.lastMessage) > 2000){
      if (!(this.messageContent.trim().length == 0)) {
        this.messageContent = this.messageContent.substring(0, 200)
        this.webSocketService.sendMessage(this.messageContent);
        this.messageContent = '';
        this.lastMessage = Date.now()
      }
    }
  }

   getRandomColor(): string {
    // Generate a random index based on the length of the colors array
    const randomIndex: number = Math.floor(Math.random() * this.colors.length);

    // Return the color at the randomly selected index
    return this.colors[randomIndex];
  }
}
