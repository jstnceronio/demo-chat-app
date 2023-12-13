import 'package:flutter/material.dart';
import 'dart:convert';

@immutable
class Message {
  final String username;
  final String message;

  const Message({required this.username, required this.message});

  factory Message.fromJson(String s) {
    String test =
        s.replaceAll("\'", "\"").replaceAll("{", "\'{").replaceAll("}", "}\'");
    print(test);
    String string = '{"username":"jg", "message":"dsf"}';

    print(string);
    Map<String, dynamic> user = jsonDecode(test);
    print(user['username']);
    // print(json.decode(s.replaceAll("\'", "\"")));
    return Message(username: 'test', message: 'test');
  }
}
