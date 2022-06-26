package ru.brkmed.dtk;


import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        //String[] title = {"id", "surname", "adress", "type"};
        // File fileSVC = new File( "test.svc");
//        List<Report> reports = parseCSV(title, "test.csv");
//        // reports.stream().
//        for (Report report : reports) {
//            System.out.println(report.getId() + " " + report.getSurname() + " " + report.getAdress() + " " + report.getType());
//        }
        Map<Integer, List<Report>> reports = readXls("test.xlsx");
        List<Report> outList = new ArrayList<>( );
        for (Map.Entry<Integer, List<Report>> report : reports.entrySet( )) {

            List<Report> listTmp = report.getValue( );
            if (listTmp.size( ) == 1) {
                for (Report report1 : listTmp) {
                    String sTmp = report1.getType( );
                    if (sTmp.equals(Visit.DEEP)) {
                        outList.add(new Report(report.getKey( ), report1.getSurname( ), report1.getAdress( ), report1.getType( )));
                    }
                }

            }


        }
        HSSFWorkbook workbook = new HSSFWorkbook( );
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Просто лист");

        // заполняем список какими-то данными


        // счетчик для строк
        int rowNum = 0;

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Id Пациента");
        row.createCell(1).setCellValue("ФИО");
        row.createCell(2).setCellValue("Адрес");
        row.createCell(3).setCellValue("Тип диспансеризации");

        // заполняем лист данными
        for (Report dataModel : outList) {
            createSheetHeader(sheet, ++rowNum, dataModel);
        }

        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File("OutFile.xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace( );
        }
        System.out.println("Excel файл успешно создан!");
        //

    }

//    public static List<Report> parseCSV(String[] columnMapping, String fileName) {
//        ColumnPositionMappingStrategy<Report> strategy = new ColumnPositionMappingStrategy<>( );
//        strategy.setType(Report.class);
//        strategy.setColumnMapping(columnMapping);
//        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
//            CsvToBean<Report> csvToBean = new CsvToBeanBuilder<Report>(csvReader)
//                    .withMappingStrategy(strategy)
//                    .build( );
//            List<Report> list = csvToBean.parse( );
//            return list;
//
//        } catch (IOException e) {
//            e.printStackTrace( );
//        }
//        return null;
//    }

    public static Map<Integer, List<Report>> readXls(String input) throws IOException {
        Integer tmp = 0;
        Map<Integer, List<Report>> mapReport = new HashMap<>( );
        String tmpStId = "";
        String tmpSurname = "";
        String tmpAdress = "";
        String tmpType = "";
        DataFormatter dataFormatter = new DataFormatter( );

        File myFile = new File(input);
        FileInputStream fis = new FileInputStream(myFile);
        // Finds the workbook instance for
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator( );
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext( )) {
            Row row = rowIterator.next( );
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator( );
            List<Report> list = new ArrayList<>( );
            while (cellIterator.hasNext( )) {
                Cell cell = cellIterator.next( );
                int index = cell.getColumnIndex( );
                if (index == 0) {
                    tmpStId = dataFormatter.formatCellValue(cell);
                    if (!tmpStId.equals("")) {
                        tmp = Integer.parseInt(tmpStId);
                    }
                } else if (index == 1) {
                    String tmpSname = cell.getStringCellValue( );
                    if (!tmpSname.equals("")) {
                        tmpSurname = tmpSname;
                    }
                } else if (index == 2) {
                    String tmpAds = cell.getStringCellValue( );
                    if (!tmpAds.equals("")) {
                        tmpAdress = tmpAds;
                    }
                } else {
                    String tmpTp = cell.getStringCellValue( );
                    if (!tmpTp.equals("")) {
                        tmpType = tmpTp;
                    }
                }

            }

            list.add(new Report(tmpSurname, tmpAdress, tmpType));
            if (mapReport.isEmpty( )) {
                mapReport.put(tmp, list);
            } else if (!mapReport.containsKey(tmp)) {
                mapReport.put(tmp, list);
            } else {
                List<Report> tmpLists = (mapReport.get(tmp));
                Report reportTmp = new Report(tmpSurname, tmpAdress, tmpType);
                tmpLists.add(reportTmp);
                mapReport.put(tmp, tmpLists);
            }

        }
        return mapReport;

    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Report dataModel) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(dataModel.getId( ));
        row.createCell(1).setCellValue(dataModel.getSurname( ));
        row.createCell(2).setCellValue(dataModel.getAdress( ));
        row.createCell(3).setCellValue(dataModel.getType( ));
    }

}
