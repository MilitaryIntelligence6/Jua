package cn.misection.jua.luac.binchunk

import java.nio.ByteBuffer

/**
 * @ClassName Upvalue
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 15:23:00
 */
internal class Upvalue private constructor(
    buf: ByteBuffer,
) {

    companion object {

        @JvmStatic
        fun alloc(buf: ByteBuffer) = Upvalue(buf)
    }

    val inStack: Byte = buf.get()

    val index: Byte = buf.get()
}
