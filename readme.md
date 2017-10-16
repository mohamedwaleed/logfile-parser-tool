# Log parser
parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

# Commands
| Command | Purpose |
| ------ | ------ |
| mvn compile | compile the project|
| mvn test | run project unit tests |
| mvn package | package your code into a jar file in target/ directory |

# How to run
java -cp "parser.jar" com.ef.Parser --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 

* accesslog: The path of the file that tyou want to make query n it.
* startDate: the start date of the query.
* duration: duration to be added to start date to get end date.
* threshold: represent a number in which the query finds all ip that have more that (threshold) request.

License
----

Mohamed Waleed