# spring-boot

## zuul 
api gateway 

## eureka 
service discovery

## cloud-config 
github config repository : https://github.com/mohalaoui/produit-config-repo.git

## OpenFeign/feign
declaratif rest client

## ribbon
load balancer client side

## actuator
### info
http://localhost:9104/actuator/info

result example : 
<pre><code>
{
  "build" : {
    "artifact" : "${project.artifactId}",
    "version" : "1.0.1-SNAPSHOT"
  },
  "app" : {
    "name" : "produit",
    "description" : "Demo project for Spring Boot",
    "version" : "1.0.1-SNAPSHOT"
  }
}
</code></pre>

### health
http://localhost:9104/actuator/health

result example:
<pre><code>
{
  "status" : "UP"
}
</code></pre>

