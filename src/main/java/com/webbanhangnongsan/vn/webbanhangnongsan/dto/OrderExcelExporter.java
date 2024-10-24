package com.webbanhangnongsan.vn.webbanhangnongsan.dto;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Order;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderExcelExporter {

    private List<Order> orders;

    public OrderExcelExporter(List<Order> orders) {
        this.orders = orders;
    }

    public void export(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Orders");

        // Tạo tiêu đề
        writeHeaderRow(sheet);

        // Ghi dữ liệu đơn hàng
        writeDataRows(sheet);

        // Auto resize columns
        autoResizeColumns(sheet);

        // Ghi workbook ra response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void writeHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Order ID", "User", "Address", "Phone", "Amount", "Order Date", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(sheet.getWorkbook()));
        }
    }

    private void writeDataRows(Sheet sheet) {
        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(order.getOrderId());
            row.createCell(1).setCellValue(order.getUser().getName()); // Giả sử User có thuộc tính fullName
            row.createCell(2).setCellValue(order.getAddress());
            row.createCell(3).setCellValue(order.getPhone());
            row.createCell(4).setCellValue(order.getAmount());
            row.createCell(5).setCellValue(order.getOrderDate().toString());
            row.createCell(6).setCellValue(getStatusString(order.getStatus()));
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private void autoResizeColumns(Sheet sheet) {
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String getStatusString(int status) {
        switch (status) {
            case 1: return "Confirmed";
            case 2: return "Delivered";
            case 3: return "Cancelled";
            default: return "Pending";
        }
    }
}

