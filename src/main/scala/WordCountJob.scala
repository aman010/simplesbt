import com.twitter.scalding._
import java.io.File
import java.net.URLClassLoader
class WordCountJob(args:Args) extends Job(args) {

TextLine(args("input"))
         .flatMap('line -> 'word) {line:String => line.split("""\s+""")}
         .groupBy('word){_.size}
         .write(Tsv(args("output")))

}

object WordCountJob{
 val name = "WordCountJob"
 val message = "######################Count######################"


def main(args:Array[String]){

  val master = "geek-HP-G42-Notebook-PC"

  val classesDir =new File("/home/geek/Desktop/simplesbt" + "/target/scala-2.11/classes")

  val loader = classLoaderSetup()
if(args.length !=0){
  Run.run(name,message,args)}
else {
Run.run(name , message , Array("--hdfs","geek-HP-G42-Notebook-PC:9000","--input", s"hdfs://$master:9000/articles.txt" , "--output" ,"/home/geek/Desktop/simplesbt/src/main/data/output.txt" ))

Run.printSomeOutput("src/main/data/output.txt")

}
 }
 def classLoaderSetup(): ClassLoader = {
    val target = new File("/home/geek/Desktop/simplesbt" + "/target/scala-2.11/classes/")
    val classFiles = target :: Nil
    val jarFiles = List(
      "home/geek/Desktop/simplesbt/target/scala-2.11/jars/Activator-Scalding-0.9.0.jar"
    ).map(new File(_))
    val allDeps = jarFiles ::: classFiles
    val depUrls = allDeps map { _.toURI.toURL }
    val loader = new URLClassLoader(depUrls.toArray, getClass.getClassLoader)
    Class.forName(classOf[WordCountJob].getCanonicalName, true, loader)
    Thread.currentThread.setContextClassLoader(loader)
    loader
  }
}





