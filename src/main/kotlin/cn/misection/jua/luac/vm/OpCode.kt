package cn.misection.jua.luac.vm

internal enum class OpCode(

    /**
     * ### operator is a test (next instruction must be a jump)
     */
    val testFlag: Int,

    /**
     * ### instruction set register A
     */
    val setAFlag: Int,

    /**
     * ### B arg mode
     */
    val argBMode: OpArgMask,

    /**
     * ### C arg mode
     */
    val argCMode: OpArgMask,

    /**
     * ### op mode
     */
    val opMode: OpMode,
) {

    /*       T  A    B       C     mode */

    /**
     * ### R(A) := R(B)
     */
    MOVE(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := Kst(Bx)
     */
    LOADK(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgN, OpMode.iABx),

    /**
     * ### R(A) := Kst(extra arg)
     */
    LOADKX(0, 1, OpArgMask.OpArgN, OpArgMask.OpArgN, OpMode.iABx),

    /**
     * ### R(A) := (bool)B; if (C) pc++
     */
    LOADBOOL(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### R(A), R(A+1), ..., R(A+B) := nil
     */
    LOADNIL(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := UpValue\[B\]
     */
    GETUPVAL(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := UpValue\[B\]\[RK(C)\]
     */
    GETTABUP(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := R(B)\[RK(C)\]
     */
    GETTABLE(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### UpValue\[A\]\[RK(B)\] := RK(C)
     */
    SETTABUP(0, 0, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### UpValue\[B\] := R(A)
     */
    SETUPVAL(0, 0, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A)\[RK(B)\] := RK(C)
     */
    SETTABLE(0, 0, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := {} (size = B,C)
     */
    NEWTABLE(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### R(A+1) := R(B); R(A) := R(B)\[RK(C)\]
     */
    SELF(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) + RK(C)
     */
    ADD(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) - RK(C)
     */
    SUB(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) * RK(C)
     */
    MUL(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) % RK(C)
     */
    MOD(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) ^ RK(C)
     */
    POW(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) / RK(C)
     */
    DIV(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),  IDIV(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),  // R(A) := RK(B) // RK(C)

    /**
     * ### R(A) := RK(B) & RK(C)
     */
    BAND(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) | RK(C)
     */
    BOR(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) ~ RK(C)
     */
    BXOR(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) << RK(C)
     */
    SHL(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := RK(B) >> RK(C)
     */
    SHR(0, 1, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### R(A) := -R(B)
     */
    UNM(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := ~R(B)
     */
    BNOT(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := not R(B)
     */
    NOT(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := length of R(B)
     */
    LEN(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A) := R(B).. ... ..R(C)
     */
    CONCAT(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgR, OpMode.iABC),

    /**
     * ### pc+=sBx; if (A) close all upvalues >= R(A - 1)
     */
    JMP(0, 0, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iAsBx),

    /**
     * ### if ((RK(B) == RK(C)) ~= A) then pc++
     */
    EQ(1, 0, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### if ((RK(B) <  RK(C)) ~= A) then pc++
     */
    LT(1, 0, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### if ((RK(B) <= RK(C)) ~= A) then pc++
     */
    LE(1, 0, OpArgMask.OpArgK, OpArgMask.OpArgK, OpMode.iABC),

    /**
     * ### if not (R(A) <=> C) then pc++
     */
    TEST(1, 0, OpArgMask.OpArgN, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### if (R(B) <=> C) then R(A) := R(B) else pc++
     */
    TESTSET(1, 1, OpArgMask.OpArgR, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### R(A), ... ,R(A+C-2) := R(A)(R(A+1), ... ,R(A+B-1))
     */
    CALL(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### return R(A)(R(A+1), ... ,R(A+B-1))
     */
    TAILCALL(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### return R(A), ... ,R(A+B-2)
     */
    RETURN(0, 0, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### R(A)+=R(A+2); if R(A) <?= R(A+1) then { pc+=sBx; R(A+3)=R(A) }
     */
    FORLOOP(0,1,OpArgMask.OpArgR,OpArgMask.OpArgN, OpMode.iAsBx),

    /**
     * ### R(A)-=R(A+2); pc+=sBx
     */
    FORPREP(0, 1, OpArgMask.OpArgR, OpArgMask.OpArgN, OpMode.iAsBx),

    /**
     * ### R(A+3), ... ,R(A+2+C) := R(A)(R(A+1), R(A+2));
     */
    TFORCALL(0, 0, OpArgMask.OpArgN, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### if R(A+1) ~= nil then { R(A)=R(A+1); pc += sBx }
     */
    TFORLOOP(0,1,OpArgMask.OpArgR,OpArgMask.OpArgN, OpMode.iAsBx),

    /**
     * ### R(A)\[(C-1)*FPF+i\] := R(A+i), 1 <= i <= B
     */
    SETLIST(0, 0, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iABC),

    /**
     * ### R(A) := closure(KPROTO\[Bx\])
     */
    CLOSURE(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABx),

    /**
     * ### R(A), R(A+1), ..., R(A+B-2) = vararg
     */
    VARARG(0, 1, OpArgMask.OpArgU, OpArgMask.OpArgN, OpMode.iABC),

    /**
     * ### extra (larger) argument for previous opcode
     */
    EXTRAARG(0, 0, OpArgMask.OpArgU, OpArgMask.OpArgU, OpMode.iAx);
}
