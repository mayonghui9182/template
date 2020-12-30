package cn.net.mayh.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class test {
    public static void main(String[] args) {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                test.class.getClassLoader());

        SpelExpressionParser parser = new SpelExpressionParser(config);
        Expression expr = parser.parseExpression("'payload'.concat('!')");

         StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("newName", "Mike Tesla");

        parser.parseExpression("Name = #newName").getValue(context);
     }
}
