import { Component, OnInit } from '@angular/core';
import { Message } from '../../model/message';
import { ChatService } from '../../service/chat.service';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { SocketService } from '../../service/socket.service';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HttpClientModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit {
  message: Message = { id: 0, content: '', sender: '' };

  messages: Message[] = [
    { id: 0, content: '', sender: '' }
  ];

  constructor(private chatService: ChatService, private socketService: SocketService) {}

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    /*
    this.chatService.getMessages().subscribe(data => {
      this.messages = data;
    }, error => console.error(error)); */
    this.chatService.getMessages().subscribe((message: any) => {
      this.messages.push(message);
    });
  }

  onSendMessage(newMessage: string) {
    /*
    this.chatService.sendMessage({ content: newMessage }).subscribe(() => {
      this.loadMessages(); // Reload messages after sending
    }); */
    this.chatService.sendMessage(this.message);
    this.message = { id: 0, content: '', sender: '' };
  }
}
