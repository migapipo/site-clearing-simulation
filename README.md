### Building, Testing and Execution Instructions
<details><summary>Prerequisites on a mac</summary>

- Java 8 sdk

</details>

<details><summary>Build and Execution</summary>

- To build the application:
```bash
./gradlew clean build
```
-  To run the jar:
```bash
java -jar build/libs/site-clearing-simulation.jar 
```
- If you run above command successfully, you will see an instruction asking for the input site map file name: 
```bash
"To start the application, please enter the correct file path to open (with extension like fileName.txt) : "
```
- Please type in the site map text file name and continue (press enter):
```bash
input/input1.txt
```
</details>


### Site Clearing Simulation Usage

**1.** Follow above steps to build and run this application.

**2.** If it runs successfully, a statement will be output first:

```bash
To start the application, please enter the correct file path to open (with extension like fileName.txt) : 
```
As a trainee, you need to input a valid site map file name with extension like fileName.txt (e.g "input/sitemap
.txt") to start the application. You can also put different custom sitemap files in the ```input``` folder for further
 usage.


**3.** Once the input file name input, the application will start with the site map file provided and displayed
it on the console for the trainee with a welcome message: 
 
 ```bash
Welcome to the Aconex site clearing simulator. This is a map of the site:
     
     o o t o o o o o o o
     o o o o o o o T o o
     r r r o o o o T o o
     r r r r o o o o o o
     r r r r r t o o o o
     
     The bulldozer is currently located at the Northern edge of the site, immediately to the West of the site, and facing East.
     
     (l)eft, (r)ight, (a)dvance <n>, (q)uit:
```


<details><summary>Input File Requirements</summary>

The input site map should be defined with one character per square of the site. Each row must have the same number of
 characters. 
</details>


<details><summary>Square Types on Site Map </summary>
 - Plain land is marked with the letter ‘o'
 - rocky land is marked with the letter ‘r’
 - removable trees are marked with the letter ‘t’
 - trees that must be preserved are marked with the letter ‘T’
 </details>
 
 The initial position of the bulldozer will be outside of the site, to the left of the top left (northwest) square of
  the site, facing towards the east. Initial position (-1,0), facing Direction EAST. The bulldozer will never be blocked
   (by an unremovable tree) from entering the site by driving east.
  
**4.** The available commands are: ```(a) <n>```, ```l```,```r```,```q```.
 - Advance ```(a) <n>```: this command takes a positive integer parameter to define the number of squares the bulldozer should move
  forwards (in whatever direction it is currently facing); (e.g.```a 4```, ```aa 4``` are both valid advance commands)
 - Left
  ```l```: turn the bulldozer (on the spot) 90 degrees to the left of the direction it is facing;
 - Right
  ```r```: turn the bulldozer 90 degrees to the right;
 - Quit 
 ```q```: end the simulation.
 
**5.** Commands are executed as soon as the user presses “Enter” until one of the following simulation ending events
  occurs:
   - there is an attempt to navigate beyond the boundaries of the site; The application will output the last position
    where the bulldozer stays on the map. 
    e.g.
    
    The Bulldozer is out of boundary at (5, 1).
    The simulation has ended at your request.
   - there is an attempt to remove a tree that is protected; The application will output the position of the protected 
    tree which the bulldozer is attempting to remove.
      
    Current location(0, 4) is a protected tree 
    The simulation has ended at your request.
    
   - the trainee enters the quit command.

    The simulation has ended at your request.
**6.** The simulation ends and commands are no longer accepted. A list of commands entered, and an itemised expense
 report
 will be displayed on the console.
 
 ```
The simulation has ended at your request.
These are the commands you issued:
advance 4, turn right, advance 4, turn left, advance 2, advance 4, turn left, quit
The costs for this land clearing operation were:
Item						Quantity		Cost
communication overhead				7		7
fuel usage			            	19		19
uncleared squares				34		102
destruction of protected tree		        0		0
paint damage to bulldozer			1		2
----
Total 										 130
Thank you for using the Aconex site clearing simulator.

```

