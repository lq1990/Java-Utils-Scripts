import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author liuqiang
 * @since 2020-12-08 21:21
 */
public class IKUtils {

    public static void main(String[] args) throws IOException {
        String split = split("今天天气不错", "\n");
        System.out.println(split);
    }

    public static String split(String content, String splitChar) throws IOException {
        StringReader stringReader = new StringReader(content);
        IKSegmenter ikSegmenter = new IKSegmenter(stringReader, true);

        Lexeme lexeme = null;
        StringBuilder stringBuilder = new StringBuilder("");

        while ((lexeme = ikSegmenter.next()) != null) {
            stringBuilder.append(lexeme.getLexemeText() + splitChar);
        }

        return stringBuilder.toString();
    }
}
