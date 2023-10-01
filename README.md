# COMP2120-Assignment-3-Workshop-10-Group-A

[**1. Project summary**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Project%20summary)

- [List of features](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Project%20summary/List%20of%20features)
- [Description of the game](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Project%20summary/Description%20of%20the%20game)
- [Future development](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Project%20summary/Future%20development)
- [Additional notes](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Project%20summary/Additional%20notes)

[**2. Evidence of planning and scheduling of issues**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Evidence%20of%20planning%20and%20scheduling%20of%20issues/Planning%20and%20scheduling)

- [Sprint cycles](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Evidence%20of%20planning%20and%20scheduling%20of%20issues/Sprint%20cycles)

[**3. Meeting minutes**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/3.%20Meeting%20minutes)

- [Sprint 1 meeting 1](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%201%20meeting%201)
- [Sprint 1 review](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Sprint%201%20review)
- [Sprint 2 meeting 1](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%202%20meeting%201)
- [Sprint 2 reviews and retrospective](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%202%20reviews%20and%20retrospective)
- [Sprint 3 planning meeting](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%203%20planning%20meeting)
- [Sprint 3 reviews and retrospective](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%203%20reviews%20and%20retrospective)
- [Sprint 4 planning meeting](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%204%20planning%20meeting)
- [Sprint 4 reviews and retrospective](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/Meeting%20minutes/Sprint%204%20reviews%20and%20retrospective)

[**4. Team structure and roles**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/4.%20Team%20structure%20and%20roles)

[**5. Test**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/5.%20Test)

[**6. Why Apache 2.0 License**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/6.%20Why%20Apache%202.0%20License)

[**7. Reflection**](https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a/-/wikis/7.%20Reflection)

## Game Installation

### Dependencies

Ensure you have installed [Java](https://www.java.com/en/download/help/download_options.html) with version 9 or later, and [Gradle](https://gradle.org/install/) which must be version 7.5 or later.

## Building from source files

clone the git repository using must be an ANU student to do so

```
git clone https://gitlab.cecs.anu.edu.au/u7581147/comp-2120-assignment-3-workshop-10-group-a
```

then inside the directory type `gradle shadowJar` to build the project. the game can then be run using the following command

```
java -jar /build/libs/TowerCrawler-1.0-all.jar
```

### POSIX platforms

if running on a POSIX platform the gradlew file can be used with `./gradlew shadowJar` and then using Java to run with the following command

```
java -jar /build/libs/TowerCrawler-1.0-all.jar
```

### Running in terminal mode

to run the program in terminal mode add the `-terminal` flag when executing the java file. This will remove the window and make the game run in the terminal taking the input that is typed in.

## Running with docker

to run with [Docker](https://www.docker.com/get-started/), Docker must be installed on the system. then run the following command to pull the image and run locally.

```
docker run --rm -it yaboibo/comp2120-assignment3
```

to have saving enabled in docker, create a folder to hold the save data then run the following command replacing SAVEFOLDER with the folder just created.

```
docker run --rm -it -v SAVEFOLDER:/app/src/cache/progress/current yaboibo/comp2120-assignment3
```