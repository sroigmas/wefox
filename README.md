# Challenge

## :computer: How to execute

Simply execute Maven goals: mvn clean install

Then run the application.

## :memo: Notes

- 'Amount' field in Payment was changed to type BigDecimal in order to allow decimal values.
- Two Kafka listeners were added (one for each topic) in order to run concurrently.
- The code stores the payment and updates the account simultaneously in one line of code.
- In case the account for the received payment is missing, the payment is discarded and the error is sent to the log system.
- In case of exception when validating (e.g.: in case of timeouts), the payment is discarded and the error is sent to the log system.
- MapStruct is used to easily convert from dto to entity and viceversa.
- H2 database is used for integration testing using a different Spring profile.

## :pushpin: Things to improve

Things to improve would be adding more tests as well as adding some code quality plugins to Maven (Checkstyle, PMD, Findbugs and Sonar). Also, adding a retry logic against timeout exceptions when calling both validation and logging endpoints.
