package cn.misection.jua.luac;

import cn.misection.jua.luac.binchunk.BinaryChunk;
import cn.misection.jua.luac.binchunk.FuncPrototype;
import cn.misection.jua.luac.binchunk.LocVar;
import cn.misection.jua.luac.binchunk.Upvalue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            byte[] data = Files.readAllBytes(Paths.get(args[0]));
            FuncPrototype proto = BinaryChunk.undump(data);
            list(proto);
        }
    }

    private static void list(FuncPrototype f) {
        printHeader(f);
        printCode(f);
        printDetail(f);
        for (FuncPrototype p : f.getPrototypes()) {
            list(p);
        }
    }

    private static void printHeader(FuncPrototype f) {
        String funcType = f.getLineDefined() > 0 ? "function" : "main";
        String varargFlag = f.isVararg() > 0 ? "+" : "";

        System.out.printf("\n%s <%s:%d,%d> (%d instructions)\n",
                funcType, f.getSource(), f.getLineDefined(), f.getLastLineDefined(),
                f.getCode().size());

        System.out.printf("%d%s params, %d slots, %d upvalues, ",
                f.getNumParams(), varargFlag, f.getMaxStackSize(), f.getUpvalues().size());

        System.out.printf("%d locals, %d constants, %d functions\n",
                f.getLocVars().size(), f.getConstants().size(), f.getPrototypes().size());
    }

    private static void printCode(FuncPrototype f) {
        List<Integer> code = f.getCode();
        List<Integer> lineInfo = f.getLineInfo();
        for (int i = 0; i < code.size(); i++) {
            String line = lineInfo.size() > 0 ? String.valueOf(lineInfo.get(i)) : "-";
            System.out.printf("\t%d\t[%s]\t0x%08X\n", i + 1, line, code.get(i));
        }
    }

    private static void printDetail(FuncPrototype f) {
        System.out.printf("constants (%d):\n", f.getConstants().size());
        int i = 1;
        for (Object k : f.getConstants()) {
            System.out.printf("\t%d\t%s\n", i++, constantToString(k));
        }

        i = 0;
        System.out.printf("locals (%d):\n", f.getLocVars().size());
        for (LocVar locVar : f.getLocVars()) {
            System.out.printf("\t%d\t%s\t%d\t%d\n", i++,
                    locVar.getVarName(), locVar.getStartPC() + 1, locVar.getEndPC() + 1);
        }

        i = 0;
        System.out.printf("upvalues (%d):\n", f.getUpvalues().size());
        for (Upvalue upval : f.getUpvalues()) {
            String name = f.getUpvalueNames().size() > 0 ? f.getUpvalueNames().get(i) : "-";
            System.out.printf("\t%d\t%s\t%d\t%d\n", i++,
                    name, upval.getInstack(), upval.getIdx());
        }
    }

    private static String constantToString(Object k) {
        if (k == null) {
            return "nil";
        } else if (k instanceof String) {
            return "\"" + k + "\"";
        } else {
            return k.toString();
        }
    }

    private static class Prototype {
    }
}
