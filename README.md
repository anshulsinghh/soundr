## General Info
Soundr is a widget that was developed for HackSC 2019. The project is still a work in progress, but the core functionality of the app is stable. To run Soundr, simply open the project in Eclipse and run the project. Soundr works bug-free on Windows, but will most likely crash on Mac. We are still working on bringing more extensive support to Mac.

## Inspiration
Amongst us and our friends, we all refrained from listening to loud music because we would become unaware of our surroundings. If someone tried to talk to us while we were watching movies or listening to music, we would almost never hear them. We all wanted a way to listen to entertainment without being unaware of our own surroundings.

## What it does
Soundr is an app which sends a notification to the user when their ambient sound rises above a given threshold. The user can choose the threshold in the app's control panel, and they will be alerted when the sound rises above their chosen threshold.

## How we built it
We built the project using Java and its built-in javax sound libraries. We used the javafx library for creating the user interface, and used java's threading library to assist us in our task.

## Challenges we ran into
We ran into many challenges in our project. The biggest one for us was decoding the byte stream that java's sound library provides. Interpreting the byte stream and retrieving ambient volume levels was particularly difficult in the project. Other challenges included creating the user interface, as none of us had any experience in designing and implementing java user interfaces. Another challenge we encountered was threading the byte stream alongside the user interface, and ensuring that the front and back-end of the project communicated properly.

## Accomplishments that we're proud of
We're happy to say that the project's core works flawlessly, and that it does exactly what it was designed to do. It sends quality notifications to the user when ambient noise surpasses the user's defined threshold. All of us can see ourselves using this widget in our personal lives, and we are excited that the widget worked successfully.

## What we learned
We learned a lot about how to implement java's sound libraries. Byte streams were a new topic for us, so it was a learning expereince for us to deal with the byte arrays. We also learned about the importance of threading in java, as it allows functions to execute simultaneously. This was our first Hackathon project, so we also learned logistics like using GitHub in our project. We also learned how to be open communicators, and how to develop an end-to-end computer science project.

## What's next for soundr
We would love to add more features to Soundr for the future. We all agree that the user interface could be improved in the future. Additionally, we also hope to add dynamic sensing to Soundr. Currently the app uses a static threshold to determine if a sound is loud, and we would like to add a dynamic sensing capability to Soundr. If the user's environment stays loud or quiet for long periods of time, Soundr would be able to adjust to these conditions.
