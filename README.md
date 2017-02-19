# Microservices-demo

Clone it into your favorite IDE or use maven directly.

## Components Used

Netflix Eureka - Service Discovery Server Netflix Eureka allows microservices to register themselves at runtime as they appear in the system landscape.

Netflix Ribbon - Dynamic Routing and Load Balancer Netflix Ribbon can be used by service consumers to lookup services at runtime. Ribbon uses the information available in Eureka to locate appropriate service instances. If more than one instance is found, Ribbon will apply load balancing to spread the requests over the available instances. Ribbon does not run as a separate service but instead as an embedded component in each service consumer.

## Prerequisites Versions
JDK 8
Maven 3.1.0


## Using an IDE

You can run the system in your IDE by running the three servers in order: _RegistrationService_, _ProductssService_  _PriceService_ and ProductPriceService.

Open the Eureka dashboard [http://localhost:1111](http://localhost:1111) in your browser to see that the `PRODUCTS-SERVICE` and `PRICE-SERVICE` and `COMPOSITE-SERVICE` applications have registered.

## Command Line

You may find it easier to view the different applications by running them from a command line since you can place the three windows side-by-side and watch their log output

To do this, open three CMD windows (Windows) or three Terminal windows (MacOS, Linux) and arrange so you can view them conveniently.

 1. In each window, change to the directory where you cloned the demo
 2. In the first window, build the application using `mvn clean package`
 3. In the same window run: `java -jar target/microservice-demo-1.1.0.RELEASE.jar registration` and wait for it to start up
 4. Switch to the second window and run: `java -jar target/microservice-demo-1.1.0.RELEASE.jar products` and again wait for
 it to start up
 5. In the third window run: `java -jar target/microservice-demo-1.1.0.RELEASE.jar price`
 6. In the third window run: `java -jar target/microservice-demo-1.1.0.RELEASE.jar composite`
 7. In your favorite browser open the same two links: [http://localhost:1111](http://localhost:1111) and [http://localhost:3333](http://localhost:3333)

or This looks like a bit of work to do, I have written a batch file which will perform above steps.
1. In order to compile and build the project, run build.bat file. Once build is successful go over to step 2.
2. Execute start-all.bat file, this will open Four bat file, one for registration, one for product and another one for price and lastly for Composite Server.

I have introduced a delay of 10 seconds, to make sure registration server will be up before product,composite and price server.

You should see servers being registered in the log output of the first (registration) window.
Navigate to [http://localhost:1111](http://localhost:1111) see Eureka Dashboard. 

 1. In a new window, run up a second products-server using HTTP port 2223:
     * `java -jar target/microservice-demo-0.0.1-SNAPSHOT.jar products 2223`
 2. Allow it to register itself
 3. Kill the first products-server and see the price-server switch to using the new products-server - no loss of service.

## Assumption
1. Domain Entity Kept very minimal for demonstration purpose, attributes only considered are productId,productName,productDescription and productPrice.
2. Single project being used for brevity purpose. Ideally, Should use a Maven Multi-Model Project, where there will be Parent model consisting of common libs and this will act like parent module for sub-model like price,product,registration and composite.

## API Testing
 1. create Product
 curl -i -X POST -H "Content-Type: application/json" -d "{\"id\":\"1\",\"name\":\"Product1Name\",\"description\":\"Product1Description\"}" localhost:2222/products/create
 
 2. create Price
 curl -i -X POST -H "Content-Type: application/json" -d "{\"productId\":\"1\",\"price\":1000.0}" localhost:3333/createprice
 
 3. get product and price through composite API
 curl localhost:4444/compositeproducts/1
 
 4. get product by description
 curl -i -X POST -H "Content-Type: application/json" -d "{\"description\":\"product\"}" localhost:2222/products/description
 
 5. Delete product
 curl localhost:2222/products/delete/4
 