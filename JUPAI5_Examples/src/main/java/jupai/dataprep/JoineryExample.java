package jupai.dataprep;

import joinery.DataFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JoineryExample {
    public static void main(String args[]){
        try {

            DataFrame<Object>  df1= DataFrame.readCsv ("src/main/resources/data/vgsales.csv")
                    .retain("Year","Genre","Publisher","NA_Sales","EU_Sales","JP_Sales")
                    .describe ();
           System.out.println (df1.toString ());
            System.out.println ("=========================================================================================");
            /*DataFrame<Object>  df= DataFrame.readCsv ("src/main/resources/data/vgsales.csv")
                    .retain("Year", "NA_Sales","EU_Sales","JP_Sales")
                    .groupBy(row ->row.get(0))
                    .mean ();
            System.out.println(df.head(df.length()));
            df.iterrows ().forEachRemaining (System.out::println);

            System.out.println ("=========================================================================================");
            */

        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
