package test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * HTML文件转换为PDF
 *
 * @author &lt;a href="http://www.micmiu.com"&gt;Michael Sun&lt;/a&gt;
 */
public class Demo4HTMLFile2PDF
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
			throws Exception
	{
		Demo4HTMLFile2PDF.parseHTML2PDFFile();

		//String pdfFile = "g:\\Source\\Test\\pdf\\aaa.pdf";
		//String htmlFile = "g:\\Source\\Test\\pdf\\aaa.html";
		//Demo4HTMLFile2PDF.parseHTML2PDFElement(pdfFile, new FileInputStream(htmlFile));
	}

	/**
	 * 用于HTML直接转换为PDF文件
	 *
	 * @throws Exception
	 */
	public static void parseHTML2PDFFile()
			throws Exception
	{

		String pdfFile = "g:\\Source\\git\\pdf\\aaa.pdf";

		Document document = new Document();
		PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
		pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
		document.open();

		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bf, 12);
		document.add(new Paragraph("HTML文件转PDF测试", font));
		document.add(Chunk.NEWLINE);

		// html文件
		InputStreamReader isr = new InputStreamReader(new FileInputStream("g:\\Source\\git\\pdf\\aaa.html"), "UTF-8");

		// 方法一：默认参数转换
		XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
		document.close();
	}

	public static void parseHTML2PDFElement(String pdfFile,
											InputStream htmlFileStream)
	{
		try
		{
			Document document = new Document(PageSize.A4);

			FileOutputStream outputStream = new FileOutputStream(pdfFile);
			PdfWriter pdfwriter = PdfWriter.getInstance(document, outputStream);
			// pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
			document.open();

			BaseFont bfCN = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			// 中文字体定义
			Font chFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLUE);
			Font secFont = new Font(bfCN, 12, Font.NORMAL, new BaseColor(0,
					204, 255));
			Font textFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLACK);

			int chNum = 1;
			Chapter chapter = new Chapter(new Paragraph(
					"HTML文件转PDF元素，便于追加其他内容", chFont), chNum++);

			Section section = chapter.addSection(new Paragraph(
					"/dev/null 2&gt;&amp;1 详解", secFont));

			section.setIndentation(10);
			section.setIndentationLeft(10);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(Chunk.NEWLINE);

			final List<Element> pdfeleList = new ArrayList<Element>();
			ElementHandler elemH = new ElementHandler()
			{

				public void add(final Writable w)
				{
					if (w instanceof WritableElement)
					{
						pdfeleList.addAll(((WritableElement) w).elements());
					}

				}
			};
			InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
			XMLWorkerHelper.getInstance().parseXHtml(elemH, isr);

			List<Element> list = new ArrayList<Element>();
			for (Element ele : pdfeleList)
			{
				if (ele instanceof LineSeparator
						|| ele instanceof WritableDirectElement)
				{
					continue;
				}
				list.add(ele);
			}
			section.addAll(list);

			section = chapter.addSection(new Paragraph("继续添加章节", secFont));

			section.setIndentation(10);
			section.setIndentationLeft(10);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Chunk("测试HTML转为PDF元素，方便追加其他内容", textFont));

			document.add(chapter);
			document.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}