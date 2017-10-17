# Log parser
parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

# Commands
| Command | Purpose |
| ------ | ------ |
| mvn clean | clean the target directory|
| mvn compile | compile the project|
| mvn test | run project unit tests |
| mvn package | package your code into a jar file in target/ directory |

# How to run
Make sure that mysql server up and running on default port 3306

Run this command :
```sh
java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
```
after the application finished , A database called parser will be created and it contains 2 tables (log_record, log_archive)
The results will be in log_archive

* accesslog: The path of the file that tyou want to make query n it.
* startDate: the start date of the query.
* duration: duration to be added to start date to get end date.
* threshold: represent a number in which the query finds all ip that have more that (threshold) request.

License
----

Mohamed Waleed