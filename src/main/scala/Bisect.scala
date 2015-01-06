import com.twitter.scalding._


class Bisect(args:Args) extends Job(args:Args) {

  case class BisectPipe[T](pipe:TypedPipe[T] , size:Int , sortBy:T=>Int){ 
  def top = BisectPipe(pipe.groupAll.sortBy{case x:T => sortBy(x)}.take((size/2).toInt).values,(size/2).toInt , sortBy)
  def bottom = BisectPipe(pipe.groupAll.sortBy{case x:T => sortBy(x)}.drop((size/2).toInt).values,(size/2).toInt , sortBy)
  }

    val sortBy = (x:Int) => x 

BisectPipe(TypedPipe.from( 10 to 1 by -1) , 10 , sortBy)
  }
