const args = process.argv;
const exec = require('child_process').exec;

const SERVER = "java -jar Server/target/Server-1.0-SNAPSHOT-jar-with-dependencies.jar";
const CLIENT = "java -jar Client/target/Client-1.0-SNAPSHOT-jar-with-dependencies.jar";

var COMMAND;

switch (args[2]) {
    case "server":
    case "0":
        COMMAND = SERVER;
        break;
    case "client":
    case "1":
    default:
        COMMAND = CLIENT;
        break;
}

exec(COMMAND, function (err, stdout, stderr) {
    if (err) {
        console.log(err);
    }
    console.log(stdout)
})
