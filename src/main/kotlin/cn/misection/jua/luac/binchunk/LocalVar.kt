package cn.misection.jua.luac.binchunk

import cn.misection.jua.luac.extension.getLuaString
import java.nio.ByteBuffer

/**
 * @ClassName LocVar
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 14:52:00
 */
internal class LocalVar private constructor(
    buf: ByteBuffer,
) {

    companion object {

        @JvmStatic
        fun alloc(buf: ByteBuffer) = LocalVar(buf)
    }

    val varName: String = buf.getLuaString()

    /**
     * ### startPC
     *
     * + buf::getInt 是一个在 buf 中的 consume 动作, 不是一个取值
     */
    val startPC: Int = buf.getInt()

    val endPC: Int = buf.getInt()
}
