package cn.misection.jua.luac;

import cn.misection.jua.luac.binchunk.BinaryChunk;
import cn.misection.jua.luac.binchunk.FuncPrototype;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class BinaryChunkTest {

    @Test
    public void undump() throws Exception {
        byte[] hwData = Files.readAllBytes(Paths.get(
                getClass().getResource("/hello_world.luac").toURI()));
        FuncPrototype proto = BinaryChunk.undump(hwData);

        assertEquals("@hello_world.lua", proto.getSource());
        assertEquals(0, proto.getLineDefined());
        assertEquals(0, proto.getLastLineDefined());
        assertEquals(0, proto.getNumParams());
        assertEquals(1, proto.isVararg());
        assertEquals(2, proto.getMaxStackSize());
        assertEquals(4, proto.getCode().size());
        assertEquals(2, proto.getConstants().size());
        assertEquals(1, proto.getUpvalues().size());
        assertEquals(0, proto.getPrototypes().size());
        assertEquals(4, proto.getLineInfo().size());
        assertEquals(0, proto.getLocalVars().size());
        assertEquals(1, proto.getUpvalueNames().size());

        assertEquals("print", proto.getConstants().get(0));
        assertEquals("Hello, World!", proto.getConstants().get(1));
        assertEquals("_ENV", proto.getUpvalueNames().get(0));
    }

}
