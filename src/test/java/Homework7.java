import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static org.assertj.core.api.Assertions.assertThat;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class Homework7 {
    @Test
    void fileParsing() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/TestFile.zip");

        //pdf
        ZipEntry zipPDF = zipFile.getEntry("������  - ����1.pdf");
        try (InputStream inputStreamPDF = zipFile.getInputStream(zipPDF)) {
            PDF parsed = new PDF(inputStreamPDF);
            assertThat(parsed.text).contains("�������� ���������� ��������");
        }

        //csv
        ZipEntry csvEntry = zipFile.getEntry("������  - ����1.csv");
        try (InputStream csvStream = zipFile.getInputStream(csvEntry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(csvStream));
            List<String[]> list = reader.readAll();
            assertThat(list)
                    .hasSize(4)
                    .contains(
                            new String[]{"�������", "�����","���������"},
                            new String[]{"������� ���� ��������"},
                            new String[]{"1", "�������� ���������� ��������"}
                    );
        }

//        xlsx
        ZipEntry zipEntryXLSX = zipFile.getEntry("������ .xlsx");
        try (InputStream inputStreamXLSX = zipFile.getInputStream(zipEntryXLSX)) {
            XLS parsed = new XLS(inputStreamXLSX);
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue())
                    .isEqualTo("�������");

        }

    }
}

