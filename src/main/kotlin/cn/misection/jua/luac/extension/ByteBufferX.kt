package cn.misection.jua.luac.extension

import java.nio.ByteBuffer

/**
 * @ClassName ByteBufferX
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 16:44:00
 */
internal fun ByteBuffer.getLuaString(): String {
    var size: Int = this.get().toInt() and 0xFF
    if (size == 0) return ""
    // size_t
    if (size == 0xFF) size = this.getLong().toInt()
    val byteArray = this.getBytes(size - 1)
    return String(byteArray)
}

internal fun ByteBuffer.getBytes(size: Int) = ByteArray(size).also { this.get(it) }
