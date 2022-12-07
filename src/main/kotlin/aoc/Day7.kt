package aoc

private val cdRegex = Regex("\\$ cd (.+)")
private val lsRegex = Regex("\\$ ls")

private val dirRegex = Regex("dir (\\w+)")
private val fileRegex = Regex("(\\d+) ([\\w.]+)")

private const val DIR_SIZE_LIMIT = 100_000

private const val TOTAL_SPACE = 70_000_000
private const val UPDATE_SIZE = 30_000_000

/**
 * [AoC 2022: Day 7](https://adventofcode.com/2022/day/7)
 */
fun findTotalSizeOf100kDirs(cmdHistory: List<String>): Int {
    val fs = FileSystem()

    emulateCmd(cmdHistory, fs)

    var size = 0
    fs.walk { file ->
        if (file.type == FileType.DIR && file.size < DIR_SIZE_LIMIT) {
            size += file.size
        }
    }

    return size
}

fun findDirToDelete(cmdHistory: List<String>): Int {
    val fs = FileSystem()

    emulateCmd(cmdHistory, fs)

    val freeSpace = TOTAL_SPACE - fs.root.size
    val requiredSpace = UPDATE_SIZE - freeSpace

    val candidates = mutableListOf<File>()
    fs.walk { file ->
        if (file.type == FileType.DIR &&
            file.size > requiredSpace) {
            candidates += file
        }
    }

    return candidates.minByOrNull { it.size }!!.size
}

private fun emulateCmd(cmdHistory: List<String>, fs: FileSystem) {
    for (cmd in cmdHistory) {
        if (cdRegex.matches(cmd)) {
            val dir = cdRegex.matchEntire(cmd)?.groupValues?.get(1) ?: continue
            fs.cd(dir)
        } else if (lsRegex.matches(cmd)) {
            // do nothing
        } else if (dirRegex.matches(cmd)) {
            val dir = dirRegex.matchEntire(cmd)?.groupValues?.get(1) ?: continue
            fs.dir(dir)
        } else if (fileRegex.matches(cmd)) {
            val (size, name) = fileRegex.matchEntire(cmd)?.groupValues?.let { it[1].toInt() to it[2] } ?: continue
            fs.touch(name, size)
        }
    }
}

private class FileSystem {

    val root = File("/", FileType.DIR, 0, null, mutableListOf())

    private var workingDirectory: File? = root

    fun dir(name: String) {
        val dir = File(name, FileType.DIR, 0, workingDirectory, mutableListOf())
        workingDirectory?.children?.add(dir)
    }

    fun touch(name: String, size: Int) {
        val file = File(name, FileType.FILE, size, workingDirectory)
        workingDirectory?.children?.add(file)
    }

    fun cd(dir: String) {
        if (dir == "/") return // we are already here

        workingDirectory = if (dir == "..") {
            workingDirectory?.parent
        } else {
            workingDirectory?.children?.find { it.type == FileType.DIR && it.name == dir }
        }
    }

    fun walk(visitor: (File) -> Unit) = walk(root, visitor)

    private fun walk(dir: File, visitor: (File) -> Unit) {
        visitor(dir)
        dir.children.forEach { walk(it, visitor) }
    }
}

private class File(
    val name: String,
    val type: FileType,
    private val _size: Int,
    val parent: File?,
    val children: MutableList<File> = mutableListOf()
) {

    val size
        get() = getTotalSize()

    private fun getTotalSize(): Int {
        return when (type) {
            FileType.FILE -> _size
            else -> children.map { it.size }.fold(0, Int::plus)
        }
    }

    override fun toString(): String = "$type $name ${getTotalSize()}"
}

private enum class FileType { FILE, DIR }