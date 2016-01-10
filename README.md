# HackTheBar

## [Third Place Winner!](http://devpost.com/software/barflow)

Inspiration
Some people just want to have a beer. Those people who are busy dancing, chilling with friends, or trying to catch a bus don't have time to wait for the bartender...

What it does
It creates a virtual tab through an android app that tracks payments throughout the night and allows users to pay their bills through their phones.
It saves users time and gets them their drinks much faster.
It facilitates social interaction by allowing users easy ways of purchasing drinks for each other.
It allows bartenders access to detailed data and analytics directly from users.
How I built it
We wrote server side code in python and deployed it to a raspberry pi which takes inputs from volumetric flow sensors in order to track the amount of beer poured at one time.

We wrote client software for Android that interfaces with the raspberry pi server to open tabs and track how much beer was dispensed and how much money a user would be charged

We created functions to scrape real time data off of the sensor box to create a faster app.

Challenges I ran into
Socket communication was a challenge, so was properly configuring the raspberry pi to get sensor input.

Accomplishments that I'm proud of
We're proud of finishing a functional prototype.

What I learned
How server-client models work.

What's next for Bar Flow
Expanding the range of data collected by the server, Implementing more secure payment options.

