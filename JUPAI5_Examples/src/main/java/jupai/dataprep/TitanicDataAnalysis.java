package jupai.dataprep;

//import sun.tools.jconsole.Tab;
import tech.tablesaw.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TitanicDataAnalysis {
    Table titanicData;
    String dataPath = "src/main/resources/data/titanic.csv";

    public TitanicDataAnalysis() {
    }

    public static void main(String[] args) {
        TitanicDataAnalysis tda = new TitanicDataAnalysis ();
        try {
            tda.titanicData = tda.loadDataFromCVS (tda.dataPath);
            //getting the Structure of the data
            String structure = tda.getDataInfoStructure (tda.titanicData);
            System.out.println (structure);
            //getting Data summery
            System.in.read ();
            String summary = tda.getDataSummary (tda.titanicData);
            System.out.println (summary);
            System.in.read ();
            // Adding date Column
            Table dataWithDate = tda.addDateColumnToData (tda.titanicData);
            System.out.println ("=====================================================================================");
            System.out.println (dataWithDate.structure ());
            System.in.read ();
            //Sorting on the added Date Field
            Table sortedData = dataWithDate.sortAscendingOn ("Fake Date");

            //getting the first 10 rows
            System.out.println ("Printing the first rows of the table");
            System.in.read ();
            Table firstTenRows = sortedData.first (50);

            System.out.println (firstTenRows);
            System.in.read ();
            // mapping the (sex) column
            Table mappedData = tda.mapTextColumnToNumber (tda.titanicData);
            Table firstFiveRows = mappedData.first (5);
            System.out.println ("=====================================================================================");
            System.out.println (firstFiveRows);
            System.in.read ();
            // mapping the (survived) column
            Table dataMap = tda.mapNumberColumnToText(tda.titanicData);
            Table first10Rows = dataMap.first(10);
            System.out.println ("=====================================================================================");
            System.out.println (first10Rows);
            System.in.read ();
            Table classMap = tda.mappingPclassColumn(tda.titanicData);
            Table first5Rows = dataMap.first(5);
            System.out.println ("=====================================================================================");
            System.out.println (first5Rows);

        } catch (IOException e) {
            e.printStackTrace ();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///  Load Data From CSV File
    public Table loadDataFromCVS(String path) throws IOException {
        Table titanicData = Table.read ().csv (path);
        return titanicData;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// get the structure of the data
    public String getDataInfoStructure(Table data) {
        Table dataStructure = data.structure ();
        return dataStructure.toString ();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //get Data Summary
    public String getDataSummary(Table data) {
        Table summary = data.summary ();
        return summary.toString ();
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Adding Columns
    public Table addDateColumnToData(Table data) {
        int rowCount = data.rowCount ();
        List<LocalDate> dateList = new ArrayList<LocalDate> ();
        for (int i = 0; i < rowCount; i++) {
            dateList.add (LocalDate.of (2021, 3, i % 31 == 0 ? 1 : i % 31));
        }
        DateColumn dateColumn = DateColumn.create ("Fake Date", dateList);
        data.insertColumn (data.columnCount (), dateColumn);
        return data;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // mapping text data to numeric values on the sex field female=1,male=0 and adding a column named gender
    public Table mapTextColumnToNumber(Table data) {
        NumberColumn mappedGenderColumn = null;
        StringColumn gender = (StringColumn) data.column ("Sex");
        List<Number> mappedGenderValues = new ArrayList<Number> ();
        for (String v : gender) {
            if ((v != null) && (v.equals ("female"))) {
                mappedGenderValues.add (new Double (1));
            } else {
                mappedGenderValues.add (new Double (0));
            }
        }
        mappedGenderColumn = DoubleColumn.create ("gender", mappedGenderValues);
        data.addColumns (mappedGenderColumn);
        return data;
    }
    public Table mapNumberColumnToText (Table data){
        StringColumn mappedSurvivedColumn = null;
        NumberColumn survivor =(NumberColumn) data.column("survived");
        List<String> mapppedSurvivedValues = new ArrayList<>();
        for (Object n : survivor){
            if (( n != null) && (n.equals(1))){
                mapppedSurvivedValues.add(new String("alive"));
            }
            else {
                mapppedSurvivedValues.add(new String("dead"));
            }
        }
        mappedSurvivedColumn = StringColumn.create("suvivors" , mapppedSurvivedValues);
        data.addColumns(mappedSurvivedColumn);
        return data;
    }
    public Table mappingPclassColumn (Table data){
        StringColumn mappedPclassColumn = null;
        NumberColumn pClass =(NumberColumn) data.column("pclass");
        List<String> mapppedPclassValues = new ArrayList<>();
        for (Object p : pClass){
            if (( p != null) && (p.equals(1))){
                mapppedPclassValues.add(new String("High Class"));
            }
            else if (( p != null) && (p.equals(2))){
                mapppedPclassValues.add(new String("Middle Class"));
            }
            else {
                mapppedPclassValues.add(new String("Lower Class"));
            }
        }
        mappedPclassColumn = StringColumn.create("Classes" , mapppedPclassValues);
        data.addColumns(mappedPclassColumn);
        return data;
    }
}

