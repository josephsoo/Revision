# Revision
## Gamified spaced repetition
___
This application is a **gamified education application**, functionally similar to Quizlet but also
builds upon the principles of **spaced repetition**. In this software, users will be presented with a question and the 
a bank of possible answers, and will need to **identify the correct answer**. 

To allow the answers presented to the user to be of a similar type,
card can be organized into decks. Within those decks, the answers to the question can be mixed with other cards to form
an answer bank,
so the user must be able to identify the correct answer.

I find existing spaced repetition to be very **technical** and thus require a strong
understanding of the underlying scheduling algorithm to make best use of them. In addition, they often require
users to evaluate their own performance on a card, which may lead to biases. This application
aims to eliminate that, while providing a fun experience for users.

## User Stories
___
- As a user, I want to be able to add a card to a deck
- As a user, I want to be able to answer a card's question given some possible answer choices from that deck
- As a user, I want to be able to rename a deck
- As a user, I want to be able to view the cards in a deck
- As a user, I want to be able to save my decks to a file (if I so choose)
- As a user, I want to be able to be able to load my decks from file (if I so choose)

Persistence was inspired by JsonSerializationDemo:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

Splash Screen generated by Bing Ai


# Instructions for Grader
___
- You can generate the first required action related to adding Xs to a Y by clicking the "Add Deck button" and providing a non-empty string as a name
- You can generate the second required action related to adding Xs to a Y by selecting (clicking on) a deck, then clicking the button "Modify Deck", then clicking "Rename deck", then providing a non-empty string as a new name 
- You can locate my visual component by opening the application (press anywhere to quit the splash screen)
- You can save the state of my application by clicking on the "Save Decks" button
- You can reload the state of my application by clicking on the "Load Decks" button