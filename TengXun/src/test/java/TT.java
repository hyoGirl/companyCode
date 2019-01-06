import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: Administrator
 * @Date: 2018/7/31 0031 13:55
 * @Description:
 */
public class TT {

    public static void main(String[] args) throws Exception {

      /*  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String t = df.format(d);
        long epoch = 0;
        try {
            epoch = df.parse(t).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("时间戳是："+epoch);*/

        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //1361325960
        long epoch = df.parse("2018-08-03 10:06:00").getTime();
        System.err.println("should be ："+epoch);


        Date d=new Date();
        String t=df.format(d);
        epoch=df.parse(t).getTime()/1000;
        System.out.println("t is ："+t+",unix stamp is "+epoch);


        System.out.println(UUID.randomUUID());


    }



}
