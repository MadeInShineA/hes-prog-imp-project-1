package Project1

import java.io.PrintStream
import java.net.{ServerSocket, Socket}
import scala.collection.mutable.ArrayBuffer

object Server extends App{
  val server: ServerSocket = new ServerSocket(1)
  println("Server started, waiting for connections ")

  val clients: ArrayBuffer[Socket] = new ArrayBuffer()

  while(true){
    val client: Socket = server.accept()
    clients.addOne(client)
    val out = new PrintStream(client.getOutputStream)
    out.println("Hello user")

    val clientHandler: ClientHandler = new ClientHandler(client)
    new Thread(clientHandler).start()
  }
}
