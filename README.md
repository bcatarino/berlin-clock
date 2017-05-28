# berlin-clock

A simple application to return a multi-line string representing a berlin clock, written by Bruno Catarino.

## Dependencies
* jre 8
* scala 2.12
* sbt

## Assumptions
* Not entirely sure if the problem requires the seconds light to be off on the even seconds or odd seconds, so I'm assuming odd = on and even = off. On a real world scenario, I'd clear it out with the Product Owner or the person responsible for providing the requirements.
* I'm also not sure which color the seconds light should have, so I assumed yellow.
* The way I understand the problem, it's not possible to have 24 hours (that's 0 instead), so there's no use case where the bottom 4 lights for the hours (2nd row of 4) are on. Unless I'm missing something, I couldn't find a test case for that 4th light in that row.

## Design decisions
* I will use 'R' for "red light" and 'Y' for "yellow light". For "lights off", I'll use 'O'.
* Not sure if was a requirement to present a main to run the system. I suspect it wasn't but I implemented a quick console Application that simply displays the clock every second and allows to terminate the execution with 'Enter'.
* I could have "improved" the code a bit by creating separate classes to deal with each time component (second, minute and hour), but at the same time, it felt like I'd be over-engineering my solution. Maybe something that could be brought up at a code review.

## Run Clock

> sbt run

It will launch a console application which shows the clock every second.

You can stop by pressing "Enter" at any time.

Note: There's a bug when running from sbt and hitting "Enter" to terminate. It will throw a `sbt.TrapExitSecurityException` which I'd need more time to figure out. Considering having a main doesn't even seem like it was a requested functionality, I decided not to spend too much time trying to figure out this issue as of now, especially since the intended behaviour is still present.

## Run unit tests

> sbt test
