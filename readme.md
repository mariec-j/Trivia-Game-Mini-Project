# About Me: Trivia Mini Project

## Objective
A short, 10 question trivia game meant for one player.

## Features
### Required Functions:

- Welcome Screen
  - Welcome screen which includes a button to play the game
- Question Display
  - A question display similar to Kahoot with a question and 4 options listed in a 2x2 grid.
  - The display also includes a 15-second timer
- Game Flow
  - Go through 10 set questions, with each having 4 options to choose from.
  - A score is given based on how many correct
  - A popup will display based on what happens, which will be:
    - Correct answer chosen
    - Incorrect answer chosen
    - Timer running out
- Score Tracking
  - Tracks a score, for every question answered correctly add 1.
- Results Screen
  - Displays a screen that includes the user-input username from the player
  - Displays a score / question amount along with a %.

### Extra Functions:
- Question Categories
  - ECE questions
  - Deadlock (a video game) questions
- Timer per Question
  - 15-second timer per question
  - Counts down on the question display
- UI Design
  - Randomized colors for answer options
  - Results screen colored based on score

## Files

- `MainClass`
- `QuestionsLoaderTest`
- `Class_Src`
  - `CardLayoutFrame`
  - `QuestionsLoader`
- `Question_Text_Files`
  - `DeadlockQuestions`
  - `DeadlockRubric`
  - `ECEQuestions`
  - `ECERubric`