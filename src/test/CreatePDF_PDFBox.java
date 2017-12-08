package test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;

public class CreatePDF_PDFBox
{
	public static void main(String[] args)
			throws Exception
	{

		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDType0Font font = PDType0Font.load(document, new File("c:\\Windows\\Fonts\\ARIALUNI.TTF"));

		PDPageContentStream stream = new PDPageContentStream(document, page);

		stream.beginText();
		stream.setFont(font, 20);
		stream.setLeading(12 * 1.2);

		stream.moveTextPositionByAmount(50, 600);
		stream.drawString("PDFBox Unicode with Embedded TrueType Font");
		stream.newLine();
		stream.drawString("啊啊啊啊");
		stream.newLine();

		stream.drawString("Supports full Unicode text ☺");
		stream.newLine();

		stream.drawString("English русский язык Tiếng Việt");

		stream.endText();
		stream.close();

		document.save("example.pdf");
		document.close();
	}
}
