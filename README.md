# How to use
With Docker and Java 21 installed, run in a terminal from the project root:

*docker network create mynetwork* <br> To set up the common network both the app and database containers will run on (docker-compose could not be made to work, maybe due to a synchronisation issue? Will need investigating)

Then, run:  <br>*docker build -t bouchonnr_database_image -f Dockerfile.db .*<br> and<br>
*docker run --name bouchonnr_database --network=mynetwork -d -P -p 127.0.0.1:5432:5432 bouchonnr_database_image* <br>to set up and start the Postgres database.

Finally, run <br>*docker build -t bouchonnr .*<br> then <br>*docker run --name bouchonnr_app --network=mynetwork -d -p 8080:8080 bouchonnr*<br> to build and start the app

The postman collection at the root of the project (Bouchonnr.postman_collection.json) has a few requests one can use that cover all of the various functions developed for this MVP.

# Notes
## Technical choices
Rails was not used, though not for lack of trying. It's possible the environment used (Ubuntu VM running on Host VirtualBox) was not the right choice to setup a Rails dev environment, as I saw many complaints of deprecated versions, difficulties in retrieving versions, compilation issues, etc.<br> 
Regardless, I attempted installation through apt get, rbm and rvenv, all of which failed at one point or another.<br>

I also could not get docker compose to work for the app -> database container links, perhaps due to a synchronization issue where the app was up before the database was?

I quickly tried but didn't have time to use springboot @TestContainers to replace the database connection with one spawned from a test container - I would've loved to try that recent feature of springboot 3, but they caused tests to stall without ever producing an error message. Instead I opted to use an H2 in-memory database handled directly by Springboot

In a similar vein, the gradle unit test phase does not work when fired in the docker build of the app. It was removed from the docker build pending investigation, but rest assured that they work from the IDE.<br>

## Model
For this MVP, the model is left deliberately simple.<br> 
Use of document databases might be preferable if schema is called to change as more data is added and exploited (Rating's raters, Wine types, regions, year, château, etc)

Reevaluate the use of Lazy fetching for performance in a V1.1, it was causing issues with JPA queries

## REST API
For simplicity's sake, all wine-depending resources (listings, ratings, alerts) use the raw wine id as a parameter.
<br>It would probably be best to establish a mix of non-id wine criteria allowing for a wine to be found in the database instead, such as a mix of chateau, year, grape type etc. and use that as a key / index instead

Hypermedia was not set up in this V1, but is an obvious improvement to make for usability of the API, especially given how many resources link to and depend on the central Wine resource

Similarly, GET requests were set up without pagination, which would be needed to prevent over-fetching in most cases

## Security
Calls from the alerting service should be signed with certificates so the target services can trust our notifications, as it could otherwise be manipulated to fool competitors.

Additionally, we'd want to have outgoing rate limits in place to avoid our system being used as part of a (D)DOS attack. 
<br>Otherwise, multiple alerts could be set up and pointed by attackers at a given target URL. Every listing sent to our system would then result in multiple times that amount of calls to said target URL.

## Other improvements
It's in TODO comments, but @Valid on entity classes is not working - one can create ratings with score <0 or >5 for example. This needs investigating before app is opened to the public

Many key properties, namely datasource URL and passwords, need to be turned into appropriately secured environment variables for proper CI/CD to be possible

## Future dream architecture
For  a use case with high capacity / lots of flow, and a need to be highly reactive, especially on the alerts front (think wine stock market!)
![image](https://github.com/PhilippeRoland/bouchonnr_springboot/assets/5500168/71775ef2-8a36-4f10-a948-106ce916ff38)


