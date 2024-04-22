# How to use
With Docker and Java 21 installed, run in a terminal from the project root:

*docker network create mynetwork* <br> To set up the common network both the app and database containers will run on (docker-compose could not be made to work, maybe due to a synchronisation issue? Will need investigating)

Then, run:  <br>*docker build -t bouchonnr_database_image -f Dockerfile.db .*<br> and<br>
*docker run --name bouchonnr_database --network=mynetwork -d -P -p 127.0.0.1:5432:5432 bouchonnr_database_image* <br>to set up and start the Postgres database.

Finally, run <br>*docker build -t bouchonnr .*<br> then <br>*docker run --name bouchonnr_app --network=mynetwork -d -p 8080:8080 bouchonnr*<br> to build and start the app

# Notes
## Technical choices
Rails was not used, though not for lack of trying. It's possible the environment used (Ubuntu VM running on Host VirtualBox) was not the right choice to setup a Rails dev environment, as I saw many complaints of deprecated versions, difficulties in retrieving versions, compilation issues, etc.<br> 
Regardless, I attempted direct sudo apt get, rbm and rvenv setups, all of which failed at one point or another.<br>
I also could not get docker compose to work for the app -> database container links, perhaps due to a synchronization issue where the app was up before the database was?

In a similar vein, the gradle unit test phase does not work when fired in the docker build of the app. It was removed from the doker build pending investigation, but rest assured that they work from intellij.<br>
I also failed to get the springboot @TestContainers to work to replace the database connection with one spawned from a test container - I would've loved to try that recent feature of springboot 3, but they caused tests to stall without ever producing an error message. Instead I opted to use an H2 in-memory database handled directly by Springboot

## Model
For this MVP, the model is left deliberately simple.<br> 
Use of document databases might be preferable if schema is called to change as more data is added and exploited (Ratin's raters, Wine types, regions, year, château, etc)

