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
    fun getOpCode(i: Int) = OpCode.values()[i and 0x3F]

    @JvmStatic
    fun getA(i: Int) = i shr 6 and 0xFF

    @JvmStatic
    fun getC(i: Int) = i shr 14 and 0x1FF

    @JvmStatic
    fun getB(i: Int) = i shr 23 and 0x1FF

    @JvmStatic
    fun getBx(i: Int) = i ushr 14

    @JvmStatic
    fun getSBx(i: Int) = getBx(i) - MAXARG_sBx

    @JvmStatic
    fun getAx(i: Int) = i ushr 6
}
