
scalaVersion := "2.10.4"

scalacOptions +="-feature"

initialCommands in console := {"import com.twitter.scalding._; import com.twitter.scalding.ScaldingShell --hdfs"}


net.virtualvoid.sbt.graph.Plugin.graphSettings

