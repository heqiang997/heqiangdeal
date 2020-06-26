package com.zhimeng.caiwuweb;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
//        ImportController importController = new ImportController();
//        //JsonData jsonData = importController.excelArea();
//        //System.out.println(jsonData);
//        FileInputStream fs= null;  //获取d://test.xls
//        try {
//            fs = new FileInputStream("C:\\Users\\Administrator\\Desktop\\差旅导入1.xls");
//            POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
//            HSSFWorkbook wb=new HSSFWorkbook(ps);
//            HSSFSheet sheet=wb.getSheetAt(1);  //获取到工作表，因为一个excel可能有多个工作表
//            HSSFRow row=sheet.getRow(0);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值
//            System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格
//
//            FileOutputStream out=new FileOutputStream("C:\\Users\\Administrator\\Desktop\\差旅导入1.xls");  //向d://test.xls中写数据
//            // row=sheet.createRow((short)(1)); //在现有行号后追加数据
//           // row.createCell(0).setCellValue("leilei"); //设置第一个（从0开始）单元格的数据
//           // row.createCell(1).setCellValue(24); //设置第二个（从0开始）单元格的数据
//            List<String> list = new ArrayList<>();
//            list.add("毕节市气象局");
//            list.add("七星关区气象局");
//            list.add("大方县气象局");
//
//            for (int i = 0; i < list.size();i++){
//                //row=sheet.createRow((short)(i+1)); //在现有行号后追加数据
//                row=sheet.createRow((short)(i+1)); //在现有行号后追加数据
//                row.createCell(0).setCellValue(list.get(i));
//            }
//
//            out.flush();
//            wb.write(out);
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            ArrayList<Object> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.forEach(entity->{
            System.out.println(entity);
        });

    }


    public static class s {
        private  int id;
        private  String name;
        private  boolean state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }
}
