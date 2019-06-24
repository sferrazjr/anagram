# Anagram Exercise

An anagram is a type of word play, the result of rearranging the letters of a word or phrase to produce a new word or phrase, 
using all the original letters exactly once; for example, the word anagram can be rearranged into nag-a-ram.

## The Exercise
Develop a RESTful WebService that given a local storage containing the dictionary and a search phrase provided as parameter of the call, 
returns all the combinations of words that are anagrams of the search phrase.

* Letter case does not matter.
* Special characters should be ignored.
* Ignore short dictionary words (those having only one or two letters).

## Prerequisites
Java 8 and maven version 3.3.9 or newer

## Installing
```
mvn clean instal
```

## SonarQube
Start SonarQube with Postgres using docker compose
Obs: this is a WIP, missing put parameters to docker volumes
```
docker-compose up -d
```
and run the SonarQube
```
mvn sonar:sonar
```
Check the analysis in the browser _http://localhost:9000_

## Running the application

Start the service (make sure there is no service running on port 8080)
```
mvn spring-boot:run
```
Call the service via command line with cURL
```
curl -X GET "http://localhost:8080/anagram/solve/IT-Crowd"
```


