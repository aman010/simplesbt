import com.twitter.scalding._
import java.io.File
import java.net.URLClassLoader

class WordCount(args:Args) extends Job(args) {

TextLine(args("input"))
         .flatMap('line -> 'word) {line:String => line.split("""\s+""")}
         .groupBy('word){_.size}
         .write(Tsv(args("output")))

}









object WordCount{
 val name = "WordCountJob"
 val message = "######################Count######################"


def main(args:Array[String]){

  val master = "geek-HP-G42-Notebook-PC"
 val loader = classLoaderSetup()
if(args.length !=0){
  Runall.run(name,message,args)}
else {
Runall.run(name , message , Array("--hdfs" ,"--input", "hdfs:///articles.txt" , "--output" , "/home/geek/output.txt"))

Runall.printSomeOutput("/home/geek/output.txt")

}
 }
 def classLoaderSetup(): ClassLoader = {
    val target = new File("file:///home/geek/Desktop/simplesbt" + "/target/scala-2.10/classes/")
    val classFiles = target :: Nil
    val jarFiles = List(
      "/home/geek/Desktop/simplesbt/target/scala-2.10/Activator-Scalding-assembly-0.9.1-deps.jar"
    ).map(new File(_))
    val allDeps = jarFiles ::: classFiles
    val depUrls = allDeps map { _.toURI.toURL }
    val loader = new URLClassLoader(depUrls.toArray, getClass.getClassLoader)
    Class.forName(classOf[WordCountJob].getCanonicalName, true, loader)
    Thread.currentThread.setContextClassLoader(loader)
    loader
  }
}





