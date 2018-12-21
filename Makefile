all:
	mvn -T 4 clean install -DskipTests=true

server:
	mvn -T 1C install -pl Server -am -DskipTests=true

client:
	mvn -T 1C install -pl Client -am -DskipTests=true

monitor:
	mvn -T 1C install -pl Monitor -am -DskipTests=true
	
library:
	mvn -T 1C install -pl ChatRoomLibrary -am -DskipTests=true

runclient:
	java -jar Client/target/Client-1.0-SNAPSHOT-jar-with-dependencies.jar

runserver:
	java -jar Server/target/Server-1.0-SNAPSHOT-jar-with-dependencies.jar

runmonitor:
	java -jar Monitor/target/Monitor-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	mvn -T 1C clean