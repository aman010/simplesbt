import com.twitter.scalding._
import cascading.pipe.Pipe



abstract class VectorSimilarities(args:Args) extends Job(args){


def input(userField:Symbol , itemField:Symbol , ratingField:Symbol):Pipe

val ratings = input('user , 'item , 'rating)

val ratingsWithSize = 

         ratings.groupBy('item){_.size('numRaters) }
                .rename('item -> 'itemX)
                .joinWithLarger('itemX -> 'item , ratings).discard('itemX)
                .filter('numRaters) { numRaters:Long => numRaters >= 3  && numRaters <= 10000 }

val ratings2 = ratingsWithSize.rename(('user , 'item , 'rating , 'numRaters) -> ('usre2 , 'item2 , 'rating2 , 'numRaters2))


val ratingPairs = ratingsWithSize.joinWithSmaller('user ->'user2 , ratings2) 
                                 .filter('item ,'item2){items :(String , String) => items._1 < items._2 }.project('item , 'rating , 'numRaters , 'item2 , 'rating2 , 'numRater2)


/*val vectorCalcs = ratingPairs.map(('rating , 'rating2) ->('ratingProd , 'ratingSq , 'rating2Sq)){ ratings:(Double , Double) =>
(ratings._ * ratings_2 , scala.map.pow(ratings._ , 2) , scala.math.pow(rating._2, 2)) 
}*/

}
