package guru.qa.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.tests.model.MenuJson;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsingFilesTest {
    ClassLoader cl = ParsingFilesTest.class.getClassLoader();


    @Test
    void zipCsvXlsxPdfParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("ZIP.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equalsIgnoreCase("csv.csv")) {
                    try (
                            InputStream csv = cl.getResourceAsStream(entry.getName());
                            CSVReader reader = new CSVReader(new InputStreamReader(csv))
                    ) {
                        List<String[]> content = reader.readAll();
                        assertThat(content.get(2)[1]).contains("Ivanov");
                    }
                } else if (entry.getName().equalsIgnoreCase("excel.xlsx")) {
                    try (InputStream resourceAsStream = cl.getResourceAsStream(entry.getName())) {
                        XLS content = new XLS(resourceAsStream);
                        assertThat(content.excel.getSheet("SheetOne").getRow(1).getCell(2).getNumericCellValue()).isEqualTo(2);
                    }
                } else if (entry.getName().equalsIgnoreCase("pdf.pdf")) {
                    try (InputStream resourceAsStream = cl.getResourceAsStream(entry.getName())) {
                        PDF content = new PDF(resourceAsStream);
                        assertThat(content.text).contains("3");
                    }
                }
            }
        }

    }

    @Test
    void jsonParseWithJacksonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        MenuJson value = mapper.readValue(new File("src/test/resources/JSON.json"), MenuJson.class);
        assertThat(value.menu.list.items.saveDoc.onclick).isEqualTo("save_doc");

    }
}