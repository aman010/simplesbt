
import sbt._
import java.io._

scalaVersion := "2.11.1"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "org.ensime" %% "ensime" % "0.9.10-SNAPSHOT"

// guaranteed to exist when started from emacs
val JavaTools = new File(sys.env("JAVA_HOME"), "/lib/tools.jar")

unmanagedClasspath in Runtime += { Attributed.blank(JavaTools) }

mainClass in Compile := Some("org.ensime.server.Server")

fork := true

javaOptions ++= Seq (
  "-Dscala.usejavacp=true",
  "-Densime.config=/home/geek/Desktop/simplesbt/.ensime",
  "-Densime.active=scalding-tutorial",
  "-Densime.cachedir=/home/geek/Desktop/simplesbt/.ensime_cache/"
)

javaOptions += "-Xms1024m"

javaOptions += "-Xmx1024m"

javaOptions += "-XX:ReservedCodeCacheSize=128m"

javaOptions += "-XX:MaxPermSize=256m"
