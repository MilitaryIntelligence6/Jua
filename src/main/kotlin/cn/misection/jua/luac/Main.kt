package cn.misection.jua.luac

import cn.misection.jua.luac.binchunk.BinaryChunk.undump
import cn.misection.jua.luac.binchunk.FuncPrototype
import java.nio.file.Files
import java.nio.file.Paths

object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size > 0) {
            val data = Files.readAllBytes(Paths.get(args[0]))
            val proto = undump(data)
            list(proto)
        }
    }

    private fun list(f: FuncPrototype) {
        printHeader(f)
        printCode(f)
        printDetail(f)
        for (p in f.prototypes) {
            list(p)
        }
    }

    private fun printHeader(f: FuncPrototype) {
        val funcType = if (f.lineDefined > 0) "function" else "main"
        val varargFlag = if (f.isVararg > 0) "+" else ""
        System.out.printf("\n%s <%s:%d,%d> (%d instructions)\n",
            funcType, f.source, f.lineDefined, f.lastLineDefined,
            f.code.size)
        System.out.printf("%d%s params, %d slots, %d upvalues, ",
            f.numParams, varargFlag, f.maxStackSize, f.upvalues.size)
        System.out.printf("%d locals, %d constants, %d functions\n",
            f.locVars.size, f.constants.size, f.prototypes.size)
    }

    private fun printCode(f: FuncPrototype) {
        val code = f.code
        val lineInfo = f.lineInfo
        for (i in code.indices) {
            val line = if (lineInfo.isNotEmpty()) lineInfo[i].toString() else "-"
            System.out.printf("\t%d\t[%s]\t0x%08X\n", i + 1, line, code[i])
        }
    }

    private fun printDetail(f: FuncPrototype) {
        System.out.printf("constants (%d):\n", f.constants.size)
        var i = 1
        for (k in f.constants) {
            System.out.printf("\t%d\t%s\n", i++, constantToString(k))
        }
        i = 0
        System.out.printf("locals (%d):\n", f.locVars.size)
        for (locVar in f.locVars) {
            System.out.printf("\t%d\t%s\t%d\t%d\n", i++,
                locVar.varName, locVar.startPC + 1, locVar.endPC + 1)
        }
        i = 0
        System.out.printf("upvalues (%d):\n", f.upvalues.size)
        for (upval in f.upvalues) {
            val name = if (f.upvalueNames.isNotEmpty()) f.upvalueNames[i] else "-"
            System.out.printf("\t%d\t%s\t%d\t%d\n", i++,
                name, upval.instack, upval.idx)
        }
    }

    private fun constantToString(k: Any?): String {
        return when (k) {
            null -> "nil"
            is String -> "\"" + k + "\""
            else -> k.toString()
        }
    }

    private class Prototype
}
