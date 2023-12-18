package Project1

import java.net.ServerSocket

object Server extends App{
  val server: ServerSocket = new ServerSocket(666)
  println("Server started, waiting for connections ")

  while(true){
    val client = server.accept()
    val clientHandler: ClientHandler = new ClientHandler(client)
    new Thread(clientHandler).start()
  }
}
