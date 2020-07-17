 ```
  mvn clean package
 java "-Djavax.net.ssl.trustStore=/Users/agiertli/Downloads/keystore.jks" "-Djavax.net.ssl.trustStorePassword=password" -jar target/redhat-verifier-0.0.1-SNAPSHOT.jar com.myspace:SampleProject:1.0.0
 ```