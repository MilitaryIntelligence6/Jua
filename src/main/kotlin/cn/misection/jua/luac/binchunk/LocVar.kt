package cn.misection.jua.luac.binchunk

import java.nio.ByteBuffer

/**
 * @ClassName LocVar
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 14:52:00
 */
class LocVar private constructor(
    buf: ByteBuffer,
) {

    companion object {

        @JvmStatic
        fun alloc(buf: ByteBuffer) = LocVar(buf)
    }

    val varName: String = BinaryChunk.getLuaString(buf)

    val startPC: Int = buf.int

    val endPC: Int = buf.int
}
