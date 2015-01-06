
import com.twitter.scalding.ScaldingShell


object repl {

def main(args:Array[String]){
val retval = ScaldingShell.process(args)

if(!retval) {
sys.exit(1)
    }
  }
}




