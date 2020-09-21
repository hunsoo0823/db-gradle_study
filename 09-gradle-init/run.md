1. make project directory

    mkdir 09-gradle-init
    cd 09-gradle-init

    // { basic, java-application, java-library,  kotlin-application, kotlin-library, pom }

2. generate java application using JUnit 4

    gradle init --type java-application

3. (or) generate java application using JUnit 5

    gradle init --type java-application --test-framework junit-jupiter

4. compile Java programs

    gradle classes

5. run Main method

    gradle run

6. run all testcases

    gradle test




Change gradle-wrapper version

    ./gradlew wrapper --gradle-version=5.5.1 --distribution-type=bin

