# SensorLympics

Sensor based Olympic Games

## How to build 
  - Generate APi key from: https://openweathermap.org/api <br><br>
    - How to implement API key:  <br><br>
         a. Add the API key to your local.properties file: <br>
               - apiKey="Your Key" <br> <br>
          b. Add these two lines to the root level of your app-level build.gradle file: <br>
               - def localProperties = new Properties() <br>
                 localProperties.load(new FileInputStream(rootProject.file("local.properties"))) <br><br>
          c. Add this following line to your app-level build.gradle file: <br>
               - android { <br>
                 defaultConfig { <br>
                     // ...<br>
                 buildConfigField "String", "API_KEY",localProperties['apiKey'] <br>
                 } <br>
                 } <br> <br>
          d. Sync Gradle and build the project. You can now reference the key: <br><br>
          e. var appid: String = BuildConfig.API_KEY; (Line 19 in WeatherApi) <br>
        
 If ApiKey goes red, press Make Project button or use Ctrl+F9 
        
## Games
  - Single player games:
    - Magneto Game
    - Pressure Game
    - Ball Game

  - Multiplayer game:
    - Tic Tac Toe



## Screenshots
<img src = "https://user-images.githubusercontent.com/71440030/157437779-48efd985-9f71-48a0-af22-614135eaadec.JPG" width="250">

## License
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
