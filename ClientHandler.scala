package Project1

import java.io.PrintStream
import java.net.Socket
import scala.io.BufferedSource

class ClientHandler (val client: Socket, val id: Int) extends Runnable{
  val random = new scala.util.Random
  var xPosition = random.nextInt(200)
  var yPosition = random.nextInt(200)
  def run(): Unit = {
    val out = new PrintStream(client.getOutputStream)
    val in = new BufferedSource(client.getInputStream).getLines()
    while (in.hasNext) {
      val line = in.next()
      Server.broadcast(s"Received from client ${id} : ${line}")
//      out.println(s"Received from client ${id} : ${line}")

    }

    client.close()

  }

}
