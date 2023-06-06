package cn.misection.jua.luac.vm

internal enum class OpMode {

    /**
     * ### [  B:9  ][  C:9  ][ A:8  ][OP:6]
     */
    iABC,

    /**
     * ### [      Bx:18     ][ A:8  ][OP:6]
     */
    iABx,

    /**
     * ### [     sBx:18     ][ A:8  ][OP:6]
     */
    iAsBx,

    /**
     * ### [           Ax:26        ][OP:6]
     */
    iAx,
    ;
}
