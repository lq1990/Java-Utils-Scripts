import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Scritps {

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm.ss");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {


//        testJavacsv();

        System.out.println(null == null);


    }


    private static void testJavacsv() {

        // read csv
        CSVFixTime csvFixTime = new CSVFixTime();
        List<String[]> strings = csvFixTime.readCSV("e:/Vhl_data_02.csv");
        System.out.println(strings.size());

        // fix timestamp
        for (int i=1; i<strings.size(); ++i) {
            String[] line = strings.get(i);
//            System.out.println(Arrays.deepToString(strings1)); // 当数组中还有数组时，使用

            try {
                Date parse = sdf1.parse(line[1]);
                String format = sdf2.format(parse);
                Timestamp timestamp = Timestamp.valueOf(format);
                line[1] = timestamp.toString();

                strings.set(i, line);



            } catch (ParseException e) {
//                e.printStackTrace();
                strings.remove(i);
                --i;
            }


        }


        // write csv
        csvFixTime.writeCSV("e:/dest.csv", strings);

    }

    private static void testSimpleDataFormat() throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm.ss");
        Date parse = sdf1.parse("09:37.2");
        System.out.println(parse);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf2.format(parse);
        String timestamp = Timestamp.valueOf(format).toString();

        System.out.println(timestamp);

    }

}

