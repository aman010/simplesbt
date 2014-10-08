import com.twitter.scalding
import org.apache.hadoop.mapred.JobClient
import org.apache.hadoop.tools
import org.apache.hadoop.util.ToolRunner
import org.apache.hadoop.util.ToolRunner.run
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.mapred.JobConf
import scala.io.Source
import java.io.File
/**
  *  This main is intended for use only for the Activator run command as
  *  the default. If you pass no arguments, it runs all of the examples
  *  using default arguments. Use the sbt command "scalding" to run any
  *  of the examples with the ability to specify your own arguments.
  *  See also the companion objects' main methods.
  */
object Runall {
def run(name: String , message: String, args: Array[String]):Int = {
println(s"\n==== $name " + ("===" * 20))
println(message)
val argsWithName = name +: args
println(s"Running: ${argsWithName.mkString(" ")}")
  val conf = new Configuration
  val masterIp= "geek-HP-G42-Notebook-PC"
// val job = new JobConf()
  conf.setStrings("fs.defaultFS",s"hdfs://$masterIp:9000")
  conf.setStrings("mapred.job.tracker",s"$masterIp:9001")
  conf.setStrings("mapreduce.framework.name","yarn")
  //conf.setStrings("yarn.resourcemanager.hostname","localhost")
  //job.set("yarn.resourcemanager.scheduler.address","0.0.0.0:8030")
  //job.set("yarn.resourcemanager.webapp.address","localhost:8088")
  //job.set("mapreduce.jobtracker.staging.root.dir", "file:///tmp/hadoop-geek/mapred/staging")
  //job.set("yarn.resourcemanager.address","localhost:8040")
  //job.set("yarn.log.server.url","http://localhost:19888/jobhistory/logs")

val tool = new NoJarTool(
  wrappedTool = new scalding.Tool,
  collectClassesFrom = Some(new File(("/home/geek/Desktop/simplesbt/target/scala-2.10/classes/"))),
  libJars = List(new File("/home/geek/Desktop/simplesbt/target/scala-2.10/Activator-Scalding-assembly-0.9.1-deps.jar"), new File("/home/geek/Desktop/simplesbt/target/scala-2.10/classes.jar")))

  ToolRunner.run(conf , tool , argsWithName)
}
def printSomeOutput(outputFileName: String, message: String = "") = {
if (message.length > 0) println(message)
println("Output in $outputFileName:")
Source.fromFile(outputFileName).getLines.take(10000) foreach println
println("...\n")
}
}
