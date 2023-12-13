import 'package:chat_app_mobile_client/service/message.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

class ChatScreen extends StatefulWidget {
  final WebSocketChannel websocket;
  final String ownUsername;

  const ChatScreen({
    super.key,
    required this.ownUsername,
    required this.websocket,
  });
  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final List<Message> _messages = [];
  final TextEditingController _messageController = TextEditingController();
  final ScrollController _listViewController = ScrollController();

  @override
  void initState() {
    super.initState();
    widget.websocket.stream.listen(
      (value) {
        Message message = Message.fromJson(value);
        setState(() {
          _messages.add(message);
        });

        // Scrollen zum unteren Ende der Liste
        if (_listViewController.hasClients) {
          if (_listViewController.position.maxScrollExtent > 0) {
            _listViewController.animateTo(
              _listViewController.position.maxScrollExtent + 30,
              duration: const Duration(milliseconds: 1),
              curve: Curves.linear,
            );
          }
        }
      },
      onDone: () {
        print('connection closed');
      },
    );
  }

  @override
  void dispose() {
    widget.websocket.sink.close();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        actions: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                shadowColor: Colors.red, // Schattenfarbe
                backgroundColor: Colors.red,
                elevation: 10, // Schattenhöhe
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(30), // Rundung der Ecken
                ),
                padding: const EdgeInsets.symmetric(
                    horizontal: 30, vertical: 0), // Abstand innen
              ),
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text(
                'Verlassen',
                style: TextStyle(fontSize: 15), // Schriftgröße
              ),
            ),
          ),
        ],
      ),
      body: Column(
        children: [
          const SizedBox(
            height: 15,
          ),
          Expanded(
              child: ListView.builder(
                  controller: _listViewController,
                  itemCount: _messages.length,
                  itemBuilder: ((context, index) {
                    return ChatMessageTile(
                        message: _messages[index],
                        ownUsername: widget.ownUsername);
                  }))),
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

                    //remove keyboard after sending a message
                    FocusScope.of(context).requestFocus(FocusNode());
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
  final Message message;
  final String ownUsername;

  const ChatMessageTile(
      {super.key, required this.message, required this.ownUsername});

  @override
  Widget build(BuildContext context) {
    //meine nachrichten auf der rechten seite
    if (message.username == ownUsername) {
      return Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 270),
            child: Container(
              width: 270,
              padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
              child: Material(
                color: const Color(0xff218aff),
                elevation: 5,
                borderRadius: const BorderRadius.only(
                  topLeft: Radius.circular(10),
                  bottomLeft: Radius.circular(10),
                  topRight: Radius.circular(10),
                ),
                child: Container(
                  padding:
                      const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
                  child: Text(message.message),
                ),
              ),
            ),
          ),
        ],
      );
    } else if (message.username == "System") {
      return Center(
          child: Text(
        message.message,
        style: const TextStyle(fontWeight: FontWeight.bold),
      ));
    } else {
      //alle anderen erhaltenen, messsages
      return Row(children: [
        ConstrainedBox(
          constraints: const BoxConstraints(maxWidth: 270),
          child: Container(
            padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
            child: Material(
              color: Colors.grey.shade200,
              elevation: 5,
              borderRadius: const BorderRadius.only(
                topRight: Radius.circular(10),
                bottomRight: Radius.circular(10),
                topLeft: Radius.circular(10),
              ),
              child: Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
                child: Text(message.message),
              ),
            ),
          ),
        ),
        Text(
          message.username, // Name des Absenders
          style: TextStyle(
            fontSize: 10, // Kleine Schriftgröße für den Absender
            color: Colors.grey, // Farbe des Textes
          ),
        )
      ]);
    }
  }
}
