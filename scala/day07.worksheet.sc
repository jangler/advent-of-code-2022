import scala.util.{Try, Success, Failure}

class FsNode(
    val name: String,
    val size: Int,
    val parent: Option[FsNode],
    var children: Map[String, FsNode]
):
  override def toString: String = name

  def directories: Seq[FsNode] =
    Seq(this).filter(_.size == 0) ++ children.values.flatMap(_.directories)

  def totalSize: Int = size + children.values.map(_.totalSize).sum

enum Line:
  case CdIn(val dir: String)
  case CdOut
  case CdRoot
  case Ls
  case LsFile(val size: Int, val name: String)
  case LsDir(val name: String)

import Line.*

def parseLine(line: String): Option[Line] =
  if line == "$ cd .." then Some(CdOut)
  else if line == "$ cd /" then Some(CdRoot)
  else if line startsWith "$ cd " then Some(CdIn(line.drop(5)))
  else if line == "$ ls" then Some(Ls)
  else if line startsWith "dir " then Some(LsDir(line.drop(4)))
  else
    "(\\d+) (.+)".r
      .findPrefixMatchOf(line)
      .map(m => LsFile(size = m.group(1).toInt, name = m.group(2)))

// Not sure how to make this less ugly!
def parseInput(lines: Seq[String]): Try[FsNode] =
  val root = FsNode("/", 0, None, Map())
  var cwd = root

  try
    for line <- lines do
      parseLine(line) match
        case None => throw Exception(s"$line: unrecognized format")
        case Some(value) =>
          value match
            case CdIn(dir) =>
              cwd.children.get(dir) match
                case None        => throw Exception(s"$line: no such directory")
                case Some(child) => cwd = child
            case CdOut =>
              cwd.parent match
                case None =>
                  throw Exception(s"$line: working directory is root")
                case Some(parent) => cwd = parent
            case CdRoot => cwd = root
            case Ls     => ()
            case LsFile(size, name) =>
              cwd.children =
                cwd.children + (name -> FsNode(name, size, Some(cwd), Map()))
            case LsDir(name) =>
              cwd.children =
                cwd.children + (name -> FsNode(name, 0, Some(cwd), Map()))

    Success(root)
  catch case e: Exception => Failure(e)

// example
val exampleLines = io.Source.fromFile("examples/day07.txt").getLines.toList
exampleLines.map(parseLine)
val exampleTree = parseInput(exampleLines).get
val exampleDirs = exampleTree.directories
val exampleSizes = exampleDirs.map(_.totalSize)
exampleSizes.filter(_ <= 100_000).sum

// part one answer
val inputLines = io.Source.fromFile("input/day07.txt").getLines.toList
val tree = parseInput(inputLines).get
tree.directories.map(_.totalSize).filter(_ <= 100_000).sum

val TotalSpace = 70_000_000
val RequiredSpace = 30_000_000

def smallestDirWithMinimumSize(root: FsNode, size: Int): Option[FsNode] =
  root.directories.filter(_.totalSize >= size).sortBy(_.totalSize).headOption

def requiredAdditionalSpace(root: FsNode): Int =
  math.max(0, RequiredSpace - (TotalSpace - root.totalSize))

// example
val exampleSpace = requiredAdditionalSpace(exampleTree)
smallestDirWithMinimumSize(exampleTree, exampleSpace)

// part two answer
val space = requiredAdditionalSpace(tree)
smallestDirWithMinimumSize(tree, space).map(_.totalSize)
