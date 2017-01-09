import org.apache.log4j.Logger

/**
  * Created by root on 17-1-9.
  */
class HelloLog {
  def pringLogLevel(i:Int): Unit ={
    i match {
      case 0=>HelloLog.logger.warn("this is warn:"+i)
      case _=>HelloLog.logger.info("this is warn:"+i)

    }
  }
}

object HelloLog {
  private val logger=Logger.getLogger(HelloLog.getClass)

}

