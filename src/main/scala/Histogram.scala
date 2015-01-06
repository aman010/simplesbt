import com.twitter.scalding.mathematics.Histogram
import com.twitter.scalding.{Tsv,IterableSource,Job , Args}

class histogram(args:Args) extends Job(args){ 


val uploadedImageList = List(
   ("20140101" , 12223),
   ("20140101" , 222011),
   ("20140101" , 451621),
   ("20140101" , 575007))

val orderPipe = IterableSource[(String , Int)](uploadedImageList , ('date , 'imageSize))
          .groupBy('date){group => group.histogram(('imageSize , 'histogram)) }
          .map('histogram ->('q1 , 'median , 'q3 , 'coefficientOfDispersion , 'innerQuartileRange)){x:Histogram => (x.q1, x.median , x.q3 , x.coefficientOfDispersion , x.innerQuartileRange)}
          .debug
          .write(Tsv("output-hist"))          

}

