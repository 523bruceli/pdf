package test;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class CreatePDF_Velocity
{
	public static void crateHTML(File target, String templatePath)
	{
		try
		{
			Velocity.init();
			VelocityContext context = new VelocityContext();
			context.put("name", "Fly Chen");
			context.put("address", new String("한국어".getBytes("UTF-8"), "UTF-8"));
			context.put("date", new Date().toString());
			context.put("address1", new String("发生夫为恶".getBytes("UTF-8"), "UTF-8"));
			FileOutputStream fos = new FileOutputStream(target);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			Velocity.mergeTemplate(templatePath, "UTF-8", context, writer);
			//template.merge(context, writer);
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public CreatePDF_Velocity()
	{
		super();
	}

	public static void main(String[] args)
			throws Exception
	{
		String templatePath = "src/hello.vm";
		File file = new File("aaa.html");
		crateHTML(file, templatePath);
	}
}
