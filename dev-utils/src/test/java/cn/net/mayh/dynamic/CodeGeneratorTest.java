package cn.net.mayh.dynamic;

import cn.net.mayh.util.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void gen() {
        JSONUtils.setObjectMapper(new ObjectMapper());
        CodeGenerator.gen();
    }
}