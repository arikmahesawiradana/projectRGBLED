#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiAP.h>
#include <Wire.h>
#include <RTClib.h>
#include <Time.h>
#include <Adafruit_NeoPixel.h>
#include <Preferences.h>
#define PIN      6
#define NUMPIXELS 7
#define DELAYVAL 500 // Time (in milliseconds) to pause between pixels

int detik;
int menit;
int jam;
String detikAlarm;
String menitAlarm;
String jamAlarm;

String redString = "0";
String greenString = "0";
String blueString = "0";
int pos1 = 0;
int pos2 = 0;
int pos3 = 0;
int pos4 = 0;

String header;

// Current time
unsigned long currentTime = millis();
// Previous time
unsigned long previousTime = 0; 
// Define timeout time in milliseconds (example: 2000ms = 2s)
const long timeoutTime = 2000;

const char *ssid = "yourAP";
const char *password = "yourPassword";

//share preferences
unsigned int detA;
unsigned int menA;
unsigned int jamA;
unsigned int merah;
unsigned int biru;
unsigned int hijau;

WiFiServer server(80);
RTC_DS1307 RTC;
Adafruit_NeoPixel pixels(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);
Preferences preferences;
TaskHandle_t Task1;
TaskHandle_t Task2;

void setup() {
  Serial.begin(115200);
  // Connect to Wi-Fi network with SSID and password
  Serial.print("Connecting to ");
  Serial.println(ssid);
  preferences.begin("ledku", false);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  // Print local IP address and start web server
  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  server.begin();

  pixels.begin();
  //inisialisasi alarm
  Wire.begin();
  RTC.begin();
  if (! RTC.isrunning()) {
    Serial.println("RTC is NOT running!");
  }
  detA = preferences.getUInt("detik", 0);
  menA = preferences.getUInt("menit", 0);
  jamA = preferences.getUInt("jam", 0);
  merah = preferences.getUInt("detik", 255);
  biru = preferences.getUInt("menit", 255);
  hijau = preferences.getUInt("jam", 255);
  xTaskCreatePinnedToCore(
                    startwifi,   /* Task function. */
                    "Task1",     /* name of task. */
                    10000,       /* Stack size of task */
                    NULL,        /* parameter of the task */
                    1,           /* priority of the task */
                    &Task1,      /* Task handle to keep track of created task */
                    0);          /* pin task to core 0 */                  
  delay(500);
  xTaskCreatePinnedToCore(
                    alarm,   /* Task function. */
                    "Task2",     /* name of task. */
                    10000,       /* Stack size of task */
                    NULL,        /* parameter of the task */
                    1,           /* priority of the task */
                    &Task1,      /* Task handle to keep track of created task */
                    0);          /* pin task to core 0 */                  
  delay(500);
}

void LEDcol(int red, int green, int blue){
  pixels.clear();
  pixels.setBrightness(10);
  pixels.setPixelColor(0, pixels.Color(red, green, blue));
  pixels.setPixelColor(1, pixels.Color(red, green, blue));
  pixels.setPixelColor(2, pixels.Color(red, green, blue));
  pixels.setPixelColor(3, pixels.Color(red, green, blue));
  pixels.setPixelColor(4, pixels.Color(red, green, blue));
  pixels.setPixelColor(5, pixels.Color(red, green, blue));
  pixels.setPixelColor(6, pixels.Color(red, green, blue));
  pixels.show();
}

void startwifi(void * pvParameters){
  WiFiClient client = server.available();   // Listen for incoming clients
  DateTime now = RTC.now(); //for alarm

  if (client) {                             // If a new client connects,
    currentTime = millis();
    previousTime = currentTime;
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected() && currentTime - previousTime <= timeoutTime) {            // loop while the client's connected
      currentTime = millis();
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();
                   
            // Display the HTML web page
            client.println("<!DOCTYPE html><html>");
            client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            client.println("<link rel=\"icon\" href=\"data:,\">");
            client.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">");
            client.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/jscolor/2.0.4/jscolor.min.js\"></script>");
            client.println("</head><body><div class=\"container\"><div class=\"row\"><h1>ESP Color Picker</h1></div>");
            client.println("<a class=\"btn btn-primary btn-lg\" href=\"#\" id=\"change_color\" role=\"button\">Change Color</a> ");
            client.println("<input class=\"jscolor {onFineChange:'update(this)'}\" id=\"rgb\"></div>");
            client.println("<script>function update(picker) {document.getElementById('rgb').innerHTML = Math.round(picker.rgb[0]) + ', ' +  Math.round(picker.rgb[1]) + ', ' + Math.round(picker.rgb[2]);");
            client.println("document.getElementById(\"change_color\").href=\"?r\" + Math.round(picker.rgb[0]) + \"g\" +  Math.round(picker.rgb[1]) + \"b\" + Math.round(picker.rgb[2]) + \"&\";}</script></body></html>");
            // The HTTP response ends with another blank line
            client.println();

            // Request sample: /?r201g32b255&
            // Red = 201 | Green = 32 | Blue = 255
            if(header.indexOf("GET /?r") >= 0) {
              pos1 = header.indexOf('r');
              pos2 = header.indexOf('g');
              pos3 = header.indexOf('b');
              pos4 = header.indexOf('&');
              redString = header.substring(pos1+1, pos2);
              greenString = header.substring(pos2+1, pos3);
              blueString = header.substring(pos3+1, pos4);
              merah = redString.toInt();
              biru = blueString.toInt();
              hijau = greenString.toInt();
              preferences.putUInt("merah", merah);
              preferences.putUInt("biru", biru);
              preferences.putUInt("hijau", hijau);
              LEDcol(merah, biru, hijau);
            }

            //request for alaram
            else if(header.indexOf("GET /?h") >= 0) {
              pos1 = header.indexOf('h');
              pos2 = header.indexOf('m');
              pos3 = header.indexOf('s');
              pos4 = header.indexOf('&');
              jamAlarm = header.substring(pos1+1, pos2);
              menitAlarm = header.substring(pos2+1, pos3);
              detikAlarm = header.substring(pos3+1, pos4);
              detik = now.second();
              menit = now.minute();
              jam = now.hour();
              detA = detikAlarm.toInt();
              menA = menitAlarm.toInt();
              jamA = jamAlarm.toInt();
              preferences.putUInt("jam", jamA);
              preferences.putUInt("menit", menA);
              preferences.putUInt("detik", detA);
              Serial.println(jam);
              Serial.println(menit);
              Serial.println(detik);
            }
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    client.stop();
    Serial.println("Client disconnected.");
    Serial.println("");
  }
}

void alarm(void * pvParameters){
  if(jam == jamA && menit == menA){
    pixels.clear();
    pixels.setBrightness(0);
  }
  delay(1000);
}

void loop() {

}
