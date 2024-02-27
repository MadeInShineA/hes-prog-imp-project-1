package Project1

class Square (var xPosition: Int ,var yPosition: Int) extends Serializable{
  var isAlive: Boolean = false

  def changeState(): Unit = {
    isAlive = !isAlive
  }

  def deepClone(): Square = {
    val clonedSquare = new Square(this.xPosition, this.yPosition)
    clonedSquare.isAlive = this.isAlive
    clonedSquare
  }
}
