import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Scritps {

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("mm:ss.SSS");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static void main(String[] args) throws Exception {

        testJavacsv();

//        System.out.println(null == null);

//        Date now = new Date();
//        System.out.println(now.getTime()); // dateTime.getTime() 获取long类型，有了long，可以相加
//
//        System.out.println(sdf2.format(new Date( now.getTime()+now.getTime() )));


    }

    private static Date addOrigin(Date min_sec) throws Exception {
        //        2019/9/27 18:00 所有的 min_sec 都加上这个
        Date origin = sdf3.parse("1970-01-01 00:00");
        Date oldTime = sdf3.parse("2019-09-27 18:00");

//        Date min_sec = sdf1.parse("00:01.5");

//        System.out.println(oldTime);
//        System.out.println(min_sec);
        return new Date(oldTime.getTime() - origin.getTime() +min_sec.getTime());
    }

    private static void testJavacsv() throws Exception {

        // read csv
        CSVFixTime csvFixTime = new CSVFixTime();
        List<String[]> strings = csvFixTime.readCSV("E:/Vhl-data/Vhl_data_02.csv");
        System.out.println(strings.size());

        // fix timestamp
        for (int i=1; i<strings.size(); ++i) { // 从第二行开始，可以保留第一行的headers
            String[] line = strings.get(i);
//            System.out.println(Arrays.deepToString(strings1)); // 当数组中还有数组时，使用

            try {
                Date parse0 = sdf1.parse(line[1]);
                Date parse = addOrigin(parse0);

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
        csvFixTime.writeCSV("e:/Vhl-data/Vhl_data_04_timestamp.csv", strings);

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

    private static void testDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
        Date parse = sdf.parse("12:13.9");

        String format = sdf2.format(parse);
        System.out.println(Timestamp.valueOf(format));
    }

}

