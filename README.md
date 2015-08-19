# AR-Storyboard
Open source application to integrate a physical storyboard (using a webcam) to a Trello board

### Installation
  * `cp conf/application-example.conf conf/application.conf`
  * Install JDK 8
    * `javac -version >>> javac 1.8.0_40`
  * Install [Scala](http://www.scala-lang.org/) (version defined in `build.sbt:scalaVersion`)
  * Install [Sbt](http://www.scala-sbt.org/) (version defined in `project/build.properties:sbt.version`)
  * `sbt run`
  * To run the application tests: `sbt tests`
  
### Setup the application.conf
  * Set `trello.api.boardid` to your Trello board's id
  * Get your Trello API key and secret from [here](https://trello.com/app-key)
    * Put the API key to `trello.api.key`
    * Put the API secret to `trello.api.secret`
  * Get your Trello API token and token secret
    * Run the application: `sbt run`
    * Go to `http://localhost:9000`
    * Give the app the rights to use your Trello account
    * Copy the token and secret to `application.conf`
      * `trello.api.token`
      * `trello.api.token_secret`