package cn.misection.jua.luac.constant

/**
 * @ClassName LuacFileConst
 * @author JeeffZheng
 * @version 1.0.0
 * @Description TODO
 * @CreateTime 2023年06月05日 21:10:00
 */
internal object LuacFileConst {

    val LUA_SIGNATURE = byteArrayOf(0x1b, 'L'.toByte(), 'u'.toByte(), 'a'.toByte())

    const val LUAC_VERSION = 0x53

    const val LUAC_FORMAT = 0

    val LUAC_DATA = byteArrayOf(0x19, 0x93.toByte(), '\r'.toByte(), '\n'.toByte(), 0x1a, '\n'.toByte())

    const val CINT_SIZE = 4

    const val CSIZET_SIZE = 8

    const val INSTRUCTION_SIZE = 4

    const val LUA_INTEGER_SIZE = 8

    const val LUA_NUMBER_SIZE = 8

    const val LUAC_INT = 0x5678

    const val LUAC_NUM = 370.5
}
