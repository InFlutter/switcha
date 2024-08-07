import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  static const platform = MethodChannel("myPhone");

  bool isFlashOn = false;

  Future<void> toggleFlashLight(bool isOn) async {
    try {
      final String result =
          await platform.invokeMethod("toggleFlashlight", {"isOn": isOn});
      setState(() {
        isFlashOn = isOn;
      });
    } on PlatformException catch (e) {
      print("Xatolik mavjud: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Home Screen",
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
        centerTitle: true,
      ),
      body: Center(
        child: SwitchListTile(
          title: const Text("Flashlight"),
          value: isFlashOn,
          onChanged: (value) {
            toggleFlashLight(value);
          },
        ),
      ),
    );
  }
}
