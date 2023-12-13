import 'package:flutter/material.dart';
import 'dart:convert';

@immutable
class Message {
  final String username;
  final String message;

  const Message({required this.username, required this.message});

  factory Message.fromJson(String s) {
    Map<String, dynamic> jsonObject = jsonDecode(s);
    return Message(
        username: jsonObject['username'], message: jsonObject['message']);
  }
}
