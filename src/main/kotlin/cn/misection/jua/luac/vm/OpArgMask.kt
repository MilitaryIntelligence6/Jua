package cn.misection.jua.luac.vm

internal enum class OpArgMask {

    /**
     * ### argument is not used
     */
    OpArgN,

    /**
     * ### argument is used
     */
    OpArgU,

    /**
     * ### argument is a register or a jump offset
     */
    OpArgR,

    /**
     * ### argument is a constant or register/constant
     */
    OpArgK
}
