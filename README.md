```Click``` on list items to expand

#### Building, Testing and Execution Instructions

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


#### Site Clearing Simulation Usage (with examples)

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


##### Input File Requirements

The input site map should be defined with one character per square of the site. Each row must have the same number of
 characters. 

##### Square Types on Site Map 
 - Plain land is marked with the letter ‘o'
 - Rocky land is marked with the letter ‘r’
 - Removable trees are marked with the letter ‘t’
 - Trees that must be preserved are marked with the letter ‘T’
 
##### Initial State
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
    where the bulldozer stays on the map. For example: 
    
    The Bulldozer is out of boundary at (5, 1).
    The simulation has ended at your request.
   - there is an attempt to remove a tree that is protected; The application will output the position of the protected 
    tree which the bulldozer is attempting to remove. For example: 
      
    Current location(0, 4) is a protected tree 
    The simulation has ended at your request.
    
   - the trainee enters the quit command. The application will output the following statement to let the trainee know
    the application has ended: 

    The simulation has ended at your request.
**6.** Once the simulation ends, commands are no longer accepted. The application will display a list of commands
 entered, and an itemised expense report on the console.For example: 
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


#### Documentation

<details><summary>Approach</summary>

- Java 8, Junit 5 
- Used a TDD (Test Driven Design) Approach within IntelliJ IDEA
- Each source file has a javadoc comment which explains its functions and what part that file plays in the
 solution.
- Each main feature/capability of this program has a corresponding test due to the TDD approach that has been followed.

</details>


<details><summary>Folder Structure</summary>


```bash
├── controller
│   └── GameController.java
├── entity
│   ├── AdvanceCommand.java
│   ├── Bulldozer.java
│   ├── Commands.java
│   ├── DirectionCommand.java
│   └── SiteMap.java
├── enums
│   ├── Activity.java
│   ├── CommandType.java
│   ├── Direction.java
│   ├── Item.java
│   └── SquareType.java
├── exceptions
│   ├── CutTreeException.java
│   ├── OutOfBoundaryException.java
│   └── ParseException.java
└── Main.java
```
</details>


<details><summary>Program Structure</summary>


- ```controller``` folder includes only one ```GameController``` class.
    - ```GameController``` 
 
    Taking the idea of Controller pattern from GRASP, GameController class is used as the single handler for all
     kinds of requests coming to the application. Main functions of this program including ```ReadCommands
     ```, ```getCommandListString```, ```generateCostReport```, ```computeUnclearedSquareAndCommunication``` are
      encapsulated in this class. 
- ```entity``` folder includes main entities of this program. 

    - ```Commands```
    According to available commands: ```(a) <n>```, ```l```,```r```,```q```.  They can be classified into 3 different
    types: 
        1. Advance Command: ```AdvanceCommand```
        2. Direction Command: ```DirectionCommand```
        3. Quit Command 

  As there are 3 different types commands for processing in this application, polymorphism and inheritance have been
   used here to increase code reusability. ```Commands``` class is the parent class, while ```DirectionCommand``` and
    ```AdvanceCommand``` are child classes inheriting from ```Commands``` class. In each child class, excepting
     functions inherited from Commands class, it included corresponding functions for processing different types of commands.
  
   - ```Bulldozer```
      Bulldozer class contains information about bulldozer location, direction and methods used to update location and
      direction and calculate the cost as it moves. 
   - ```SiteMap```
    SiteMap is a utility class including just static methods for reading input site map and encoding/decoding
     coordinates. It is stateless and cannot be instantiated, and all methods in this class can be reused across the
      application.

- ```enums``` folder includes all enum classes.
     - ```Activity``` Class is an enum class for Activity Types and its corresponding fuel usage. 
        
     - ```CommandType``` Class is an enum class for 4 available Command Types.  
        - Advance```(a) <n>```, Turn left ```l
     ```, Turn right ```r```, Quit ```q```
     
     - ```Direction``` Class is an enum class for 4 directions and its degrees.  
        - NORTH(0), EAST(90), SOUTH(180), WEST(270))
     
     - ```Item``` Class is an enum class for Item Types and its corresponding cost. 
     
     - ```SquareType``` Class is an enum class for Command Types. 
       - PLAIN("o"), ROCKY("r"), TREE_REMOVABLE("t"), TREE_PRESERVED("T")
  
  
- ```exceptions``` folder includes exceptions to be thrown when something goes wrong in this program. 

     - ```CutTreeException``` will be thrown  when the program is trying to clear a protected tree.
          
     - ```OutOfBoundaryException``` will be thrown if there is an attempt to navigate beyond the boundaries of the site.
       
     - ```ParseException``` will be thrown if there is any parsing errors.
</details>


<details><summary>Design Summary</summary>

- ```Main``` is the entry of this program. 
- ```SiteMap``` class is used to process the siteMap file and complete the mapping between locations and coordiates.
    - As soon as the program is lauched, the site map will be read and parsed into the program using ```readInputSiteMap
``` and ```parseSiteMapListToArray``` methods. An arraylist ```siteMapList```  has been used to store the site map as a list after
 reading it from the text file. Then, a 2D array ```siteMap2DArray``` was designed to store the value of each square on the site map for
  later processing. 
    - Boundary is managed by Array indices.
    - Coordinates (x, y) will be encoded to an integer represents its location using the following formula in
    ```encodeCoordinates()``` method: 
    
      ```location = x * COL + y;```
      
      In this case, current location of the bulldozer can be determined by only two parameters including one integer
       number and direction. 
     - Decode function ```decodeLocation()``` is used to decode the location integer in order to update the location
      and calculate costs. The formula used for decoding is:    
    ```
        int x = location / COL;
  
        int y = location % COL;
    ``` 
      
- Taking the idea of Controller pattern from GRASP, ```GameController``` class is used as the single handler for all kinds
   of requests coming to the application. 
   - The 2D array ```siteMap2DArray``` generated from ```SiteMap```, which will be used to create a GameController object as a
    parameter with corresponding row and column number distracted from the input site map. 
- After creation of ```GameController``` object, the application will display commands instructions for the trainee to input
 commands.
    - The bulldozer's initial position is defined as (-1,0), and facing East.
    - Command reading and processing, cost calculations will be handled by the ```GameController```. 
    - Command reading process will be completed by ```readCommands()``` method. ```validateCommandAndConstruct()``` method
     will be called inside of ```readCommands()``` to validate input commands and create corresponding ```Commands
     ``` object. (```AdvanceCommand``` or ```DirectionCommand```). For instance, if the input command including the
      keyword “a”, after validation the input, the AdvanceCommand object will be generated and added in the CommandList for further process. 
    - As there are different commands for processing in this application, polymorphism and inheritance have been used
    . ```Commands``` class is the parent class. ```DirectionCommand``` and ```AdvanceCommand``` are child classes inheriting from
     ```Commands``` class. 
    - In each child class, excepting functions inherited from Commands class, it included corresponding functions for
     processing different types of commands.
 - Meanwhile, ```generateCostReport``` and ```computeUnclearedSquareAndCommunication()``` methods in
  ```GameController``` are used for cost calculation and generate itemised cost report. 
 - Finally, ```getCommandListString()``` will display the commands list including all input commands in string and
  displays the final cost report.

</details>



