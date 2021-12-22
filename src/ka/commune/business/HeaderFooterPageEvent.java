package ka.commune.business;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.Cell;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    final Font font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf", BaseFont.IDENTITY_H,14);
    public void onStartPage(PdfWriter writer, Document document) {

        PdfPTable table=new PdfPTable(1);

        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        PdfPCell cell=new PdfPCell();
        Paragraph paragraph=new Paragraph();
        paragraph.setFont(font);
        paragraph.add("المملكة المغربية\n  جماعة كرامة");
        paragraph.setExtraParagraphSpace(6);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //ct.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,phrase, 750, 565, 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(""), 400, 30, 0);
    }

}
