package cn.misection.jua.luac.binchunk

import cn.misection.jua.luac.binchunk.FuncPrototype.Companion.alloc
import cn.misection.jua.luac.constant.LuacFileConst
import java.nio.ByteOrder
import cn.misection.jua.luac.extension.getBytes
import java.lang.RuntimeException
import java.nio.ByteBuffer

internal object BinaryChunk {

    @JvmStatic
    fun undump(data: ByteArray?): FuncPrototype {
        val buf = ByteBuffer.wrap(data)
            .order(ByteOrder.LITTLE_ENDIAN)
        checkHead(buf)
        // size_upvalues
        buf.get()
        // mainFunc
        return alloc(buf, "")
    }

    private fun checkHead(buf: ByteBuffer) {
        if (!LuacFileConst.LUA_SIGNATURE.contentEquals(buf.getBytes(4))) throw RuntimeException("not a precompiled chunk!")
        if (buf.get().toInt() != LuacFileConst.LUAC_VERSION) throw RuntimeException("version mismatch!")
        if (buf.get().toInt() != LuacFileConst.LUAC_FORMAT) throw RuntimeException("format mismatch!")
        if (!LuacFileConst.LUAC_DATA.contentEquals(buf.getBytes(6))) throw RuntimeException("corrupted!")
        if (buf.get().toInt() != LuacFileConst.CINT_SIZE) throw RuntimeException("int size mismatch!")
        if (buf.get().toInt() != LuacFileConst.CSIZET_SIZE) throw RuntimeException("size_t size mismatch!")
        if (buf.get().toInt() != LuacFileConst.INSTRUCTION_SIZE) throw RuntimeException("instruction size mismatch!")
        if (buf.get().toInt() != LuacFileConst.LUA_INTEGER_SIZE) throw RuntimeException("lua_Integer size mismatch!")
        if (buf.get().toInt() != LuacFileConst.LUA_NUMBER_SIZE) throw RuntimeException("lua_Number size mismatch!")
        if (buf.long != LuacFileConst.LUAC_INT.toLong()) throw RuntimeException("endianness mismatch!")
        if (buf.double != LuacFileConst.LUAC_NUM) throw RuntimeException("float format mismatch!")
    }
}
