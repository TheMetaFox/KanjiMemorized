# KanjiMemorized
One of Japanese's three scripts, Kanji, is often a highly contested subject for beginner to advanced learner's of japanese leaving people many people frustrated or intimidated at it's difficulty or abandoning the effort to study kanji or even Japanese as a whole. KanjiMemorized is a mobile application that uses cognitive psychology and evidence-based learning principles to accelerate Japanese learners' acquisition of essential kanji meanings, while simplifing the process, decreasing mental fatigue, and saving time.

Kanji as an logographic script are symbols with attached meaning that are often used in Japanese vocabulary, nouns, verbs, and adjectives. Oftentimes, Kanji will be composed of smaller parts that also heppen to be their own distinct kanji. This app is design to use that nature of Japanese kanji to it's fullest, as a bases for all studying of kanji. As users learn, they start of learning the more symbolically simple kanji; then, as familiarity grows, that begin to study Kanji have components that the learner is already familiar with. This method of studying encourges the brain to encode the meaning of kanji at a higher level, meaning it stays in memory for longer.

A visual representation of the interconnected nature of Japanese Kanji:

<img width="500" alt="Screenshot 2024-11-15 114759" src="https://github.com/user-attachments/assets/ef4859ad-a2be-43ed-931a-6ff038136bf9">
<img width="500" alt="Screenshot 2024-11-15 115310" src="https://github.com/user-attachments/assets/f14c7ac9-d190-4945-9ded-93be99b95d85">
<img width="500" alt="Screenshot 2024-11-15 115156" src="https://github.com/user-attachments/assets/f30af289-d913-406a-b377-14bec44d583b">
<img width="500" alt="Screenshot 2024-11-15 115034" src="https://github.com/user-attachments/assets/b4765121-3e1f-496b-be9b-d29d58aff33f">

https://github.com/user-attachments/assets/ec753403-a907-4ea7-b54a-33fc91911fc5

Current UI:

<img width="200" alt="Screenshot_20241115_132012_Kanji" src="https://github.com/user-attachments/assets/11519377-a1fa-4164-bc0a-0baf919366c9">
<img width="200" alt="Screenshot_20241115_131949_Kanji" src="https://github.com/user-attachments/assets/d2dd0d69-185a-4cfc-87e0-f59f4a6543f4">
<img width="200" alt="Screenshot_20241115_131942_Kanji" src="https://github.com/user-attachments/assets/3aef48b9-c5e9-4aea-af6f-3ec0ad778fdc">
<img width="200" alt="Screenshot_20241115_132120_Kanji" src="https://github.com/user-attachments/assets/7c24f92d-a120-4fcc-8712-71254ac1ce83">
<img width="200" alt="Screenshot_20241115_132102_Kanji" src="https://github.com/user-attachments/assets/d059c06b-85b7-40b1-818d-4ee08525a448">

## How To Use
1. On your mobile device, go to the 'Releases' section of this repository and click on 'tags'
2. Select the desired version of the app you'd like to install and download the .apk file
3. ...

## App Guide
### Study Modes
__Guided Study:__ Creates a queue of kanji to study out of the leftover daily new kanji and the kanji ready for review.

__Learn:__ Let's you study new kanji past the daily new kanji limit.

__Review:__ Limits studying to the kanji that are needed for review.
###### - Note: If you have more kanji for review than you'd like, use the review mode; if you finished with all your daily new kanji and review kanji and don't want to increase you're daily new kanji limit, use the learn mode, but don't overdo it or you'll end up overwhelmed by reviews in the future.
### Kanji Info
###### - Note: If you want more info on kanji, tapping on the kanji character will take you to [Jisho.org](https://jisho.org/)'s entry of that kanji.
__Retention:__ a decimal from 0 to 1 that represent how likely a kanji is to be correctly recalled.

$$
e^{-\frac{m}{1440 \times d}}
$$

$m$ = number of minutes since the last successful review of a kanji<br/>
$d$ = durability, a value that guages how strongly a kanji is held in memory<br/>
$e$ = euler's number, produces the curve that represents the rate of forgetting<br/>

__Durability:__ A numberical representation of how familiar a kanji is; influences the duration of time between your last and next review.

__Ease:__ A numerical representation of how easy a kanji is to learn; influences the frequency of a kanji's reviews.

__Meanings:__ The meaning to be associated with the kanji.

__Strokes:__ The number of strokes or broken lines in a kanji character, used to measure kanji complexity.

__Components:__ Kanji that make up or compose the kanji in subject.
### Statistics Data

__Unlocked Kanji:__ Kanji that are availibale for study, which means that all it's component kanji have a sufficent retention value

__Kanji Count:__ Shows the distribution of the familiarity of kanji by their durability values; Unknown kanji have a durability of 0; Known kanji have a durability greater than 0 and less than 100; and, Mastered kanji have a durability greater than 100.

__Review Forecast:__ A bar graph of kanji to be reviewed organized by their calculated distance away from the current time.
### Setting Options
__Daily New Kanji:__ Change the upper limit of new kanji to be studied daily. Modify this number based on how quickly you learn new kanji and handle reviews, as well as how much time you want to spend on each.

__Intial Ease:__ Change the ease value that kanji are set at when you first study them. Increase this value to spend less time on reviewing new kanji if you find that recently studied kanji appear too frequently.

__Retention Threshold:__ Change the retation value that dicatates when a kanji is ready to study. Increase this value if you find your getting more incorrect recall than you'd like; or, decrease the value if you're willing to possibly get more cards incorrect inexchange for seeing cards less often.
