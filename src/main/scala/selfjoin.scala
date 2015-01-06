import com.twitter.scalding._

class selfjoin(args:Args) extends Job(args) { 


val slaesList = List(
("q1" , "wine" , 100),
("q1" , "beer" , 220),
("q1" , "coffee" , 550), 
("q2" , "coffee" , 5))

val pipe = IterableSource[(String ,String ,Int)] (slaesList , ('quarter , 'product , 'sales))

val copy_pipe = pipe.rename(('quarter , 'product , 'sales) ->('quarter2 , 'product2 , 'sales2))


val join = pipe.joinWithSmaller('quarter -> 'quarter2 , copy_pipe).write(TextLine("join-output.txt"))


}
