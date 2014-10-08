import sbt._
import Keys._
object BuildSettings {
// Basic settings for our app
lazy val basicSettings = Seq[Setting[_]](
organization := "Concurrent Inc.",
version := "0.9.1", // -> follow the release numbers of scalding
description := "The scalding tutorial as an SBT project",
scalaVersion := "2.10.4",
scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
resolvers ++= Dependency.resolutionRepos
)
// sbt-assembly settings for building a fat jar
import sbtassembly.Plugin._
import AssemblyKeys._
lazy val sbtAssemblySettings = assemblySettings ++ Seq(
// Slightly cleaner jar name
jarName in assembly := {
name.value + "-" + version.value + ".jar" 
},
// Drop these jars
excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
val excludes = Set(
"jsp-api-2.1-6.1.14.jar",
"jsp-2.1-6.1.14.jar",
"jasper-compiler-5.5.12.jar",
"minlog-1.2.jar", // Otherwise causes conflicts with Kyro (which bundles it)
"janino-2.5.16.jar", // Janino includes a broken signature, and is not needed anyway
"commons-beanutils-core-1.8.0.jar", // Clash with each other and with commons-collections
"commons-beanutils-1.7.0.jar", //  "
"hadoop-core-1.1.2.jar",
"hadoop-tools-1.1.2.jar" // "
)
cp filter { jar => excludes(jar.data.getName) }
},
mergeStrategy in assembly <<= (mergeStrategy in assembly) {
    (old) => {
     
      case "project.clj" => MergeStrategy.discard // Leiningen build fileis
      case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
      case PathList("org","apache","commons","beanutils",  xs @ _*)  => MergeStrategy.last
      case PathList("org","apache","commons","collections" , xs @ _*) => MergeStrategy.last
      case PathList("org","apache","hadoop","yarn",  xs @ _*) => MergeStrategy.last
      case PathList("org","objectweb", xs @ _*) => MergeStrategy.last
      case PathList("org","fusesource","hawtjni", xs @ _*) => MergeStrategy.last
      case PathList("org","fusesource","jansi" , xs @ _*) => MergeStrategy.last 
      case PathList("org","slf4j","impl",xs @ _*) => MergeStrategy.discard
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
     // case _ => MergeStrategy.last // leiningen build files
      case x => old(x)
     
    }
  },

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
 cp filter {x => x.data.getName.matches("sbt.*") || x.data.getName.matches(".*macros.*")}
}
)
lazy val buildSettings = basicSettings ++ sbtAssemblySettings
}

object Resolvers {
val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
val mvnrepository = "MVN Repo" at "http://mvnrepository.com/artifact"
val conjars = "Concurrent Maven Repo" at "http://conjars.org/repo"
val clojars = "Clojars Repo" at "http://clojars.org/repo"
val twitterMaven = "Twitter Maven" at "http://maven.twttr.com"
val allResolvers = Seq(typesafe, sonatype, mvnrepository, conjars, clojars, twitterMaven)
}

object Dependency {
val resolutionRepos = Seq(
  "Concurrent Maven Repo" at "http://conjars.org/repo") // For Scalding, Cascading etc                                                                 
object V {
val scalding = "0.12.0rc4"
val hadoop = "2.2.0"
val specs2 = "3.0.1" // -> "1.13" when we bump to Scala 2.10.0
// Add versions for your additional libraries here...                                                                                                 
val cascading = "3.0.0-wip-51"
val cas = "2.5.5"
}
val cascadingCore = "cascading" % "cascading-core" % V.cascading
val cascadingLocal = "cascading" % "cascading-local" % V.cascading
val cascadingHadoop = "cascading" % "cascading-hadoop2-mr1" % V.cascading
val cascadingtez = "cascading" % "cascading-hadoop2-tez" % V.cascading
val opertaionxml= "cascading" % "cascading-xml" % V.cascading
val scaldingCore = "com.twitter" % "scalding-core_2.10" % V.scalding exclude( "cascading", "cascading-local" ) exclude( "cascading", "cascading-hadoop" )
val scalding_repl = "com.twitter" % "scalding-repl_2.10" % V.scalding
val hadoopCore = "org.apache.hadoop" % "hadoop-common" % V.hadoop
val hadoopClientCore = "org.apache.hadoop" % "hadoop-mapreduce-client-core" % V.hadoop
val hadoopClient = "org.apache.hadoop" % "hadoop-client" % V.hadoop
// Add additional libraries from mvnrepository.com (SBT syntax) here...                                                                               
// Scala (test only)                                                                                                                                  
val specs2 = "org.scalamock" % "scalamock-specs2-support_2.10" % V.specs2 % "test"

}


object Dependencies {
import Dependency._

val activatorscalding = Seq(
cascadingCore , cascadingLocal ,cascadingHadoop,cascadingtez, opertaionxml , scaldingCore  , hadoopCore , hadoopClientCore , hadoopClient,specs2, scalding_repl)
}

object ActivatorScaldingBuild extends Build {
import Resolvers._
import Dependencies._
import BuildSettings._
lazy val activatorscalding = Project(
id = "Scalding",
base = file("."),
settings = buildSettings ++ Seq(
// runScriptSetting,
resolvers := allResolvers,
libraryDependencies ++= Dependencies.activatorscalding,
mainClass := Some("RunAll")))

}


/*import autoImport._

object Pack extends AutoPlugin {

override lazy val assemblySetting = Seq(commands += pack)

lazy val pack = Command.command("pcaking ...") 
{ (state:State) => 
mergeStrategy in assembly <<= (mergeStrategy in assembly) {
    (old) => {

                 

    }




}


}*/


