package tuple

/**
  * Created by root on 17-1-10.
  */
object tupleToInt {
  def main(args: Array[String]) {
    val t = new Tuple3(1, "null", "")
    val ct=Seq((1, "null", ""),(1, "NULL", ""),(1, 3, ""),(1, 3, 1),(1, 2, ""))
    //全部元素转化为string
    //println("Concatenated String: " + t.toString())
    //println(t.productIterator.contains(""))
    val a=ct.filter{
        case row if row.productIterator.contains("null")=>false
        case row if row.productIterator.contains("NULL")=>false
        case row if row.productIterator.contains("")=>false
        case _=>true
    }
//    a.foreach(row=>println(row))
    ct.foreach{
      row=>
        val a=row.productIterator
        a.contains("null")
        println("this is test ---")
        println(a.length)
    }
    t.productIterator.foreach{
      row=>
        row match {
        case line if scala.util.Try(line.toString.toInt).isFailure=> println("this is not int "+line)
        case _ => println("thi is int  "+row)

        }
    }
  }

}
