import org.apache.log4j.Logger

/**
  * Created by root on 17-1-9.
  */
class HellopLog {
  private val logger=Logger.getLogger(getClass.getName)
  def logTest(i:Int): Unit ={
    i match {
      case 0=>logger.warn("this is i:"+i)
      case _=>logger.info("this is normal:"+i)
    }
  }
}

