package Project1

class SquareGrid (var xLength: Int, var yLength: Int) extends Serializable{
  var squares: Array[Array[Square]] = populateGrid()

  def populateGrid(): Array[Array[Square]] ={
    Array.tabulate(xLength, yLength) {(x ,y) => new Square(x, y)}
  }

  def getSquare(x: Int, y: Int): Square = {
    assert(x < xLength)
    assert(y < yLength)
    squares(x)(y)
  }

}
