# How to use
With Docker and Java 21 installed, run in a terminal from the project root:

*docker network create mynetwork* <br> To set up the common network both the app and database containers will run on (docker-compose could not be made to work, maybe due to a synchronisation issue? Will need investigating)

Then, run:  <br>*docker build -t bouchonnr_database_image -f Dockerfile.db .*<br> and<br>
*docker run --name bouchonnr_database --network=mynetwork -d -P -p 127.0.0.1:5432:5432 bouchonnr_database_image* <br>to set up and start the Postgres database.

Finally, run <br>*docker build -t bouchonnr .*<br> then <br>*docker run --name bouchonnr_app --network=mynetwork -d -p 8080:8080 bouchonnr*<br> to build and start the app



