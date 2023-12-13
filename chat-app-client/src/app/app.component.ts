import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ChatService } from './service/chat.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'chat-app-client';
  messages: any[] = [];

  constructor(private chatService: ChatService) {}

  ngOnInit() {
    // this.loadMessages();
  }

  loadMessages() {
    this.chatService.getMessages().subscribe(data => {
      this.messages = data;
    }, error => console.error(error));
  }

  onSendMessage(newMessage: string) {
    this.chatService.sendMessage({ content: newMessage }).subscribe(() => {
      this.loadMessages(); // Reload messages after sending
    });
  }
}
