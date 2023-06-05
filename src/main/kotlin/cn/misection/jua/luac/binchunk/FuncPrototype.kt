package cn.misection.jua.luac.binchunk

import cn.misection.jua.luac.constant.LuaTag
import cn.misection.jua.luac.extension.getLuaString
import java.nio.ByteBuffer

/**
 * @ClassName Prototype
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 15:45:00
 */
internal class FuncPrototype private constructor(
    buf: ByteBuffer,
    parentSource: String,
) {

    companion object {

        @JvmStatic
        fun alloc(buf: ByteBuffer, parentSource: String) = FuncPrototype(buf, parentSource)
    }

    val source: String = buf.getLuaString()
        .takeIf(String::isNotEmpty)
        ?: parentSource

    val lineDefined: Int = buf.getInt()

    val lastLineDefined: Int = buf.getInt()

    val numParams: Byte = buf.get()

    val isVararg: Byte = buf.get()

    val maxStackSize: Byte = buf.get()

    val code: List<Int> = buf.readCode()

    val constants: List<Any?> = buf.readConstants()

    val upvalues: List<Upvalue> = buf.readUpvalues()

    val prototypes: List<FuncPrototype> = buf.readPrototypes(parentSource)

    val lineInfo: List<Int> = buf.readLineInfo()

    val localVars: List<LocalVar> = buf.readLocVars()

    val upvalueNames: List<String> = buf.readUpvalueNames()
}

private fun ByteBuffer.readCode(): List<Int> = mutableListOf<Int>().also {
    for (i in 0 until this.getInt()) it.add(this.getInt())
}

private fun ByteBuffer.readConstants(): List<Any?> = mutableListOf<Any?>().also {
    for (i in 0 until this.getInt()) it.add(this.readConstant())
}

private fun ByteBuffer.readConstant(): Any? = when (this.get().toInt()) {
    LuaTag.TAG_NIL -> null
    LuaTag.TAG_BOOLEAN -> this.get().toInt() != 0
    LuaTag.TAG_INTEGER -> this.getLong()
    LuaTag.TAG_NUMBER -> this.getDouble()
    LuaTag.TAG_SHORT_STR, LuaTag.TAG_LONG_STR -> this.getLuaString()
    else -> throw IllegalArgumentException("corrupted!")
}

private fun ByteBuffer.readUpvalues(): List<Upvalue> = mutableListOf<Upvalue>().also {
    for (i in 0 until this.getInt()) it.add(Upvalue.alloc(this))
}

private fun ByteBuffer.readPrototypes(parentSource: String): List<FuncPrototype> = mutableListOf<FuncPrototype>().also {
    for (i in 0 until this.getInt()) it.add(FuncPrototype.alloc(this, parentSource))
}

private fun ByteBuffer.readLineInfo(): List<Int> = mutableListOf<Int>().also {
    for (i in 0 until this.getInt()) it.add(this.getInt())
}

private fun ByteBuffer.readLocVars(): List<LocalVar> = mutableListOf<LocalVar>().also {
    for (i in 0 until this.getInt()) it.add(LocalVar.alloc(this))
}

private fun ByteBuffer.readUpvalueNames(): List<String> = mutableListOf<String>().also {
    for (i in 0 until this.getInt()) it.add(this.getLuaString())
}
