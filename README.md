How to run App
Steps to Run the Weather App
Option 1: Clone the Repository and Build in Android Studio
1.	Clone the repository:
bash
Copy code
git clone https://github.com/dayanand1531/Weather.git
cd Weather
  2.	Open the project in Android Studio.
  3.	Build the project and run it on an emulator or a physical device connected via USB.
Option 2: Download and Install the APK
  1.	Download the APK file from this link: Weather App APK.
  2.	Transfer the APK to your device and install it by allowing installations from unknown sources.
How to Use the Weather App
1.	Launch the App:
    o	The app displays an EditText field for entering a city name and a Search button for fetching weather information.
2.	Search for Weather:
    o	Enter the city name in the search field and tap the Search button.
3.	App Behavior:
    o	If the API call is successful:
      1.	Weather information is displayed.
      2.	Data is stored in the local database and displayed from there.
    o	If the API call fails:
      1.	An error message is displayed in a Toast.
      2.	The last successful weather information (if available in the database) is displayed.
    o	If no data is available in the database:
      	The app will not display any weather information.
4.	Special Cases:
    o	First-time search with an API error:
      	Displays an error message, and no weather information is shown as there is no previous data.
    o	Internet not connected:
      	Weather data from the database (if available) is displayed.
      	New searches cannot be performed until the connection is restored.
5.	Database Updates:
    o	Every successful search updates the local database with the latest weather data.

Why You Made the Choices You Did
Explain design and technology choices thoughtfully:

Technologies and Frameworks:
•	Jetpack Compose: Used for the UI because it’s declarative, modern, and simplifies UI development, especially for reactive updates.
•	Hilt for Dependency Injection: Ensures cleaner code and easier management of dependencies.
•	Coroutines and Flow: Enables efficient, asynchronous operations for fetching data (e.g., weather updates).
•	Room Database: Provides offline capabilities for storing and retrieving weather data.

Design Decisions:
•	MVVM Architecture:
  o	Decouples UI logic from business logic, ensuring easier testing and scalability.
  o	ViewModel handles the data, making the UI simpler and more reusable.
•	Connectivity Check:
  o	Adds robustness by switching between live and offline modes based on internet availability.
•	Clean and Modular Code:
  o	Organized the project into modules such as UI, Data, and Domain, ensuring maintainability.
•	User Experience (UX):
  o	Simple and intuitive UI with features like live weather updates and offline fallback, catering to both online and offline users.
