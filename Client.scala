package Project1

import java.io.PrintStream
import java.net.{InetAddress, Socket}
import scala.io.BufferedSource

object Client extends App{
  val server = new Socket(InetAddress.getByName("localhost"), 666)
  val in = new BufferedSource(server.getInputStream).getLines()
  val out = new PrintStream(server.getOutputStream)

  out.println("Hello, Server!")
  println("Server says: " + in.next())

  server.close()

}
