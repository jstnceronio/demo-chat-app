import 'dart:convert';

import 'package:chat_app_mobile_client/service/message.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class ChatScreen extends StatefulWidget {
  final WebSocketChannel websocket;

  const ChatScreen(this.websocket, {super.key});
  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final List<String> messages = [];
  final TextEditingController _messageController = TextEditingController();

  @override
  void dispose() {
    widget.websocket.sink.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    widget.websocket.stream.listen(
      (message) {
        String message2 = message.toString().replaceAll("\'", "\"");
        print(message2);
        String test = "{\"username\":\"test\", \"message\":\"test\"}";
        print(test);

        if (test == message2) {
          print('gleich');
        }
        var test2 = (jsonDecode(message2));
        print(test2['username']);

        //var test = Message.fromJson(message);
      },
      onDone: () {
        print('done');
      },
    );
    return Scaffold(
      appBar: AppBar(
        leading: TextButton(
          child: Text('zurück'),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: Column(
        children: [
          /*  Expanded(
              child: ),  */
          Container(
            padding:
                const EdgeInsets.only(left: 8, right: 8, bottom: 30, top: 15),
            color: Colors.white,
            child: Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _messageController,
                    decoration: InputDecoration(
                      hintText: 'Nachricht schreiben...',
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30),
                        borderSide: BorderSide.none,
                      ),
                      fillColor: Colors.grey[200],
                      filled: true,
                    ),
                  ),
                ),
                IconButton(
                  icon: const Icon(Icons.send),
                  onPressed: (() {
                    String message = _messageController.text;

                    //nachricht senden
                    widget.websocket.sink.add(message);

                    //message aus textfeld löschen
                    _messageController.text = '';
                  }),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class ChatMessageTile extends StatelessWidget {
  final String message;

  const ChatMessageTile({super.key, required this.message});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
      child: Material(
        elevation: 5,
        borderRadius: BorderRadius.circular(10),
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
          child: Text(message),
        ),
      ),
    );
  }
}
