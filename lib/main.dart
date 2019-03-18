// Copyright 2017 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:async';

import 'package:flutter/material.dart';
//import 'package:url_launcher/url_launcher.dart';
import 'url_launcher.dart';
import 'package:flutter/services.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'URL Launcher',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'URL Launcher'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = const MethodChannel('samples.flutter.io/battery');
  Future<void> _launched;
  String _phone = '09024922369';

  Future<void> _launchInBrowser(String url) async {
    if (await canLaunch(url)) {
      await launch(url, forceSafariVC: false, forceWebView: false);
    } else {
      throw 'Could not launch $url';
    }
  }

  Future<void> _launchInWebViewOrVC(String url) async {
    if (await canLaunch(url)) {
      await launch(url, forceSafariVC: true, forceWebView: true);
    } else {
      throw 'Could not launch $url';
    }
  }

  Future<void> _launchInWebViewWithJavaScript(String url) async {
    if (await canLaunch(url)) {
      await launch(
        url,
        forceSafariVC: true,
        forceWebView: true,
        enableJavaScript: true,
      );
    } else {
      throw 'Could not launch $url';
    }
  }

  Widget _launchStatus(BuildContext context, AsyncSnapshot<void> snapshot) {
    if (snapshot.hasError) {
      return Text('Error: ${snapshot.error}');
    } else {
      return const Text('');
    }
  }

  Future<void> _makePhoneCall(String url) async {
    if (await canLaunch(url)) {
      await launch(url);
    } else {
      throw 'Could not launch $url';
    }
  }

   // Get battery level.
  String _batteryLevel = 'Unknown battery level.';

  Future<void> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level at $result % .';
    } on PlatformException catch (e) {
      batteryLevel = "Failed to get battery level: '${e.message}'.";
    }

    setState(() {
      _batteryLevel = batteryLevel;
    });
  }



  @override
  Widget build(BuildContext context) {
    const String toLaunch = 'https://flutter.io';
    return Scaffold(
     // appBar: AppBar(
     //   title: Text(widget.title),
     // ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                  onChanged: (String text) => _phone = text,
                  decoration: const InputDecoration(
                      hintText: 'RepeatCall Telephone number')),
            ),
            RaisedButton(
              onPressed: () => setState(() {
                    _launched = _makePhoneCall('tel:$_phone');
                  }),
              child: const Text('Make phone call'),
            ),
            const Padding(padding: EdgeInsets.all(16.0)),
            RaisedButton(
              child: const Text('Call me'),
              onPressed: _getBatteryLevel,
            ),
            Text(_batteryLevel),
            
            const Padding(padding: EdgeInsets.all(16.0)),
            RaisedButton(
              child: Text('Get Battery Level'),
              onPressed: _getBatteryLevel,
            ),
            Text(_batteryLevel),
            const Padding(padding: EdgeInsets.all(16.0)),
            RaisedButton(
              onPressed: () => setState(() {
                    _launched = _launchInWebViewOrVC(toLaunch);
                    Timer(const Duration(seconds: 5), () {
                      print('Closing WebView after 5 seconds...');
                      closeWebView();
                    });
                  }),
              child: const Text('Launch in app + close after 5 seconds'),
            ),
            const Padding(padding: EdgeInsets.all(16.0)),
            //FutureBuilder<void>(future: _launched, builder: _launchStatus),
          ],
        ),
      ),
    );
  }
}


//flutter create --org com.sumitomo --template=plugin -i swift -a java ticketcall
