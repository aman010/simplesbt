import com.twitter.scalding._


class WordCountJob(args:Args) extends Job(args) {

TextLine(args("input"))
         .flatMap('line -> 'word) {line:String => line.split("""\s+""")}
         .groupBy('word){_.size}
         .write(Tsv(args("output")))

}

object WordCountJob{
 val name = "WordCountJob"
 val message = "Find and Count"


def main(args:Array[String]){

if(args.length !=0){
  Run.run(name,message,args)}
else {
Run.run(name , message , Array("--local", "--input", "/home/geek/Desktop/simplesbt/src/main/data/articles.txt","--output" ,"/home/geek/Desktop/simplesbt/src/main/data/output.txt"))

Run.printSomeOutput("/home/geek/Desktop/simplesbt/src/main/data/output.txt")
}
 }
  }




