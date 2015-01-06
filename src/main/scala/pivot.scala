import com.twitter.scalding._

class pivotUnpivot(args:Args) extends Job(args){

val slaesList = List(
("q1" , "wine" , 100),
("q1" , "beer" , 220),
("q1" , "coffee" , 550), 
("q2" , "coffee" , 5))


val defaultValue = 0

val pivotPipe = IterableSource[(String ,String ,Int)] (slaesList , ('quarter , 'product , 'sales))
                  .groupBy('quarter){group => group.pivot(('product, 'slaes) -> ('wine , 'beer , 'coffee) , defaultValue)}
                  .debug
                  .write(Tsv("pivotOutput"))

val unpivotPipe = pivotPipe.unpivot(('wine , 'beer , 'coffee) -> ('product , 'sales))
                  .write(Tsv("unpivotOutput"))
                   
}
