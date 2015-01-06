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

conf.setStrings("fs.defaultFS" , s"hdfs://$masterIp:9000")
conf.set("mapreduce.jobtracker.address",s"$masterIp:9001")
conf.setStrings("mapreduce.framework.name","yarn")
conf.setStrings("yarn.resourcemanager.hostname", s"$masterIp")
conf.setStrings("yarn.resourcemanager.resource-tracker.address" , s"$masterIp:8025")
conf.setStrings("yarn.resourcemanager.scheduler.address" , s"$masterIp:8030")
conf.setStrings("yarn.resourcemanager.address" , s"$masterIp:8050") 

val tool = new NoJarTool(wrappedTool = new scalding.Tool, Some(new File("/home/geek/Desktop/simplesbt/target/scala-2.10/classes/")), List(new File("/home/geek/Desktop/simplesbt/target/scala-2.10/activatorscalding-assembly-0.9.1-deps.jar"), new File("/home/geek/Desktop/simplesbt/target/scala-2.10/activatorscalding_2.10-0.9.1.jar")))
  

  ToolRunner.run(conf , tool , argsWithName)
}
def printSomeOutput(outputFileName: String, message: String = "") = {
if (message.length > 0) println(message)
println("Output in $outputFileName:")
Source.fromFile(outputFileName).getLines.take(10000) foreach println
println("...\n")
}
}
