import com.twitter.scalding._
class bisect(args:Args) extends Job(args) {

case class BisectPipe[T](pipe:TypedPipe[T],size:Int,sortBy:T=>Int){
def top = BisectPipe(pipe.groupAll.sortBy{case x:T => sortBy(x)}.take((size/2).toInt).values , (size/2).toInt ,  sortBy)
def bottom = BisectPipe(pipe.groupAll.sortBy{case x:T=> sortBy(x)}.drop((size/2).toInt).values , (size/2).toInt , sortBy)
}


def sortBy(x:Int) = x

BisectPipe(TypedPipe.from((10 to 1 by -1).toList),10, sortBy)
 

//qres$_.top.pipe.dump


//bisect divides the field into two field by using groupall operation i.e. agr to single reducer. 
}

