package Project1

import java.io
import java.io.{ByteArrayOutputStream, ObjectOutputStream, PrintStream}
import java.net.{ServerSocket, Socket}
import scala.collection.mutable.ArrayBuffer


object Server extends App{
  val server: ServerSocket = new ServerSocket(1)
  println("Server started, waiting for connections ")

  val clients: ArrayBuffer[Socket] = new ArrayBuffer()
  var clientCounter: Int = 0

  val clientHandlers: ArrayBuffer[ClientHandler] = new ArrayBuffer()

  val DISPLAY_WIDTH: Int = 400
  val DISPLAY_HEIGHT: Int = 400

  val SQUARE_SIZE: Int = 20

  val squareGrid: SquareGrid = new SquareGrid(DISPLAY_WIDTH, DISPLAY_HEIGHT)

  def broadcast(message: String): Unit = {
    for(client: Socket <- clients){
      val out = new PrintStream(client.getOutputStream)
      out.println(message)
    }
  }

  def broadcast(message: Array[Byte]): Unit = {
    for (client: Socket <- clients) {
      val out = new ObjectOutputStream((client.getOutputStream))
      out.write(message)
    }
  }

  def serializeSquareGrid(squareGrid: SquareGrid): Array[Byte] = {
    val byteStream = new ByteArrayOutputStream()
    val objectStream = new ObjectOutputStream(byteStream)
    objectStream.writeObject(squareGrid)
    objectStream.close()
    byteStream.toByteArray
  }

  while(true){
    val client: Socket = server.accept()
    clients.addOne(client)
    val out = new PrintStream(client.getOutputStream)
    clientCounter += 1
    println(s"User ${clientCounter} connected")

    out.println(s"Hello ${clientCounter} user")
    out.println(s"Display width : ${DISPLAY_WIDTH} | height : ${DISPLAY_HEIGHT}")
    out.println(s"Square size : ${SQUARE_SIZE}")

//    broadcast(serializeSquareGrid(squareGrid))

    val clientHandler: ClientHandler = new ClientHandler(client, clientCounter)
    clientHandlers.addOne(clientHandler)
    broadcast(s"User spawns X : ${clientHandler.xPosition} | Y : ${clientHandler.yPosition}")
    new Thread(clientHandler).start()
  }
}
