# eureka_server

### vm args :


<pre><code> 
-Xmx512m -Dapp.id=eureka-provider -Duser.timezone=Europe/Paris -Duser.language=en -Duser.country=FR -Duser.variant=EURO -Dcom.sun.management.jmxremote  -Xdebug -jar target/app.jar --spring.profiles.active=dev --spring.config.location=src/test/resources/application.properties --logging.config=src/test/resources/logback.xml
</code></pre>