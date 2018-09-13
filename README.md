# TextSearchEngine 
## ABOUT
TextSearchEngine project is a command line driven text search engine to compute files ranking for files in given folder. 

The rank score for file:
* Is 100% if a file contains all the input words.
* Is 0% if it contains none of the words
* It is between 0 and 100% if it contains only some of the words depending on used algorythm.   

The program use Template Method design pattern, so you can design your own algoryth using abstract class: FileSearchAlgorythmTemplate.java without changes in Main class.

The algorithm recognizes words as non special characters and numbers. It is not resistant to differences in the size of letters so:
"AlA" is not equals "Ala".

Project contains JUnit tests.
Search depth for the folder is set to 1.

## HOW TO START
Using .jar file which is located in /RUN folder.

For example type:   
```
java -jar TextSearchEngine.jar "PathToDirectory"
```
"PathToDirectory" should be absolute, for example: "C:\Users\SuperUser\Desktop\PROjekty\FOLDER"

EXAMPLE:   
filename1 contains: "Ala ma ***   kot"   
fielname2 contains: "W imie zasad"   
```   
search>Ala ma kota[ENTER]   
filename1: 66% //(because it contains two of three given words - [Ala, ma])   
search>    Ala ma kot zasad[ENTER]   
filename1: 75% //(because it contains three of four given words)   
filename2: 25% //(because it contains one of four given words)   
search>:quit
```

## DOWNLOAD A PROJECT
You can download project from git.
You can use given folder for testing purpose: /FOLDER.


