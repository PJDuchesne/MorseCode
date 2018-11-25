# Identification
MorseCode App, CSCI 4176 Mobile Computing Project (Fall 2018)
Group 16:
* Dinesh Sai Bayireddi   B00791584
* George Faraj           B00638341
* Paul Duchesne          B00332119
* Samarth Raval          B00812673
* Tom Smith              B00694293

For questions about this project send an email to [Paul](mailto:paulduchesne1337@gmail.com).

This project used GitHub for version control and can be accessed [here](https://github.com/PJDuchesne/MorseCode).

# Project Summary

The main concept behind this application is to provide a method for users to KKKKK. The key features of this application include, allow the user to translate text to Morse code and vice versa, and follow a set series of lessons to learn Morse code. The progress of the aforementioned lessons can be tracked if the users makes an account (another key feature of the application).

The application allows the user to .... This greatly....


## Libraries
**Components Libraries (Button,TextView,):** The

**camera2/CameraManager :** It can also be used to convert a JSON string to an equivalent Java object. Source [here](https://github.com/google/gson)

**Vibrator :** It can also be used t


**google-gson:** Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Source [here](https://github.com/google/gson)

## Installation Notes
This version of the application will run on the same version of. There have been adjustments to the gradle file Build Tools 28.0.3 may be needed
Please note: The vibration and flash components do work and have been tested on several physical devices. However, the android studio emulator does not have anyway of outputting if the phone's light is on or if it is vibrating.

## Code Examples

**Problem 1: PAUL WITH THE DELAY THING ADD THE TAILCHAIN SOLUTION**

EXPLAIN THE HEAD TAIL SOLUTION.
```
// The method we implemented that solved our problem
public static VOID THING(VOID * THING?) {
    //YOUR CODE HERE
}

// Source: YOUR MIND
```

**Problem 2: Using custom made sounds with varying frequency**

Since outputting sound from a static MP3/WAV file limits user freedom the AudioTrack library to generate tones.
Despite the online documentation provided by android on this libraries usage using the library was tricky as it required generating an array of bytes that represent sound waves.
To solve this an online tutorial for creating an 8-bit music generating app was followed (link below).
The generateSound function (shown below) from the tutorial solved the problem with using AudioTrack.
```
private void generateSound(int frequency, int duration) {
    int sampleRate = 44100;
    soundData = new byte[sampleRate * duration];
    for (int i = 0; i < soundData.length; i++) {
        byte sample = (byte) ( Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255 );
        soundData[i] = sample;
    }
}
// Source: Progur.com How to Create 8-bit Music on Android [1]
```

**Problem 3: Efficiently Decoding Morse to Letters**



![Morse Trie Search Algorithm](https://github.com/PJDuchesne/MorseCode/blob/georgeRefactor/ProjectDocumentation/MorseTrieBranchAlgorithm_Diagram.png "Morse Trie Search")

## Feature Section
List all the main features of your application with a brief description of each feature.

Morse Feedback:

Morse Translation:

User Account System:

## Final Project Status
Write a description of the final status of the project. Did you achieve all your goals? What would be the next step for this project (if it were to continue)?
Overall the project was a success, asdadsalsakda

Our original scope of the applications intended functionality is as follows:

#### Minimum Functionality
- Input Morse code as taps and display corresponding text. (Completed)
- Take input text and translate it into Morse code as text, audio, and haptic feedback. (Completed)
- Provide an options page to modify timing parameters for Morse code I/O. (Completed)

#### Expected Functionality
- Training course for learning Morse code (With a minimum of 5 lessons). (Completed)
- User login and corresponding database, which stores basic analytics. (Completed)
- Output Morse code as light using the phone’s flashlight. (Completed)

#### Bonus Functionality
- Advanced account analytics. (Completed)
- Utilize Google’s audio and visual API to provide audio and visual inputs. (Completed)
- Implement two-way Morse Code communication between two phones. (Completed)
- Provide an option to input Morse code as light. (Completed)

## Sources
What to include in your project sources:
- Stock images
- Design guides
- Programming tutorials
- Research material
- Android libraries
- Everything listed on the Dalhousie Plagiarism and Cheating pages(https://www.dal.ca/dept/university_secretariat/academic-integrity/plagiarism-cheating.html)

[1] "How to Create 8-bit Music on Android", Progur.com, 11 December 2016. [Online]. Available: https://progur.com/2016/12/how-to-create-8-bit-music-on-android.html.