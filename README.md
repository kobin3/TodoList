# TodoList

Todo List management api on play 2.4 (scala)

### Prerequisites

* You need to have a JDK 1.8 (or later) installed on your machine (see [General Installation Tasks](https://www.playframework.com/documentation/2.4.x/Installing)).
* sbt (see http://www.scala-sbt.org/)
* Scala (see https://www.scala-lang.org/download/)

### Installing
You can build this project by (It may take a long time in the first build)
```
sbt run
```
If all thing done, you'll see

```
--- (Running the application, auto-reloading is enabled) ---

[info] p.c.s.NettyServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Ctrl+D to stop and go back to the console...)
```
Then you can access through browser http://localhost:9000/swagger

### Running the tests
If you want to test
```
sbt test
```
