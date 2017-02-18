start /D target call java -jar microservice-demo-1.1.0.RELEASE.jar  registration
timeout 10
start /D target call java -jar microservice-demo-1.1.0.RELEASE.jar  products
timeout 10
start /D target call java -jar microservice-demo-1.1.0.RELEASE.jar  price