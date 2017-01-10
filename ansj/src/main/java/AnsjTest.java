import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * Created by root on 17-1-9.
 */
public class AnsjTest {
    public static void main(String[] args){
        String str="这是第一次使用这个粉刺工具包";
        System.out.println(ToAnalysis.parse(str));
    }
}
