import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public
class CSVFixTime {

    private SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm.ss");
    private  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private char seperator = ',';

    public CSVFixTime() {
    }

    public List<String[]> readCSV(String path) {
        LinkedList<String[]> result = new LinkedList<String[]>();

        try {
            CsvReader csvReader = new CsvReader(path, seperator, Charset.forName("UTF-8"));

            while(csvReader.readRecord()) {
                String[] line = csvReader.getValues();

                // 处理line[1] 即时间
//                try {
//
//                    Date parse = sdf1.parse(line[1]);
//                    String format = sdf2.format(parse);
//                    Timestamp timestamp = Timestamp.valueOf(format);
//                    line[1] = timestamp.toString();
//
                    result.add(line);
//                } catch (ParseException e) {
//
//                }
            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
//            System.exit(0);
        }

        return result;

    }


    public void writeCSV(String path, List<String[]> content) {
        try {
            CsvWriter csvWriter = new CsvWriter(path, seperator, Charset.forName("UTF-8"));

            // write content
            for (int i=0; i<content.size(); ++i) {
                String[] line = content.get(i);
                csvWriter.writeRecord(line);
            }

            // close
            csvWriter.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }


    public char getSeperator() {
        return seperator;
    }

    public void setSeperator(char seperator) {
        this.seperator = seperator;
    }
}