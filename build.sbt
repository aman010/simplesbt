
scalaVersion := "2.10.4"

scalacOptions +="-feature"

initialCommands in console := {"import com.twitter.scalding._; import com.twitter.scalding.ReplImplicits._; import com.twitter.scalding.ReplImplicitContext._;"}


net.virtualvoid.sbt.graph.Plugin.graphSettings
