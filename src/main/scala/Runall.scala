import com.twitter.scalding
import org.apache.hadoop.tools
import org.apache.hadoop.util.ToolRunner
import org.apache.hadoop.conf.Configuration
import scala.io.Source
import java.io.File
/**
  *  This main is intended for use only for the Activator run command as
  *  the default. If you pass no arguments, it runs all of the examples
  *  using default arguments. Use the sbt command "scalding" to run any
  *  of the examples with the ability to specify your own arguments.
  *  See also the companion objects' main methods.
  */
object Run {

  def run(name: String, message: String, args: Array[String]): Int = {
    run(name, new File("/home/geek/Desktop/simplesb" + "/target/scala-2.11/classes/"), message, args)
  }
def run(name: String, classDir:File, message: String, args: Array[String]):Int = {
println(s"\n==== $name " + ("===" * 20))
println(message)
val argsWithName = name +: args
println(s"Running: ${argsWithName.mkString(" ")}")

  val masterIp= "geek-HP-G42-Notebook-PC"

  val conf = new Configuration

  conf.setStrings("fs.default.name",s"hdfs://$masterIp:9000")
  conf.setStrings("mapred.job.tracker",s"$masterIp:9001")

val tool = new NoJarTool(
  wrappedTool = new scalding.Tool,
  collectClassesFrom = Some(new File("/home/geek/Desktop/simplesbt/target/scala-2.11/classes")),
  libJars = List(new File("/home/geek/Desktop/simplesbt/target/scala-2.11/jars/Activator-Scalding-0.9.0.jar")))

  ToolRunner.run(conf , tool , argsWithName)
}
def printSomeOutput(outputFileName: String, message: String = "") = {
if (message.length > 0) println(message)
println("Output in $outputFileName:")
Source.fromFile(outputFileName).getLines.take(10) foreach println
println("...\n")
}
}
