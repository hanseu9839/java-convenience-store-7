package store.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String FILE_ERROR = "파일을 읽어오는데 에러가 발생하였습니다.";

    public static List<String> readFile(String filePath) {
        List<String> reads = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String str;
            while((str = br.readLine()) != null) {
                reads.add(str);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_ERROR);
        }

        return reads;
    }

}
