name := "pipeline"

version := "0.1"

scalaVersion := "2.11.12"

mainClass in assembly :=Some("pipeline.Main")
assemblyJarName in assembly := "pipeline.jar"

resolvers +="cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos"

libraryDependencies ++={
  val akkaVersion = "2.4.12"
  val redisClientVersion = "2.9.0"
  val alpakkaVersion = ""
  Seq(
    "redis.clients" % "jedis" % redisClientVersion,
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion
  )
}