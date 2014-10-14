/**
 * Created by geek on 14/10/14.
 */
import com.twitter.scalding.ScaldingShell

object repl {

  def main(args:Array[String]):Unit= {
    ScaldingShell.process(args)
  }
}
