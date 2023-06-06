package cn.misection.jua.luac.vm

internal object Instruction {

    /**
     * ### 262143
     */
    const val MAXARG_Bx = (1 shl 18) - 1

    /**
     * ### 131071
     */
    const val MAXARG_sBx = MAXARG_Bx shr 1

    @JvmStatic
    fun decodeOpCode(instCode: Int) = OpCode.values()[instCode and 0x3F]

    @JvmStatic
    fun decodeA(instCode: Int) = instCode shr 6 and 0xFF

    @JvmStatic
    fun decodeC(instCode: Int) = instCode shr 14 and 0x1FF

    @JvmStatic
    fun decodeB(instCode: Int) = instCode shr 23 and 0x1FF

    @JvmStatic
    fun decodeBx(instCode: Int) = instCode ushr 14

    @JvmStatic
    fun decodeSBx(instCode: Int) = decodeBx(instCode) - MAXARG_sBx

    @JvmStatic
    fun decodeAx(instCode: Int) = instCode ushr 6
}
