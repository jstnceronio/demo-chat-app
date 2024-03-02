import {Component, OnDestroy, OnInit} from '@angular/core';
import { Message } from '../../model/message';
import { ChatService } from '../../service/chat.service';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from "@angular/forms";

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

  constructor(private webSocketService: ChatService) {}

  ngOnInit(): void {
    this.connect();
  }

  ngOnDestroy(): void {
    this.webSocketService.close();
  }

  connect(): void {
    this.webSocketService.connect('ws://localhost:8080/websocket', this.username);
    this.webSocketService.messages.subscribe((message) => {
      this.messages.push(message);
    });
  }

  sendMessage(): void {
    this.webSocketService.sendMessage(this.messageContent);
    this.messageContent = '';
  }
}
