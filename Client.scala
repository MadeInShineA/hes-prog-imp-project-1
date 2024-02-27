package Project1

import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{KeyEvent, KeyListener}
import java.io.PrintStream
import java.net.{InetAddress, Socket}
import scala.io.BufferedSource

object Client extends App{
  val server = new Socket(InetAddress.getByName("localhost"), 1)
  val in = new BufferedSource(server.getInputStream).getLines()
  val out = new PrintStream(server.getOutputStream)
  var display: Option[FunGraphics] = None
  var displayWidth: Option[Int] = None
  var displayHeight: Option[Int] = None
  var squareSize: Option[Int] = None
  var squareGrid: Option[SquareGrid] = None
  val keyListener: KeyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {
      out.println(s"Key pressed: ${e.getKeyChar}")
    }

    override def keyReleased(e: KeyEvent): Unit = {

    }
  }

  def drawGrid(): Unit = {

    display.get.setColor(Color.red)

    // Draw the vertical lines on the display
    for (i: Int <- 0 to displayWidth.get by squareSize.get) {
      display.get.drawLine(i, 0, i, displayHeight.get)
    }

    // Draw the horizontal lines on the display
    for (i: Int <- 0 to displayHeight.get by squareSize.get) {
      display.get.drawLine(0, i, displayWidth.get, i)
    }

  }

  out.println("Hello, Server!")
  while (true){
    if(in.hasNext){
      val message: String = in.next()
      println("Server says: " + message)
      if (message.contains("User spawns")){
        val xCoordinate: Int = message.substring(message.indexOf("X : ") + 4, message.indexOf(" | ")).toInt
        val yCoordinate: Int = message.substring(message.indexOf("Y : ") + 4).toInt
        userSpawn(xCoordinate, yCoordinate)
      }
      if(message.contains("Display")){
        displayWidth = Some(message.substring(message.indexOf("width : ") + 8, message.indexOf(" | ")).toInt)
        displayHeight = Some(message.substring(message.indexOf("height : ") + 9).toInt)
        display = Some(new FunGraphics(displayWidth.get, displayHeight.get))
        display.get.setKeyManager(keyListener)
      }

      if(message.contains("Square size")){
        squareSize = Some(message.substring(message.indexOf(" : ") + 3).toInt)
        squareGrid = Some(new SquareGrid(displayWidth.get / squareSize.get, displayHeight.get / squareSize.get))
        drawGrid()
      }

    }
  }

  def userSpawn(x: Int, y: Int, radius: Int = 10): Unit = {
    display.get.drawFillRect(x / squareSize.get * squareSize.get + 1, y / squareSize.get * squareSize.get + 1, squareSize.get - 1, squareSize.get - 1)
  }

  server.close()

}
