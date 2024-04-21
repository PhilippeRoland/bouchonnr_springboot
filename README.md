# How to use
With Docker and Java 21 installed, run in a terminal from the project root:
*docker network create mynetwork*

*docker build -t bouchonnr_database_image -f Dockerfile.db .*, then
*docker run --name bouchonnr_database --network=mynetwork -d -P -p 127.0.0.1:5432:5432 bouchonnr_database_image* to set up the Database.

Finally, *docker build -t bouchonnr .* and then *docker run --name bouchonnr_app --network=mynetwork -d -p 8080:8080 bouchonnr* to run the app



