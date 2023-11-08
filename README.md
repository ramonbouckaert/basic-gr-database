# basic-gr-database

## Build

To build all modules, run:

`./gradlew build`

This will produce `build` folders for each of the modules: `lambda`, `shared` and `utility`.

- `shared/build/libs/shared.jar` contains shared classes used by both the Lambda and the Utility tool
- `lambda/build/libs/lambda-all.jar` is a shaded JAR that can be used as an AWS Lambda function
- `utility/builds/libs/utility-all.jar` is a shaded executable JAR that provides a command-line utility for setting up a
  basic-gr-database database.

The build targets Java 17.

## Run

To use the utility tool, run:

`java -jar utility/build/libs/utility-all.jar`

The tool will prompt you for:

- a JDBC connection string to your Postgres database (starting with `jdbc:postgresql://`)
- an option to update the database schema to create the tables expected by basic-gr-database

To use the lambda, configure an AWS Lambda function:

- select Java 17 for the language
- set the handler reference to `io.bouckaert.basicgrdatabase.lambda.GraphQlLambdaRequestHandler::handleRequest`
- create an environment variable called `DB_URL` and set it to your JDBC connection URL (starting
  with `jdbc:postgresql://`)
- create an environment variable called `DB_DRIVER` and set it to your JDBC driver, it will probably
  be `org.postgresql.Driver`

You can test the lambda by using the AWS template `apigateway-aws-proxy`:

```json
{
  "body": "{\"query\":\"{getAllPoliticians{id}}\"}",
  "resource": "/",
  "path": "/path/to/resource",
  "httpMethod": "POST"
}
```

You should receive back a JSON object that has a `body` property that a JSON-formatted GraphQL response.
