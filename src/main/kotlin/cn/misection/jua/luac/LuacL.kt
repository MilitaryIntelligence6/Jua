package cn.misection.jua.luac

import cn.misection.jua.luac.binchunk.BinaryChunk.undump
import cn.misection.jua.luac.binchunk.FuncPrototype
import java.nio.file.Files
import java.nio.file.Paths

internal object LuacL {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            val data = Files.readAllBytes(Paths.get(args[0]))
            val proto = undump(data)
            list(proto)
        }
    }

    private fun list(f: FuncPrototype) {
        printHeader(f)
        printCode(f)
        printDetail(f)
        for (p in f.prototypes) list(p)
    }

    private fun printHeader(f: FuncPrototype) {
        val funcType = if (f.lineDefined > 0) "function" else "main"
        val varargFlag = if (f.isVararg > 0) "+" else ""
        println("\n$funcType <${f.source}:${f.lineDefined},${f.lastLineDefined}> (${f.code.size} instructions)")
        print("${f.numParams}${varargFlag} params, ${f.maxStackSize} slots, ${f.upvalues.size} upvalues, ")
        println("${f.localVars.size} locals, ${f.constants.size} constants, ${f.prototypes.size} functions")
    }

    private fun printCode(f: FuncPrototype) {
        val code = f.code
        val lineInfo = f.lineInfo
        for (i in code.indices) {
            val line = if (lineInfo.isNotEmpty()) lineInfo[i].toString() else "-"
            // code[i].toString(16) 没有前 0
            println("\t${i + 1}\t[${line}]\t${String.format("0x%08X", code[i])}")
        }
    }

    private fun printDetail(f: FuncPrototype) {
        println("constants (${f.constants.size}):", )
        var i = 1
        for (k in f.constants) {
            println("\t${i++}\t${constantToString(k)}")
        }
        i = 0
        println("locals (${f.localVars.size}):", )
        for (locVar in f.localVars) {
            println("\t${i++}\t${locVar.varName}\t${locVar.startPC + 1}\t${locVar.endPC + 1}")
        }
        i = 0
        println("upvalues (${f.upvalues.size}):")
        for (upval in f.upvalues) {
            val name = if (f.upvalueNames.isNotEmpty()) f.upvalueNames[i] else "-"
            println("\t${i++}\t${name}\t${upval.inStack}\t${upval.index}")
        }
    }

    private fun constantToString(k: Any?): String {
        return when (k) {
            null -> "nil"
            is String -> "\"" + k + "\""
            else -> k.toString()
        }
    }
}
