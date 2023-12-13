import 'package:chat_app_mobile_client/app_config.dart';
import 'package:chat_app_mobile_client/view_chat.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyWidget(),
    );
  }
}

class MyWidget extends StatelessWidget {
  MyWidget({super.key});

  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(left: 20, right: 20),
              child: TextFormField(
                controller: _controller,
                decoration: InputDecoration(
                  labelText: 'Benutzername eingeben',
                  fillColor: Colors.white,
                  filled: true,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30),
                    borderSide: BorderSide.none,
                  ),
                  contentPadding:
                      const EdgeInsets.symmetric(horizontal: 30, vertical: 15),
                ),
              ),
            ),
            const SizedBox(height: 20),
            Center(
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  shadowColor: Colors.blueAccent, // Schattenfarbe
                  elevation: 10, // Schattenhöhe
                  shape: RoundedRectangleBorder(
                    borderRadius:
                        BorderRadius.circular(30), // Rundung der Ecken
                  ),
                  padding: const EdgeInsets.symmetric(
                      horizontal: 30, vertical: 15), // Abstand innen
                ),
                onPressed: () {
                  String textValue = _controller.text;

                  if (textValue.isEmpty) {
                    textValue = "Anonym";
                  }

                  final WebSocketChannel websocket = IOWebSocketChannel.connect(
                      AppConfig.websocketUrl,
                      headers: {
                        'username': textValue,
                      });

                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => ChatScreen(
                              ownUsername: textValue,
                              websocket: websocket,
                            )),
                  );
                },
                child: const Text(
                  'Verbinden',
                  style: TextStyle(fontSize: 20), // Schriftgröße
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
