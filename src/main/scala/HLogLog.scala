import com.twitter.algebird.SparseHLL
import com.twitter.algebird.HyperLogLog._
import com.twitter.scalding.{Job , TextLine , IterableSource, Args}

class HyperLogLog(args:Args) extends Job(args) {

IterableSource[Int]((0 to 10000).toList , 'numbers)
                .groupAll{ group => group.hyperLogLog[Int](('numbers -> 'sparseHHL) , 2D)}
                .mapTo('sparseHHL -> 'approximateSize){x:SparseHLL => x.approximateSize.estimate}
                .write(TextLine("output-HLL35.txt"))

run

}
