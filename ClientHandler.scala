package Project1

import java.io.PrintStream
import java.net.Socket
import scala.io.BufferedSource

class ClientHandler (client: Socket) extends Runnable{
  def run(): Unit = {
    val in = new BufferedSource(client.getInputStream).getLines()

    while (in.hasNext) {
      val line = in.next()
      println("Received from client: " + line)
    }

    client.close()

  }

}
