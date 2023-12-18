package Project1

import hevs.graphics.FunGraphics

import java.io.PrintStream
import java.net.{InetAddress, Socket}
import scala.io.BufferedSource

object Client extends App{
  val server = new Socket(InetAddress.getByName("localhost"), 1)
  val in = new BufferedSource(server.getInputStream).getLines()
  val out = new PrintStream(server.getOutputStream)
  var counter: Int = 0

  out.println("Hello, Server!")
  while (true){
    if(in.hasNext){
      println("Server says: " + in.next())
    }
  }

  server.close()

}
