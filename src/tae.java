// Adding table in a pdf using java
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
  
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

  
public class Tae {
    public static void main(String args[]) throws Exception
    {
        String file = "addingTableToPDF.pdf";
  
        // Step-1 Creating a PdfDocument object
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
  
        // Step-2 Creating a Document object
        Document doc = new Document(pdfDoc);
  
        // Step-3 Creating a table
        Table table = new Table(2);
        ImageData data = ImageDataFactory.create("logo.png");
        Image img = new Image(data);

        // I am just adjusting
        img.scaleAbsolute(60f, 60f);
        img.setFixedPosition(100, 250);
  
        // Step-4 Adding cells to the table
        table.addCell(new Cell().add(new Paragraph("Name")));
    
        // Step-6 Adding Table to document
        doc.add(table);
        doc.add(img);
  
        // Step-7 Closing the document
        doc.close();
        System.out.println("Shit created successfully..");
    }
}