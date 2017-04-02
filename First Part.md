# Installing the Required Toolset (1 Hour)

## Installing Scala and Sbt

Let's make sure we have `java` first
```
brew cask install java
```

The we install the `Scala Build Tool (sbt)`

```
brew install sbt
```

Now we download latest `Spark` source code.

```
git clone git://github.com/apache/spark.git
```

```
cd spark
```

Now, let's build `Spark`

The official build tool for `Spark` is `Maven`, but `sbt` allows a faster development cycle.

```
./build/sbt -Pyarn -Phadoop-2.3 package
```

After the build is complete, we should test everything went fine.

```
./build/sbt -Pyarn -Phadoop-2.3 -Phive -Phive-thriftserver test
```



If we want to build it using `Maven`, we can run this command.

```
./build/mvn -Pyarn -Phadoop-2.4 -Dhadoop.version=2.4.0 -DskipTests clean package
```

This link is the full page for creating different distributions.

[Building Spark, Official Site](http://spark.apache.org/docs/latest/building-spark.html)

### Please notice that for production systems we might want to download the binaries directly instead of building it ourselves!

