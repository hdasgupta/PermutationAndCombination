#

FROM ubuntu:java

COPY ./target/PermutationAndCombination-1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT java -jar PermutationAndCombination-1.0-SNAPSHOT.jar

