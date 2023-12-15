import { Component, OnInit } from '@angular/core';
import { Message } from '../../model/message';
import { ChatService } from '../../service/chat.service';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HttpClientModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit {
  messages: Message[] = [
    {
      id: 0,
      content: "Hi", 
      sender: "Aaron"
    },
    {
      id: 1,
      content: "Hey, how's it going?", 
      sender: "Lukas"
    }
  ];

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
