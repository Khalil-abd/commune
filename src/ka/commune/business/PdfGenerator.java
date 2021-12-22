package ka.commune.business;

import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.scenario.effect.ImageData;
import ka.commune.entity.*;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.*;
import javax.swing.border.Border;
import java.awt.print.PrinterJob;
import java.util.List;

public class PdfGenerator {

	SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");

	Font font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14);
	Font fontList= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,12);
	Font titleFont= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,16,0,new BaseColor(25, 42, 86));
	Font nomFont= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,12);
	Font textFont= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14);
	
	@SuppressWarnings("deprecation")
	public boolean genererInvitation(Sujetsession sujetSession, Commission commission,String chemin)
	{
		if(commission.getMembreconseils().size()==0)
			return false;
		try 
		{
			
			Document document = new Document(PageSize.A4);
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+ ("/" + sujetSession.getDesignationsujetsession().getDesignation() +
					"_" + sujetSession.getMois() + "_" + sujetSession.getAnnee() + "_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
					+ ".pdf").replace(":", "-")));
			document.open();
			getMinistrePage(sujetSession,document);
			getObjetsPage(document,sujetSession);
			ColumnText ct;
			int i=0; //
			for(Membreconseil mc: commission.getMembreconseils())
			{
				if(mc.getMandat().getIdMandat() != App.getMandat().getIdMandat())
					continue;
				document.newPage();
				ct= new ColumnText(pw.getDirectContent());
				ct.setAlignment(2);
				ct.setSimpleColumn(36, 800, 569, 36);
				ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

				Paragraph paragraph=new Paragraph();
				paragraph.setFont(font);
				paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
				paragraph.setSpacingAfter(20);
				paragraph.setExtraParagraphSpace(2);
				ct.addElement(paragraph);
				
				PdfPTable table=new PdfPTable(1);
				table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				table.setWidthPercentage(100);
				PdfPCell cell;
				Paragraph para=new Paragraph("دعوة لحضور أشغال "+sujetSession.getSession().getDesignationSession(),titleFont);
				cell= new PdfPCell(para);
				cell.setPaddingTop(10);
				cell.setPaddingBottom(10);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(cell);
				ct.addElement(table);
				
				Paragraph paragraph2=new Paragraph();
				paragraph2.setFont(nomFont);
				paragraph2.add("                يتشرف رئيس المجلس الجماعي دعوة السيد:");
				paragraph2.add(new Phrase(" "+mc.getNomArabe()+" "+mc.getPrenomArabe(), titleFont));
				paragraph2.setSpacingAfter(5);
				paragraph2.setSpacingBefore(20);
				paragraph2.setExtraParagraphSpace(2);
				ct.addElement(paragraph2);
				
				Paragraph paragraph3=new Paragraph();
				paragraph3.setFont(nomFont);
				paragraph3.setSpacingAfter(20);
				paragraph3.setSpacingBefore(5);
				paragraph3.setExtraParagraphSpace(10);
				paragraph3.add("                الصفة داخل المجلس : ");
				paragraph3.add(new Phrase(" "+mc.getFonction()+ "\n", titleFont));
				paragraph3.add("                عـــنوانه : ");
				paragraph3.add(new Phrase(" "+mc.getAdresse()+" \n", titleFont));
				paragraph3.add("                للحضور بمقر الجماعة يوم :    ");
				paragraph3.add(new Phrase(getDayName(sujetSession.getDate())+" "+getArabicDate(sujetSession.getDate())+" ", titleFont));
				paragraph3.add("       على الساعة   :    ");
				paragraph3.add(new Phrase(timeFormater(sujetSession.getHeure())+"  ", titleFont));
				paragraph3.add("\n                لأجل :   ");
				paragraph3.add(new Phrase(sujetSession.getDesignationsujetsession().getDesignation(), titleFont));
				if(sujetSession.getSession().getDesignationSession().contains("دورة"))
					paragraph3.add(new Phrase(" لشهر "+sujetSession.getMois()+" "+sujetSession.getAnnee(),titleFont));
				paragraph3.add("\n                لمناقشة النقط الواردة بجدول الأعمال المرفق   ");
				ct.addElement(paragraph3);
				
				Paragraph paragraph4=new Paragraph();
				paragraph4.setFont(nomFont);
				paragraph4.setAlignment(2);
				paragraph4.add("                حرر بكرامة في :  ");
				paragraph4.cloneShallow(false);
				paragraph4.add(new Phrase(".........................\n"));
				paragraph4.add("رئيس المجلس الجماعي");
				paragraph4.setSpacingAfter(40);
				paragraph4.setSpacingBefore(20);
				paragraph4.setExtraParagraphSpace(2);
				ct.addElement(paragraph4);
				ct.go();

				Paragraph par4=new Paragraph();
				par4.setFont(nomFont);
				par4.setAlignment(1);
				par4.add("...................................................................................");
				par4.cloneShallow(false);
				par4.setSpacingAfter(40);
				par4.setSpacingBefore(20);
				par4.setExtraParagraphSpace(2);
				ct.addElement(par4);
				ct.go();

				// Reçu

				Paragraph parag=new Paragraph();
				parag.setFont(font);
				parag.add("                المملكة المغربية\n                 جماعة كرامة");
				parag.setSpacingAfter(20);
				parag.setExtraParagraphSpace(2);
				ct.addElement(parag);

				PdfPTable table1=new PdfPTable(1);
				table1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				table1.setWidthPercentage(100);
				PdfPCell cell1;
				Paragraph para1=new Paragraph("إشهاد بالتوصل",titleFont);
				cell1= new PdfPCell(para1);
				cell1.setPaddingTop(10);
				cell1.setPaddingBottom(10);
				cell1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table1.addCell(cell1);
				ct.addElement(table1);

				Paragraph parag2=new Paragraph();
				parag2.setFont(nomFont);
				parag2.add("                أنا الموقع أسفله السيد(ة) :");
				parag2.add(new Phrase(" "+mc.getNomArabe()+" "+mc.getPrenomArabe(), titleFont));
				parag2.setSpacingAfter(5);
				parag2.setSpacingBefore(20);
				parag2.setExtraParagraphSpace(2);
				ct.addElement(parag2);

				Paragraph parag3=new Paragraph();
				parag3.setFont(nomFont);
				parag3.setSpacingAfter(20);
				parag3.setSpacingBefore(5);
				parag3.setExtraParagraphSpace(10);
				parag3.add("                الصفة داخل المجلس : ");
				parag3.add(new Phrase(" "+mc.getFonction()+ "\n", titleFont));
				parag3.add("                أشهد بأني توصلت يوم :  "+"\n");
				parag3.add(new Phrase("                بالدعوة المتعلقة ب: "));
				parag3.add(new Phrase(sujetSession.getDesignationsujetsession()+"\n", titleFont));
				parag3.add("                المزمع عقدها يوم :    ");
				parag3.add(new Phrase(getDayName(sujetSession.getDate())+" "+getArabicDate(sujetSession.getDate())+" ", titleFont));
				parag3.add("       على الساعة   :    ");
				parag3.add(new Phrase(timeFormater(sujetSession.getHeure())+"  ", titleFont));
				ct.addElement(parag3);

				Paragraph parag4=new Paragraph();
				parag4.setFont(nomFont);
				parag4.setAlignment(1);
				parag4.add("                التوقيع :  ");
				parag4.cloneShallow(false);
				parag4.setSpacingAfter(40);
				parag4.setSpacingBefore(20);
				parag4.setExtraParagraphSpace(2);
				ct.addElement(parag4);
				ct.go();

			}
			document.close();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean downloadEtatActivites(List<Operation> operations , String chemin,String categorie)
	{
		Document document = new Document(PageSize.A4);
		try{
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+ ("/EtatDétaillé-" +categorie+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
					+ ".pdf").replace(":", "-")));
		}catch (Exception e){
			return false;
		}
		document.setMargins(0,0,20,20);
		document.open();

		PdfPTable communeIdentification=new PdfPTable(12);
		communeIdentification.setSpacingBefore(20);
		communeIdentification.setHorizontalAlignment(Element.ALIGN_LEFT);
		communeIdentification.setWidthPercentage(100);
		PdfPCell ce=new PdfPCell();
		ce.setColspan(4);
		Paragraph pa=new Paragraph();
		Font font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		pa.setFont(font);
		pa.setAlignment(1);
		pa.add("Service d'action social de Gourrama");
		pa.setExtraParagraphSpace(6);
		ce.addElement(pa);
		ce.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		ce.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(ce);

		PdfPCell c2=new PdfPCell();
		c2.setColspan(4);
		c2.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(c2);

		PdfPCell c=new PdfPCell();
		c.setColspan(4);
		Paragraph p=new Paragraph();
		p.setFont(font);
		p.setAlignment(1);

		String imageFile = App.class.getResource("/ka/commune/view/resources/img/logoLaHulpe.png").toString();
		try{
			Image img = Image.getInstance(imageFile);
			img.setWidthPercentage(80);
			c.addElement(img);
		}catch(Exception e){e.printStackTrace();}

		//p.add("La Commune de La Hulpe");
		//p.setExtraParagraphSpace(6);
		//c.addElement(p);
		c.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(c);
		try {
			document.add(communeIdentification);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(30);
		titleTable.setWidthPercentage(40);
		PdfPCell titleCell=new PdfPCell();
		Paragraph titleParagraph=new Paragraph();
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,15,Font.BOLD);
		font.setColor(new BaseColor(0,65,17));
		titleParagraph.setFont(font);
		titleParagraph.setAlignment(1);
		titleParagraph.add("Le programme C.I.C");
		titleParagraph.setExtraParagraphSpace(6);
		titleCell.setPaddingTop(10);
		titleCell.setPaddingBottom(10);
		titleCell.setPaddingRight(10);
		titleCell.setPaddingLeft(10);
		titleCell.addElement(titleParagraph);
		titleTable.addCell(titleCell);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable title=new PdfPTable(1);
		title.setSpacingBefore(30);
		title.setWidthPercentage(70);
		PdfPCell tcell=new PdfPCell();
		Paragraph tpara=new Paragraph();
		tpara.setFont(font);
		tpara.setAlignment(1);
		tpara.add("Etat détaillé des activités");
		tpara.setExtraParagraphSpace(6);
		tcell.setPaddingTop(10);
		tcell.setPaddingBottom(10);
		tcell.setPaddingRight(10);
		tcell.setPaddingLeft(10);
		tcell.addElement(tpara);
		title.addCell(tcell);
		try {
			document.add(title);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable objetsTable=new PdfPTable(12);
		objetsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		objetsTable.setWidthPercentage(80);
		objetsTable.setSpacingBefore(40);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14);
		PdfPCell cell3=new PdfPCell();
		cell3.setBorder(Rectangle.NO_BORDER);
		cell3.setColspan(3);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell3.setPaddingTop(5);
		cell3.setPaddingBottom(5);
		Paragraph para3=new Paragraph();
		para3.setFont(font);
		para3.add("Catégorie : ");
		cell3.addElement(para3);
		objetsTable.addCell(cell3);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		PdfPCell cell=new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(9);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		Paragraph para=new Paragraph();
		para.setFont(font);
		if(categorie!=null)
			para.add(categorie+".");
		else
			para.add("");
		cell.addElement(para);
		objetsTable.addCell(cell);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14);
		PdfPCell cell1=new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.setColspan(3);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell1.setPaddingTop(5);
		cell1.setPaddingBottom(5);
		Paragraph para1=new Paragraph();
		para1.setFont(font);
		para1.add("Dossier N° : ");
		cell1.addElement(para1);
		objetsTable.addCell(cell1);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		PdfPCell dossier=new PdfPCell();
		dossier.setBorder(Rectangle.NO_BORDER);
		dossier.setColspan(9);
		dossier.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		dossier.setPaddingTop(5);
		dossier.setPaddingBottom(5);
		Paragraph par=new Paragraph();
		par.setFont(font);
		par.add(operations.get(0).getNumeroDossier()+".");
		dossier.addElement(par);
		objetsTable.addCell(dossier);

		try {
			document.add(objetsTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable objetsT=new PdfPTable(20);
		objetsT.setHorizontalAlignment(Element.ALIGN_CENTER);
		objetsT.setWidthPercentage(90);
		objetsT.setSpacingBefore(40);

		objetsT.addCell(createHeaderCell("N°",1));
		objetsT.addCell(createHeaderCell("Année",2));
		objetsT.addCell(createHeaderCell("Date d'opération",4));
		objetsT.addCell(createHeaderCell("Désignation",10));
		objetsT.addCell(createHeaderCell("Montant",3));
		double total=0;
		boolean bottom=false;
		for(int i=0;i<operations.size();i++)
		{
			System.out.println(operations.get(i).getDesignation());
			total+=operations.get(i).getMontantRecu();
			if((i+1)==operations.size())
				bottom=true;
			objetsT.addCell(createCell((i+1)+"",1,bottom));
			objetsT.addCell(createCell(operations.get(i).getAnnee()+"",2,bottom));
			objetsT.addCell(createCell(dateFormater(operations.get(i).getDateOperation()),4,bottom));
			objetsT.addCell(createCell(operations.get(i).getDesignation(),10,bottom));
			objetsT.addCell(createCell(operations.get(i).getMontantRecu()+" DH",3,bottom));
		}

		try {
			document.add(objetsT);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable totalT=new PdfPTable(12);
		totalT.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalT.setWidthPercentage(60);
		totalT.setSpacingBefore(40);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14);
		PdfPCell totalC=new PdfPCell();
		totalC.setBorder(Rectangle.NO_BORDER);
		totalC.setColspan(2);
		totalC.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		totalC.setPaddingTop(5);
		totalC.setPaddingBottom(5);
		Paragraph totalP=new Paragraph();
		totalP.setFont(font);
		totalP.add("Total : ");
		totalC.addElement(totalP);
		totalT.addCell(totalC);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		PdfPCell tc=new PdfPCell();
		tc.setBorder(Rectangle.NO_BORDER);
		tc.setColspan(10);
		tc.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		tc.setPaddingTop(5);
		tc.setPaddingBottom(5);
		Paragraph tp=new Paragraph();
		tp.setFont(font);
		tp.add(total+" DH");
		tc.addElement(tp);
		totalT.addCell(tc);

		try {
			document.add(totalT);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();

		return true;
	}

	private PdfPCell createCell(String text,int size,boolean bottomBorder)
	{
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,12);
		PdfPCell cell=new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);

			if(bottomBorder)
				cell.setBorder(2|8|4);
			else
				cell.setBorder(4|8);

		cell.setColspan(size);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setPaddingTop(6);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		Paragraph para2=new Paragraph();
		para2.setFont(font);
		para2.add(text);
		cell.addElement(para2);
		return cell;
	}

	private PdfPCell createHeaderCell(String text,int size)
	{
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,12,Font.BOLD);
		PdfPCell cell=new PdfPCell();
		cell.setColspan(size);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setPaddingTop(6);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		Paragraph para2=new Paragraph();
		para2.setFont(font);
		para2.add(text);
		cell.addElement(para2);
		return cell;
	}

	public boolean downloadActivityPresentation(Operation operation, String chemin) {

		Document document = new Document(PageSize.A4);
		try{
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+ ("/" +operation.getDesignation()+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
					+ ".pdf").replace(":", "-")));
		}catch (Exception e){
			return false;
		}
		document.setMargins(0,0,20,20);
		document.open();

		PdfPTable communeIdentification=new PdfPTable(12);
		communeIdentification.setSpacingBefore(20);
		communeIdentification.setHorizontalAlignment(Element.ALIGN_LEFT);
		communeIdentification.setWidthPercentage(100);
		PdfPCell ce=new PdfPCell();
		ce.setColspan(4);
		Paragraph pa=new Paragraph();
		Font font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		pa.setFont(font);
		pa.setAlignment(1);
		pa.add("Service d'action social de Gourrama");
		pa.setExtraParagraphSpace(6);
		ce.addElement(pa);
		ce.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		ce.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(ce);

		PdfPCell c2=new PdfPCell();
		c2.setColspan(4);
		c2.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(c2);

		PdfPCell c=new PdfPCell();
		c.setColspan(4);
		Paragraph p=new Paragraph();
		p.setFont(font);
		p.setAlignment(1);

		String imageFile = App.class.getResource("/ka/commune/view/resources/img/logoLaHulpe.png").toString();
		try{
			Image img = Image.getInstance(imageFile);
			img.setWidthPercentage(80);
			c.addElement(img);
		}catch(Exception e){e.printStackTrace();}


		//p.add("La Commune de La Hulpe");
		//p.setExtraParagraphSpace(6);
		//c.addElement(p);
		c.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(c);
		try {
			document.add(communeIdentification);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(30);
		titleTable.setWidthPercentage(40);
		PdfPCell titleCell=new PdfPCell();
		Paragraph titleParagraph=new Paragraph();
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		font.setColor(new BaseColor(0,65,17));
		titleParagraph.setFont(font);
		titleParagraph.setAlignment(1);
		titleParagraph.add("Fiche de Présentation d'une activité");
		titleParagraph.setExtraParagraphSpace(6);
		titleCell.setPaddingTop(10);
		titleCell.setPaddingBottom(10);
		titleCell.setPaddingRight(10);
		titleCell.setPaddingLeft(10);
		titleCell.addElement(titleParagraph);
		titleTable.addCell(titleCell);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable objetsTable=new PdfPTable(12);
		objetsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
		objetsTable.setWidthPercentage(85);
		objetsTable.setSpacingBefore(50);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell1=new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.setColspan(6);
		cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell1.setPaddingTop(5);
		cell1.setPaddingBottom(5);
		Paragraph para1=new Paragraph();
		para1.setFont(font);
		para1.add("Dossier N° : "+operation.getNumeroDossier());
		cell1.addElement(para1);
		objetsTable.addCell(cell1);

		PdfPCell cell2=new PdfPCell();
		cell2.setBorder(Rectangle.NO_BORDER);
		cell2.setColspan(6);
		cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell2.setPaddingTop(5);
		cell2.setPaddingBottom(5);
		Paragraph para2=new Paragraph();
		para2.setFont(font);
		para2.add("Année : "+operation.getAnnee());
		cell2.addElement(para2);
		objetsTable.addCell(cell2);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell3=new PdfPCell();
		cell3.setBorder(Rectangle.NO_BORDER);
		cell3.setColspan(6);
		cell3.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell3.setPaddingTop(5);
		cell3.setPaddingBottom(5);
		Paragraph para3=new Paragraph();
		para3.setFont(font);
		para3.add("Catégorie : ");
		cell3.addElement(para3);
		objetsTable.addCell(cell3);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell=new PdfPCell();
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(6);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		Paragraph para=new Paragraph();
		para.setFont(font);
		if(operation.getCategorieBean()!=null)
			para.add(operation.getCategorieBean().getDesignation()+".");
		else
			para.add("");
		cell.addElement(para);
		objetsTable.addCell(cell);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell4=new PdfPCell();
		cell4.setBorder(Rectangle.NO_BORDER);
		cell4.setColspan(6);
		cell4.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell4.setPaddingTop(5);
		cell4.setPaddingBottom(5);
		Paragraph para4=new Paragraph();
		para4.setFont(font);
		para4.add("Résultat attendu : ");
		cell4.addElement(para4);
		objetsTable.addCell(cell4);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell5=new PdfPCell();
		cell5.setBorder(Rectangle.NO_BORDER);
		cell5.setColspan(6);
		cell5.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell5.setPaddingTop(5);
		cell5.setPaddingBottom(5);
		Paragraph para5=new Paragraph();
		para5.setFont(font);
		if(operation.getResultatBean()!=null)
			para5.add(operation.getResultatBean()+".");
		else
			para5.add("");
		cell5.addElement(para5);
		objetsTable.addCell(cell5);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell6=new PdfPCell();
		cell6.setBorder(Rectangle.NO_BORDER);
		cell6.setColspan(6);
		cell6.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell6.setPaddingTop(5);
		cell6.setPaddingBottom(5);
		Paragraph para6=new Paragraph();
		para6.setFont(font);
		para6.add("Activité type prévu dans le plan opérationnel : ");
		cell6.addElement(para6);
		objetsTable.addCell(cell6);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell7=new PdfPCell();
		cell7.setBorder(Rectangle.NO_BORDER);
		cell7.setColspan(6);
		cell7.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell7.setPaddingTop(5);
		cell7.setPaddingBottom(5);
		Paragraph para7=new Paragraph();
		para7.setFont(font);
		if(operation.getActivitetype()!=null)
			para7.add(operation.getActivitetype()+".");
		else
			para7.add("");
		cell7.addElement(para7);
		objetsTable.addCell(cell7);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell8=new PdfPCell();
		cell8.setBorder(Rectangle.NO_BORDER);
		cell8.setColspan(6);
		cell8.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell8.setPaddingTop(5);
		cell8.setPaddingBottom(5);
		Paragraph para8=new Paragraph();
		para8.setFont(font);
		para8.add("Réalisation : ");
		cell8.addElement(para8);
		objetsTable.addCell(cell8);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell9=new PdfPCell();
		cell9.setBorder(Rectangle.NO_BORDER);
		cell9.setColspan(6);
		cell9.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell9.setPaddingTop(5);
		cell9.setPaddingBottom(5);
		Paragraph para9=new Paragraph();
		para9.setFont(font);
		if(operation.getRealisationBean()!=null)
			para9.add(operation.getRealisationBean()+".");
		else
			para9.add("");
		cell9.addElement(para9);
		objetsTable.addCell(cell9);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell14=new PdfPCell();
		cell14.setBorder(Rectangle.NO_BORDER);
		cell14.setColspan(6);
		cell14.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell14.setPaddingTop(5);
		cell14.setPaddingBottom(5);
		Paragraph para14=new Paragraph();
		para14.setFont(font);
		para14.add("Date de réalisation : ");
		cell14.addElement(para14);
		objetsTable.addCell(cell14);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell15=new PdfPCell();
		cell15.setBorder(Rectangle.NO_BORDER);
		cell15.setColspan(6);
		cell15.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell15.setPaddingTop(5);
		cell15.setPaddingBottom(5);
		Paragraph para15=new Paragraph();
		para15.setFont(font);
		if(operation.getDateRealisation()!=null)
			para15.add(getFrenchDate(operation.getDateRealisation())+".");
		else
			para15.add("");
		cell15.addElement(para15);
		objetsTable.addCell(cell15);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell10=new PdfPCell();
		cell10.setBorder(Rectangle.NO_BORDER);
		cell10.setColspan(6);
		cell10.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell10.setPaddingTop(5);
		cell10.setPaddingBottom(5);
		Paragraph para10=new Paragraph();
		para10.setFont(font);
		para10.add("Cansistance : ");
		cell10.addElement(para10);
		objetsTable.addCell(cell10);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell11=new PdfPCell();
		cell11.setBorder(Rectangle.NO_BORDER);
		cell11.setColspan(6);
		cell11.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell11.setPaddingTop(5);
		cell11.setPaddingBottom(5);
		Paragraph para11=new Paragraph();
		para11.setFont(font);
		for(Cansistance can:operation.getCansistances())
		{
			para11.add(" - "+can.getDesignation()+".\n");
		}
		cell11.addElement(para11);
		objetsTable.addCell(cell11);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell12=new PdfPCell();
		cell12.setBorder(Rectangle.NO_BORDER);
		cell12.setColspan(6);
		cell12.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell12.setPaddingTop(5);
		cell12.setPaddingBottom(5);
		Paragraph para12=new Paragraph();
		para12.setFont(font);
		para12.add("Partenaires : ");
		cell12.addElement(para12);
		objetsTable.addCell(cell12);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell13=new PdfPCell();
		cell13.setBorder(Rectangle.NO_BORDER);
		cell13.setColspan(6);
		cell13.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell13.setPaddingTop(5);
		cell13.setPaddingBottom(5);
		Paragraph para13=new Paragraph();
		para13.setFont(font);
		for(PartenaireOperation po:operation.getPartenaireoperations())
		{
			para13.add(" - "+po.getDesignation()+".\n");
		}
		cell13.addElement(para13);
		objetsTable.addCell(cell13);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.BOLD);
		PdfPCell cell16=new PdfPCell();
		cell16.setBorder(Rectangle.NO_BORDER);
		cell16.setColspan(6);
		cell16.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell16.setPaddingTop(5);
		cell16.setPaddingBottom(5);
		Paragraph para16=new Paragraph();
		para16.setFont(font);
		para16.add("Participation du CIC : ");
		cell16.addElement(para16);
		objetsTable.addCell(cell16);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		PdfPCell cell17=new PdfPCell();
		cell17.setBorder(Rectangle.NO_BORDER);
		cell17.setColspan(6);
		cell17.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		cell17.setPaddingTop(5);
		cell17.setPaddingBottom(5);
		Paragraph para17=new Paragraph();
		para17.setFont(font);
		para17.add(operation.getMontantRecu()+" DH.");
		cell17.addElement(para17);
		objetsTable.addCell(cell17);

		try {
			document.add(objetsTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		document.close();
		return true;
	}

	private void getMinistrePage(Sujetsession sujetSession, Document document) {
		PdfPTable communeIdentification=new PdfPTable(12);
		communeIdentification.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		communeIdentification.setHorizontalAlignment(Element.ALIGN_LEFT);
		communeIdentification.setWidthPercentage(100);
		PdfPCell cell=new PdfPCell();
		cell.setColspan(3);
		Paragraph paragraph=new Paragraph();
		Font font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,14,Font.BOLD);
		paragraph.setFont(font);
		paragraph.setAlignment(1);
		paragraph.add("المملكة المغربيـــــــة\n  وزارة الداخلية\nإقليم ميدلـــــــــت\nدائرة الريـش\nقيادة كرامـــــــــة\nجماعة كرامـــــــــــــة");
		paragraph.setExtraParagraphSpace(6);
		cell.addElement(paragraph);
		cell.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(cell);
		PdfPCell cell2=new PdfPCell();
		cell2.setColspan(9);
		cell2.setBorder(Rectangle.NO_BORDER);
		communeIdentification.addCell(cell2);
		try {
			document.add(communeIdentification);
		} catch (DocumentException e) {
			e.printStackTrace();
		}


		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(20);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTable.setWidthPercentage(40);
		PdfPCell titleCell=new PdfPCell();
		Paragraph titleParagraph=new Paragraph();
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.UNDERLINE|Font.BOLD);
		titleParagraph.setFont(font);
		titleParagraph.setAlignment(1);
		if(sujetSession.getSession().getDesignationSession().contains("ستثنا"))
			titleParagraph.add("جدول الأعمال الخاص ب"+sujetSession.getSession().getDesignationSession()+" ليوم :"); // +sujetSession.getMois()+" "+sujetSession.getAnnee()
		else
			titleParagraph.add("جدول الأعمال الخاص ب"+sujetSession.getSession().getDesignationSession()+" لشهر "+sujetSession.getMois()+" "+sujetSession.getAnnee()); //
		titleParagraph.setExtraParagraphSpace(6);
		titleCell.addElement(titleParagraph);
		titleCell.setBorder(Rectangle.NO_BORDER);
		titleTable.addCell(titleCell);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable loiTable=new PdfPTable(12);
		loiTable.setSpacingBefore(10);
		loiTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		loiTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		loiTable.setWidthPercentage(80);
		PdfPCell loiCell=new PdfPCell();
		loiCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED|PdfPCell.ALIGN_LEFT);
		loiCell.setColspan(12);
		Paragraph loiParagraph=new Paragraph();
		loiParagraph.setExtraParagraphSpace(8);
		loiParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);
		loiParagraph.setFont(font);
		loiParagraph.add("    طبقا لمقتضيات الظهير الشريف رقم 85-15-1 بتاريخ 20 رمضان 1436 ( 7 يوليوز 2015 ) بتنفيذ القانون التنظيمي رقم 113-14 المتعلق بالجماعات، سيتم عقد "+sujetSession.getSession().getDesignationSession()+" لشهر "+sujetSession.getMois()+" "+sujetSession.getAnnee()+
				" بمقر جماعة كرامة وذلك يوم "
		+getDayName(sujetSession.getDate())+" "+getArabicDate(sujetSession.getDate())+" على الساعة "+timeFormater(sujetSession.getHeure())+" في جلسة واحدة لدراسة النقط التالية:");
		loiCell.addElement(loiParagraph);
		loiCell.setBorder(Rectangle.NO_BORDER);
		loiTable.addCell(loiCell);
		try {
			document.add(loiTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable objetsTable=new PdfPTable(12);
		objetsTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		objetsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
		objetsTable.setWidthPercentage(75);

		font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);


		int i=0;
		for(Objetreunion o:sujetSession.getObjetreunions())
		{

			PdfPCell numCell=new PdfPCell();
			numCell.setBorder(Rectangle.NO_BORDER);
			numCell.setColspan(1);
			numCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			numCell.setPaddingTop(3);
			numCell.setPaddingBottom(3);
			Paragraph numPara=new Paragraph();
			numPara.setFont(font);
			numPara.add((++i)+")");
			numCell.addElement(numPara);
			objetsTable.addCell(numCell);
			PdfPCell objetsCell=new PdfPCell();
			objetsCell.setColspan(11);
			objetsCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED|PdfPCell.ALIGN_LEFT);
			Paragraph objetsParagraph=new Paragraph();
			objetsParagraph.setExtraParagraphSpace(8);
			objetsParagraph.setAlignment(Paragraph.ALIGN_LEFT);
			objetsParagraph.setFont(font);
			objetsParagraph.add(o.getDesignationObjetReunion()+".");
			objetsCell.addElement(objetsParagraph);
			objetsCell.setPaddingTop(3);
			objetsCell.setPaddingBottom(3);
			objetsCell.setBorder(Rectangle.NO_BORDER);
			objetsTable.addCell(objetsCell);

		}

		try {
			document.add(objetsTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

        PdfPTable signTable=new PdfPTable(12);
        signTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        signTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        signTable.setSpacingBefore(60);
        signTable.setKeepTogether(true);
        signTable.setWidthPercentage(100);
        PdfPCell signCell2=new PdfPCell();
        signCell2.setColspan(6);
        signCell2.setBorder(Rectangle.NO_BORDER);
        signTable.addCell(signCell2);
        PdfPCell signCell=new PdfPCell();
        signCell.setColspan(6);
        Paragraph signParagraph=new Paragraph();
        signParagraph.setFont(font);
        signParagraph.setAlignment(1);
        signParagraph.add("حرر بكرامة في:...................\n");
        signParagraph.add("رئيس المجلس الجماعــــــــــــي\n\n");
        signParagraph.add("إمضاء:");
        signParagraph.setExtraParagraphSpace(6);
        signCell.addElement(signParagraph);
        signCell.setBorder(Rectangle.NO_BORDER);
        signTable.addCell(signCell);

        try {
            document.add(signTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
	}

	private void getObjetsPage(Document document,Sujetsession sujetSession)
	{
		document.newPage();

			PdfPTable titleTable=new PdfPTable(1);
			titleTable.setSpacingBefore(20);
			titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			titleTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			titleTable.setWidthPercentage(40);
			PdfPCell titleCell=new PdfPCell();
			Paragraph titleParagraph=new Paragraph();
			font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13,Font.UNDERLINE|Font.BOLD);
			titleParagraph.setFont(font);
			titleParagraph.setAlignment(1);
			titleParagraph.add("جدول الأعمال");
			titleParagraph.setExtraParagraphSpace(6);
			titleCell.addElement(titleParagraph);
			titleCell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(titleCell);
			try {
				document.add(titleTable);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			PdfPTable objetsTable=new PdfPTable(12);
			objetsTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			objetsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
			objetsTable.setWidthPercentage(75);

			font= FontFactory.getFont("/ka/commune/view/resources/font/arial.ttf",BaseFont.IDENTITY_H,13);


			int i=0;
			for(Objetreunion o:sujetSession.getObjetreunions())
			{

				PdfPCell numCell=new PdfPCell();
				numCell.setBorder(Rectangle.NO_BORDER);
				numCell.setColspan(1);
				numCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				Paragraph numPara=new Paragraph();
				numPara.setFont(font);
				numPara.add((++i)+")");
				numCell.addElement(numPara);
				numCell.setFixedHeight(20);
				objetsTable.addCell(numCell);
				PdfPCell objetsCell=new PdfPCell();
				objetsCell.setColspan(11);
				objetsCell.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED|PdfPCell.ALIGN_LEFT);
				Paragraph objetsParagraph=new Paragraph();
				objetsParagraph.setExtraParagraphSpace(8);
				objetsParagraph.setAlignment(Paragraph.ALIGN_LEFT);
				objetsParagraph.setFont(font);
				objetsParagraph.add(o.getDesignationObjetReunion()+".");
				objetsCell.addElement(objetsParagraph);
				objetsCell.setFixedHeight(20);
				objetsCell.setBorder(Rectangle.NO_BORDER);
				objetsTable.addCell(objetsCell);

			}

			try {
				document.add(objetsTable);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

	}

	public boolean downloadListMembres(List<Membreconseil> membres, String chemin)
	{
		try
		{
			Document document = new Document(PageSize.A4.rotate(), 5, 20, 25, 25);
			String nom;
			if(copie==0)
				nom="لائحة أعضاء المجلس.pdf";
			else
				nom="لائحة أعضاء المجلس("+copie+").pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			fillListMembres(document,membres,pw);

		} catch (Exception e) {
			// TODO: handle exception
			copie++;
			downloadListMembres(membres,chemin);
			e.printStackTrace();
		}
		copie=0;
		return true;
	}

	private void fillListMembres(Document document,List<Membreconseil> membres,PdfWriter pw)
	{
		document.open();
		PdfPTable header=new PdfPTable(1);

		header.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		PdfPCell c=new PdfPCell();
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("المملكة المغربية\n  جماعة كرامة");
		paragraph.setExtraParagraphSpace(6);
		c.addElement(paragraph);
		c.setBorder(Rectangle.NO_BORDER);
		header.addCell(c);
		try {
			document.add(header);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(15);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph para=new Paragraph("لائحة أعضاء المجلس الجماعي",font);
		cell= new PdfPCell(para);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(24);
		table.setSpacingAfter(20);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(270));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.addCell(getCell(1,"ر,ت"));
		table.addCell(getCell(3,"إسم بالعربية"));
		table.addCell(getCell(4,"العنوان"));
		table.addCell(getCell(2,"ب.ت.و"));
		table.addCell(getCell(4,"الإنتماء السياسي"));
		table.addCell(getCell(4,"عنوان الدائرة"));
		table.addCell(getCell(3,"رقم الهاتف"));
		table.addCell(getCell(3,"الصفة داخل المجلس"));
		int i=0;
		for(Membreconseil m : membres)
		{
			table.addCell(getCellList(1,(++i)+""));
			table.addCell(getCellList(3,m.getNomArabe()+" "+m.getPrenomArabe()));
			table.addCell(getCellList(4,m.getAdresse()));
			table.addCell(getCellList(2,m.getCin()));
			table.addCell(getCellList(4,m.getPartiepolitique().getDesignationPartiePolitique()));
			table.addCell(getCellList(4,m.getCirconscription().getDesignationCirconscription()));
			table.addCell(getCellList(3,m.getTelephone()));
			table.addCell(getCellList(3,m.getFonction().getDesignation()));
		}

		table.completeRow();

		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();
	}
	
	public boolean printMembreInformation(List<Membreconseil> listMembre, String chemin)
	{
		try 
		{
			Document document = new Document(PageSize.A4);
			String nom;
			if(listMembre.size()==1)
				nom=listMembre.get(0).getNom()+"_"+listMembre.get(0).getPrenom()+".pdf";
			else
				nom="Membres_du_conseils.pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			pw.setPageEvent(new HeaderFooterPageEvent());
			document.open();
			ColumnText ct;
			for(Membreconseil mc: listMembre)
			{

				PdfPTable titleTable=new PdfPTable(1);
				titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				titleTable.setWidthPercentage(50);
				PdfPCell cell;
				Paragraph para=new Paragraph("بطاقة معلومات",font);
				cell= new PdfPCell(para);
				cell.setPaddingTop(10);
				cell.setPaddingBottom(10);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				titleTable.addCell(cell);
				titleTable.setSpacingAfter(20);
				document.add(titleTable);
				
				PdfPTable table = new PdfPTable(20);
				table.setSpacingAfter(20);
				table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			    table.setTotalWidth(Utilities.millimetersToPoints(200));
			    table.setLockedWidth(true);
			    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			    table.addCell(getCell(8,"الإسم العائلي"));
			    table.addCell(getCell(6,mc.getNomArabe()));
			    table.addCell(getCell(6,mc.getNom()));
			    table.addCell(getCell(8,"الإسم الشخصي"));
			    table.addCell(getCell(6,mc.getPrenomArabe()));
			    table.addCell(getCell(6,mc.getPrenom()));
			    table.addCell(getCell(8,"تاريخ الإزدياد"));
			    table.addCell(getCell(12,format1.format(mc.getDateNaissance())));
			    table.addCell(getCell(8,"محل الإزدياد"));
			    table.addCell(getCell(12,mc.getLieuNaissance()));
			    table.addCell(getCell(8,"المهنة"));
			    table.addCell(getCell(12,mc.getProfession()));
			    table.addCell(getCell(8,"البطاقة الوطنية"));
			    table.addCell(getCell(12,mc.getCin()));
			    table.addCell(getCell(8,"المستوى الثقافي"));
			    table.addCell(getCell(12,mc.getNiveauScolaire().getDesignation()));
			    table.addCell(getCell(8,"الإنتماء السياسي"));
			    table.addCell(getCell(12,mc.getPartiepolitique().getDesignationPartiePolitique()));
			    table.addCell(getCell(8,"رقم الدائرة الإنتخابية"));
			    table.addCell(getCell(12,String.valueOf(mc.getCirconscription().getNumero())));
			    table.addCell(getCell(8,"عنوان الدائرة"));
			    table.addCell(getCell(12,mc.getCirconscription().getDesignationCirconscription()));
			    table.addCell(getCell(8,"الصفة داخل المجلس"));
			    table.addCell(getCell(12,mc.getFonction().getDesignation()));
			    table.addCell(getCell(8,"العنوان"));
			    table.addCell(getCell(12,mc.getAdresse()));
			    table.addCell(getCell(8,"رقم الهاتف"));
			    table.addCell(getCell(12,mc.getTelephone()));
			    table.completeRow();
			    document.add(table);

				PdfPTable lasttable=new PdfPTable(1);
				lasttable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				PdfPCell lastcell=new PdfPCell();
				Paragraph paragraph=new Paragraph();
				paragraph.setAlignment(2);
				paragraph.setFont(font);
				paragraph.add("رئيس المجلس الجماعي");
				paragraph.setExtraParagraphSpace(6);
				lastcell.addElement(paragraph);
				lastcell.setBorder(Rectangle.NO_BORDER);
				lasttable.addCell(lastcell);
				try {
					document.add(lasttable);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
			document.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean inviteAssociation(List<Association> associations, ReunionAssociation reunionAssociation ,String chemin)
	{
		if(associations.size()==0)
			return false;
		try
		{

			Document document = new Document(PageSize.A4);
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+ ("/" + reunionAssociation.getSujet() +
					"_" + getArabicDate(reunionAssociation.getDate()).replace(" ","_")+"_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
					+ ".pdf").replace(":", "-")));
			document.open();
			ColumnText ct=null;
			int i=0; //
			for(Association a : associations)
			{
				if(i%2==0)
				{
					if(i!=0)
						document.newPage();
					ct= new ColumnText(pw.getDirectContent());
					ct.setAlignment(2);
					ct.setSimpleColumn(36, 800, 569, 36);
					ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				}
				else {
					Paragraph paragraph5=new Paragraph();
					paragraph5.setFont(nomFont);
					paragraph5.setAlignment(1);
					paragraph5.add("------------------------------------------------------------------------------------------------------------  ");
					paragraph5.cloneShallow(false);
					paragraph5.setSpacingAfter(10);
					ct.addElement(paragraph5);
					ct.go();
				}

				Paragraph paragraph=new Paragraph();
				paragraph.setFont(font);
				paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
				paragraph.setSpacingAfter(30);
				ct.addElement(paragraph);

				PdfPTable table=new PdfPTable(1);
				table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				table.setWidthPercentage(100);
				PdfPCell cell;
				Paragraph para=new Paragraph("دعوة لحضور اجتماع ",titleFont);
				cell= new PdfPCell(para);
				cell.setPaddingTop(10);
				cell.setPaddingBottom(10);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(cell);
				ct.addElement(table);

				Paragraph paragraph2=new Paragraph();
				paragraph2.setFont(nomFont);
				paragraph2.add("                         يتشرف المكتب الإجتماعي لجماعة كرامة:");
				paragraph2.setSpacingAfter(5);
				paragraph2.setSpacingBefore(20);
				paragraph2.setExtraParagraphSpace(2);
				ct.addElement(paragraph2);

				Paragraph paragraph3=new Paragraph();
				paragraph3.setFont(nomFont);
				paragraph3.setSpacingAfter(20);
				paragraph3.setSpacingBefore(5);
				paragraph3.setExtraParagraphSpace(10);
				paragraph3.add("                أن يدعو السيد : ");
				paragraph3.add(new Phrase(" "+a.getRepresantant()+ "\n", titleFont));
				paragraph3.add("                اسم الجمعية : ");
				paragraph3.add(new Phrase(" "+a.getNom()+" \n", titleFont));
				paragraph3.add("                للحضور بمقر الجماعة يوم :    ");
				paragraph3.add(new Phrase(getDayName(reunionAssociation.getDate())+" "+getArabicDate(reunionAssociation.getDate())+" \n", titleFont));
				paragraph3.add("                على الساعة   :    ");
				paragraph3.add(new Phrase(timeFormater(reunionAssociation.getTime())+"  ", titleFont));
				paragraph3.add("\n                لأجل :   ");
				paragraph3.add(new Phrase(reunionAssociation.getSujet(), titleFont));
				ct.addElement(paragraph3);

				Paragraph paragraph4=new Paragraph();
				paragraph4.setFont(nomFont);
				paragraph4.setAlignment(1);
				paragraph4.add("التوقيع  ");
				paragraph4.cloneShallow(false);
				paragraph4.setSpacingAfter(70);
				paragraph4.setSpacingBefore(3);
				ct.addElement(paragraph4);
				ct.go();


				i++;
			}
			document.close();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean inviteCommissionEC(List<CommissionEC> associations, ReunionCommissionec reunionAssociation ,String chemin)
	{
		if(associations.size()==0)
			return false;
		try
		{

			Document document = new Document(PageSize.A4);
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+ ("/" + reunionAssociation.getSujet() +
					"_" + getArabicDate(reunionAssociation.getDate()).replace(" ","_")+"_" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
					+ ".pdf").replace(":", "-")));
			document.open();
			ColumnText ct=null;
			int i=0; //
			for(CommissionEC a : associations)
			{
				if(i%2==0)
				{
					if(i!=0)
						document.newPage();
					ct= new ColumnText(pw.getDirectContent());
					ct.setAlignment(2);
					ct.setSimpleColumn(36, 800, 569, 36);
					ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				}
				else {
					Paragraph paragraph5=new Paragraph();
					paragraph5.setFont(nomFont);
					paragraph5.setAlignment(1);
					paragraph5.add("------------------------------------------------------------------------------------------------------------  ");
					paragraph5.cloneShallow(false);
					paragraph5.setSpacingAfter(10);
					ct.addElement(paragraph5);
					ct.go();
				}


				Paragraph paragraph=new Paragraph();
				paragraph.setFont(font);
				paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
				paragraph.setSpacingAfter(30);
				ct.addElement(paragraph);


				PdfPTable table=new PdfPTable(1);
				table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				table.setWidthPercentage(100);
				PdfPCell cell;
				Paragraph para=new Paragraph("دعوة لحضور اجتماع ",titleFont);
				cell= new PdfPCell(para);
				cell.setPaddingTop(10);
				cell.setPaddingBottom(10);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(cell);
				ct.addElement(table);

				Paragraph paragraph2=new Paragraph();
				paragraph2.setFont(nomFont);
				paragraph2.add("                         يتشرف المكتب الإجتماعي لجماعة كرامة:");
				paragraph2.setSpacingAfter(5);
				paragraph2.setSpacingBefore(20);
				paragraph2.setExtraParagraphSpace(2);
				ct.addElement(paragraph2);

				Paragraph paragraph3=new Paragraph();
				paragraph3.setFont(nomFont);
				paragraph3.setSpacingAfter(20);
				paragraph3.setSpacingBefore(5);
				paragraph3.setExtraParagraphSpace(10);
				paragraph3.add("                أن يدعو السيد : ");
				paragraph3.add(new Phrase(" "+a.getRepresantant()+ "\n", titleFont));
				paragraph3.add("                اسم المؤسسة : ");
				paragraph3.add(new Phrase(" "+a.getNom()+" \n", titleFont));
				paragraph3.add("                للحضور بمقر الجماعة يوم :    ");
				paragraph3.add(new Phrase(getDayName(reunionAssociation.getDate())+" "+getArabicDate(reunionAssociation.getDate())+" \n", titleFont));
				paragraph3.add("                على الساعة   :    ");
				paragraph3.add(new Phrase(timeFormater(reunionAssociation.getTime())+"  ", titleFont));
				paragraph3.add("\n                لأجل :   ");
				paragraph3.add(new Phrase(reunionAssociation.getSujet(), titleFont));
				ct.addElement(paragraph3);

				Paragraph paragraph4=new Paragraph();
				paragraph4.setFont(nomFont);
				paragraph4.setAlignment(1);
				paragraph4.add("التوقيع  ");
				paragraph4.cloneShallow(false);
				paragraph4.setSpacingAfter(70);
				paragraph4.setSpacingBefore(3);
				ct.addElement(paragraph4);
				ct.go();


				i++;

			}
			document.close();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean downloadAssociationInformation(List<Association> associations, String chemin)
	{
		try
		{
			Document document = new Document(PageSize.A4);
			String nom;
			if(associations.size()==1)
				nom=associations.get(0).getNom()+".pdf";
			else
				nom="معلومات الجمعيات.pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			fillAssociationInfo(document,associations,pw);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public boolean printAssociationInformation(List<Association> associations)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4);
			String nom;
			if(associations.size()==1)
				nom=associations.get(0).getNom()+".pdf";
			else
				nom="معلومات الجمعيات.pdf";
			PdfWriter pw=PdfWriter.getInstance(document, baos);
			fillAssociationInfo(document,associations,pw);

			byte[] byteStream = baos.toByteArray();
			PrinterJob job = PrinterJob.getPrinterJob();
			PDDocument pdDocument = null;
			DocFlavor docType = DocFlavor.INPUT_STREAM.AUTOSENSE;
			try {
				if(job.printDialog()) {
					PrintService services =job.getPrintService();
					DocPrintJob printJob = services.createPrintJob();
					Doc documentToBePrinted = new SimpleDoc(new ByteArrayInputStream(byteStream), docType, null);
					printJob.print(documentToBePrinted, null);
				}

			} catch (Exception pe) {
				pe.printStackTrace();

			} finally {
				if (pdDocument != null) {
					pdDocument.close();
				}
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}


	private void fillAssociationInfo(Document document,List<Association> associations,PdfWriter pw)
	{
		document.open();
		ColumnText ct;
		for(Association a:associations)
		{
			document.newPage();
			ct= new ColumnText(pw.getDirectContent());
			ct.setAlignment(2);
			ct.setSimpleColumn(36, 800, 569, 36);
			ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			Paragraph paragraph=new Paragraph();
			paragraph.setFont(font);
			paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
			paragraph.setSpacingAfter(20);
			paragraph.setExtraParagraphSpace(2);
			ct.addElement(paragraph);

			PdfPTable titleTable=new PdfPTable(1);
			titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			titleTable.setWidthPercentage(50);
			PdfPCell cell;
			Paragraph para=new Paragraph("بطاقة معلومات",font);
			cell= new PdfPCell(para);
			cell.setPaddingTop(10);
			cell.setPaddingBottom(10);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			titleTable.addCell(cell);
			titleTable.setSpacingAfter(20);
			ct.addElement(titleTable);

			PdfPTable table = new PdfPTable(20);
			table.setSpacingAfter(20);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			table.setTotalWidth(Utilities.millimetersToPoints(200));
			table.setLockedWidth(true);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.addCell(getCell(8,"إسم الجمعية"));
			table.addCell(getCell(12,a.getNom()));
			table.addCell(getCell(8,"عنوانها"));
			table.addCell(getCell(12,a.getAdresse()));
			table.addCell(getCell(8,"اسم الممثل"));
			table.addCell(getCell(12,a.getRepresantant()));
			table.addCell(getCell(8,"رقم الهاتف"));
			table.addCell(getCell(12,a.getPhone()));
			table.addCell(getCell(8,"المجال الرئيسي للجمعية"));
			table.addCell(getCell(12,String.valueOf(a.getDomaine())));
			table.addCell(getCell(8,"تاريخ التأسيس"));
			table.addCell(getCell(12,format1.format(a.getDateFondation())));
			table.addCell(getCell(8,"تاريخ التجديد"));
			table.addCell(getCell(12,format1.format(a.getDateRenouvelement())));
			table.addCell(getCell(8,"تاريخ انتهاء المكتب"));
			table.addCell(getCell(12,format1.format(a.getDateExpiration())));

			table.completeRow();
			ct.addElement(table);

			Paragraph paragraph4=new Paragraph();
			paragraph4.setFont(nomFont);
			paragraph4.setAlignment(2);
			paragraph4.add("رئيس المجلس الجماعي");
			paragraph4.setSpacingAfter(40);
			paragraph4.setSpacingBefore(20);
			paragraph4.setExtraParagraphSpace(2);
			ct.addElement(paragraph4);

			try {
				ct.go();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		document.close();
	}

	private static int copie=0;
	public boolean downloadListAssociation(List<Association> associations, String chemin,String domaine)
	{
		try
		{
			Document document = new Document(PageSize.A4.rotate(), 5, 20, 25, 25);
			String nom;
			String extend;
			if(!domaine.equals(""))
				extend="-"+domaine;
			else
				extend="";
			if(copie==0)
			{
				nom="لائحة الجمعيات"+extend+".pdf";
			}
			else
				nom="لائحة الجمعيات"+extend+"("+copie+").pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			pw.setPageEmpty(false);
			fillListAssociation(document,associations,pw);

		} catch (Exception e) {
			// TODO: handle exception
			copie++;
			downloadListAssociation(associations,chemin,domaine);
			e.printStackTrace();
		}
		copie=0;
		return true;
	}

	private void fillListAssociation(Document document,List<Association> associations,PdfWriter pw) {
		document.open();
		PdfPTable header=new PdfPTable(1);

		header.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		PdfPCell c=new PdfPCell();
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("المملكة المغربية\n  جماعة كرامة");
		paragraph.setExtraParagraphSpace(6);
		c.addElement(paragraph);
		c.setBorder(Rectangle.NO_BORDER);
		header.addCell(c);
		try {
			document.add(header);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(15);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph para=new Paragraph("لائحة الجمعيات",font);
		cell= new PdfPCell(para);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(18);
		table.setKeepTogether(false);
		table.setSpacingAfter(20);
		table.setSplitLate(false);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(270));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.addCell(getCell(1,"ر,ت"));
		table.addCell(getCell(6,"إسم الجمعية"));
		table.addCell(getCell(3,"عنوانها"));
		table.addCell(getCell(3,"اسم الممثل"));
		table.addCell(getCell(2,"رقم الهاتف"));
		table.addCell(getCell(3,"ملاحظات"));
		for(Association a : associations)
		{
			table.addCell(getCellList(1,String.valueOf(a.getNumero())));
			table.addCell(getCellList(6,a.getNom()));
			table.addCell(getCellList(3,a.getAdresse()));
			table.addCell(getCellList(3,a.getRepresantant()));
			table.addCell(getCellList(2,a.getPhone()));
			table.addCell(getCellList(3,""));
		}

		table.completeRow();
		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}



		document.close();
	}

	public boolean downloadListCommissionEC(List<CommissionEC> associations, String chemin)
	{
		try
		{
			Document document = new Document(PageSize.A4.rotate(), 5, 20, 25, 25);
			String nom;
			if(copie==0)
				nom="لائحة أعضاء لجنة تكافئ الفرص.pdf";
			else
				nom="لائحة أعضاء لجنة تكافئ الفرص("+copie+").pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			fillListCommissionEC(document,associations,pw);

		} catch (Exception e) {
			// TODO: handle exception
			copie++;
			downloadListCommissionEC(associations,chemin);
			e.printStackTrace();
		}
		copie=0;
		return true;
	}

	private void fillListCommissionEC(Document document,List<CommissionEC> associations,PdfWriter pw)
	{
		document.open();
		PdfPTable header=new PdfPTable(1);

		header.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		PdfPCell c=new PdfPCell();
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("المملكة المغربية\n  جماعة كرامة");
		paragraph.setExtraParagraphSpace(6);
		c.addElement(paragraph);
		c.setBorder(Rectangle.NO_BORDER);
		header.addCell(c);
		try {
			document.add(header);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setSpacingBefore(15);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph para=new Paragraph("لائحة أعضاء لجنة تكافئ الفرص",font);
		cell= new PdfPCell(para);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		try {
			document.add(titleTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(18);
		table.setSpacingAfter(20);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(270));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.addCell(getCell(1,"ر,ت"));
		table.addCell(getCell(6,"إسم المؤسسة"));
		table.addCell(getCell(2,"عنوانها"));
		table.addCell(getCell(2,"اسم الممثل"));
		table.addCell(getCell(3,"رقم الهاتف"));
		table.addCell(getCell(4,"ملاحظات"));
		for(CommissionEC a : associations)
		{
			table.addCell(getCellList(1,String.valueOf(a.getNumero())));
			table.addCell(getCellList(2,a.getNom()));
			table.addCell(getCellList(2,a.getAdresse()));
			table.addCell(getCellList(2,a.getRepresantant()));
			table.addCell(getCellList(3,a.getPhone()));
			table.addCell(getCellList(2,""));
		}

		table.completeRow();

		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.close();
	}



	// Commission EC

	public boolean downloadCommissionECInformation(List<CommissionEC> associations, String chemin)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			Document document = new Document(PageSize.A4);
			String nom;
			if(associations.size()==1)
				nom=associations.get(0).getNom()+".pdf";
			else
				nom="معلومات الجمعيات.pdf";
			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			//PdfWriter pw=PdfWriter.getInstance(document, ps);
			document.open();
			fillCommissionEC(document,associations,pw);
			document.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private void fillCommissionEC(Document document, List<CommissionEC> associations,PdfWriter pw)
	{

		ColumnText ct;
		for(CommissionEC a:associations)
		{
			document.newPage();
			ct= new ColumnText(pw.getDirectContent());
			ct.setAlignment(2);
			ct.setSimpleColumn(36, 800, 569, 36);
			ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			Paragraph paragraph=new Paragraph();
			paragraph.setFont(font);
			paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
			paragraph.setSpacingAfter(20);
			paragraph.setExtraParagraphSpace(2);
			ct.addElement(paragraph);

			PdfPTable titleTable=new PdfPTable(1);
			titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			titleTable.setWidthPercentage(50);
			PdfPCell cell;
			Paragraph para=new Paragraph("بطاقة معلومات",font);
			cell= new PdfPCell(para);
			cell.setPaddingTop(10);
			cell.setPaddingBottom(10);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			titleTable.addCell(cell);
			titleTable.setSpacingAfter(20);
			ct.addElement(titleTable);

			PdfPTable table = new PdfPTable(20);
			table.setSpacingAfter(20);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			table.setTotalWidth(Utilities.millimetersToPoints(200));
			table.setLockedWidth(true);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.addCell(getCell(8,"إسم المؤسسة"));
			table.addCell(getCell(12,a.getNom()));
			table.addCell(getCell(8,"عنوانها"));
			table.addCell(getCell(12,a.getAdresse()));
			table.addCell(getCell(8,"اسم الممثل"));
			table.addCell(getCell(12,a.getRepresantant()));
			table.addCell(getCell(8,"رقم الهاتف"));
			table.addCell(getCell(12,a.getPhone()));
			table.addCell(getCell(8,"المجال الرئيسي للمؤسسة"));
			table.addCell(getCell(12,String.valueOf(a.getDomaine())));
			table.addCell(getCell(8,"تاريخ التأسيس"));
			table.addCell(getCell(12,format1.format(a.getDateFondation())));
			table.addCell(getCell(8,"تاريخ التجديد"));
			table.addCell(getCell(12,format1.format(a.getDateRenouvelement())));
			table.addCell(getCell(8,"تاريخ انتهاء المكتب"));
			table.addCell(getCell(12,format1.format(a.getDateExpiration())));

			table.completeRow();
			ct.addElement(table);

			Paragraph paragraph4=new Paragraph();
			paragraph4.setFont(nomFont);
			paragraph4.setAlignment(2);
			paragraph4.add("رئيس المجلس الجماعي");
			paragraph4.setSpacingAfter(40);
			paragraph4.setSpacingBefore(20);
			paragraph4.setExtraParagraphSpace(2);
			ct.addElement(paragraph4);

			try {
				ct.go();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}

	// Pret Materiel
	public boolean downloadCartePretMateriel(PretMateriel p, String chemin)
	{
		try
		{
			Document document = new Document(PageSize.A4);
			String nom;
			nom="إعارة معدات لفائدة "+p.getBeneficiare()+" "+getArabicDate(p.getDateDemande())+".pdf";

			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			document.open();
			document.top(10);
			fillDemandePretMateriel(document,p,pw);
			fillEngagementPretMateriel(document,p,pw);
			fillCartePretMateriel(document,p,pw);
			document.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private void fillCartePretMateriel(Document document,PretMateriel p,PdfWriter pw)
	{
		ColumnText ct;

		document.newPage();
		ct= new ColumnText(pw.getDirectContent());
		ct.setAlignment(2);
		ct.setSimpleColumn(36, 800, 569, 36);
		ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
		paragraph.setSpacingAfter(20);
		paragraph.setExtraParagraphSpace(2);
		ct.addElement(paragraph);
		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph para=new Paragraph("بطاقة تسليم معدات\n\nرقم : "+p.getNumero()+"     سنة : "+p.getYear(),font);
		cell= new PdfPCell(para);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		ct.addElement(titleTable);

		Paragraph paragraph3=new Paragraph();
		paragraph3.setFont(nomFont);
		paragraph3.setSpacingAfter(20);
		paragraph3.setSpacingBefore(5);
		paragraph3.setExtraParagraphSpace(10);
		paragraph3.add("                المستفيد : ");
		paragraph3.add(new Phrase(" "+p.getBeneficiare()+ "\n", titleFont));
		paragraph3.add("                الممثلة من طرف : ");
		paragraph3.add(new Phrase(" "+p.getRepresentant()+"    رقم الهاتف : "+p.getPhone()+"\n", titleFont));
		paragraph3.add("                تاريخ إيداع الطلب :    ");
		paragraph3.add(new Phrase(getArabicDate(p.getDateDemande())+" \n", titleFont));
		paragraph3.add("                طبيعة النشاط  :    ");
		paragraph3.add(new Phrase(p.getActivite()+"\n", titleFont));
		paragraph3.add("                تاريخ تسليم المعدات : ");
		paragraph3.add(new Phrase(getArabicDate(p.getDateDebut())+"\n", titleFont));
		paragraph3.add("                تاريخ إرجاع المعدات : ");
		paragraph3.add(new Phrase(getArabicDate(p.getDateFin())+"\n", titleFont));
		ct.addElement(paragraph3);

		Paragraph paragraph4=new Paragraph();
		paragraph4.setFont(nomFont);
		paragraph4.setAlignment(1);
		paragraph4.add("توقيع صاحب الطلب                                                                  توقيع الجماعة ");
		paragraph4.cloneShallow(false);
		paragraph4.setSpacingAfter(70);
		paragraph4.setSpacingBefore(3);
		ct.addElement(paragraph4);

		try {
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void fillEngagementPretMateriel(Document document,PretMateriel p,PdfWriter pw)
	{
		ColumnText ct;

		document.newPage();
		ct= new ColumnText(pw.getDirectContent());
		ct.setAlignment(2);
		ct.setSimpleColumn(36, 800, 569, 36);
		ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
		paragraph.setSpacingAfter(20);
		paragraph.setExtraParagraphSpace(2);
		ct.addElement(paragraph);


		Paragraph parag2=new Paragraph();
		parag2.setFont(nomFont);
		parag2.add("                أنا الموقع أسفله السيد(ة) :");
		parag2.add(new Phrase(" "+p.getRepresentant()+"\n", titleFont));
		parag2.setSpacingAfter(5);
		parag2.setSpacingBefore(20);
		parag2.setExtraParagraphSpace(2);
		ct.addElement(parag2);

		Paragraph parag3=new Paragraph();
		parag3.setFont(nomFont);
		parag3.setSpacingAfter(20);
		parag3.setSpacingBefore(5);
		parag3.setExtraParagraphSpace(10);
		parag3.add("                ممثل مؤسسة : ");
		parag3.add(new Phrase(" "+p.getBeneficiare()+ "\n", titleFont));
		parag3.add("                أشهد أنني توصلت من الجماعة بالمعدات التالية  :\n" );
		ct.addElement(parag3);

		PdfPTable table = new PdfPTable(20);
		table.setSpacingAfter(20);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(100));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		table.addCell(getCell(10,"إسم المعدة"));
		table.setHorizontalAlignment(1);
		table.addCell(getCell(10,"الكمية"));
		for(PretedMateriel pm: p.getPretedmateriels())
		{
			table.addCell(getCell(10,pm.getMateriel().getDesignation()));
			table.addCell(getCell(10,String.valueOf(pm.getQuantite())));
		}

		table.completeRow();
		ct.addElement(table);


		Paragraph parag4=new Paragraph();
		parag4.setFont(nomFont);
		parag4.add( "                وذلك للقيام بالنشاط المذكور في البطاقة رقم :  "+p.getYear()+" /"+p.getNumero()+" طيه.");
		parag4.cloneShallow(false);
		parag4.setSpacingAfter(40);
		parag4.setSpacingBefore(20);
		parag4.setExtraParagraphSpace(2);
		ct.addElement(parag4);

		Paragraph paragraph4=new Paragraph();
		paragraph4.setFont(nomFont);
		paragraph4.setAlignment(1);
		paragraph4.add("التوقيع");
		paragraph4.cloneShallow(false);
		paragraph4.setSpacingAfter(70);
		paragraph4.setSpacingBefore(3);
		ct.addElement(paragraph4);


		try {
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	private void fillDemandePretMateriel(Document document,PretMateriel p,PdfWriter pw)
	{
		ColumnText ct;

		document.newPage();
		ct= new ColumnText(pw.getDirectContent());
		ct.setAlignment(2);
		ct.setSimpleColumn(36, 800, 569, 36);
		ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

		Paragraph paragraph=new Paragraph();
		paragraph.setAlignment(2);
		paragraph.setFont(font);
		paragraph.add("كرامة في : "+ getArabicDate(p.getDateDemande()));

		Paragraph para=new Paragraph();
		para.setFont(font);
		para.add("   المؤسسة : "+p.getBeneficiare()+"\n\n");
		para.add("   ممثلها  : "+p.getRepresentant());

		PdfPTable table = new PdfPTable(3);
		table.setSpacingAfter(30);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(200));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		PdfPCell leftCell=new PdfPCell(paragraph);
		leftCell.setBorder(0);
		leftCell.setHorizontalAlignment(2);

		PdfPCell rightCell=new PdfPCell(para);
		rightCell.setBorder(0);
		rightCell.setHorizontalAlignment(0);
		PdfPCell centerCell=new PdfPCell(new Paragraph(""));
		centerCell.setBorder(0);
		centerCell.setHorizontalAlignment(0);

		table.setHorizontalAlignment(1);
		table.addCell(rightCell);
		table.addCell(centerCell);
		table.addCell(leftCell);
		ct.addElement(table);


		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph par=new Paragraph("طلب معدات ",font);
		cell= new PdfPCell(par);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		ct.addElement(titleTable);

		Paragraph parag2=new Paragraph();
		parag2.setFont(textFont);
		parag2.add("                سلام تام بوجود مولانا الإمام ");
		parag2.setSpacingAfter(10);
		parag2.setSpacingBefore(20);
		parag2.setExtraParagraphSpace(2);
		ct.addElement(parag2);

		Paragraph paragraph3=new Paragraph();
		paragraph3.setFont(textFont);
		paragraph3.add("                وبعد، يشرفني أن ألتمس منكم العمل على تزويد مؤسستنا بالمعدات التالية : ");
		paragraph3.cloneShallow(false);
		paragraph3.setSpacingAfter(20);
		paragraph3.setSpacingBefore(3);
		ct.addElement(paragraph3);

		PdfPTable tableMateriel = new PdfPTable(20);
		tableMateriel.setSpacingAfter(10);
		tableMateriel.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		tableMateriel.setTotalWidth(Utilities.millimetersToPoints(100));
		tableMateriel.setLockedWidth(true);
		tableMateriel.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableMateriel.addCell(getCell(10,"إسم المعدة"));
		table.setHorizontalAlignment(1);
		tableMateriel.addCell(getCell(10,"الكمية"));
		for(PretedMateriel pm: p.getPretedmateriels())
		{
			tableMateriel.addCell(getCell(10,pm.getMateriel().getDesignation()));
			tableMateriel.addCell(getCell(10,String.valueOf(pm.getQuantite())));
		}

		tableMateriel.completeRow();
		ct.addElement(tableMateriel);

		Paragraph paragrap=new Paragraph();
		paragrap.setFont(textFont);
		paragrap.add("                وذلك من أجل القيام ب : ");
		paragrap.add(new Phrase(" "+p.getActivite()+"\n\n", titleFont));
		paragrap.add("                هذا وأصرح بما يلي : ");
		paragrap.cloneShallow(false);
		paragrap.setSpacingAfter(10);
		paragrap.setSpacingBefore(3);
		ct.addElement(paragrap);

		Paragraph paragraph5=new Paragraph();
		paragraph5.setFont(textFont);
		paragraph5.add("                - اخبار السلطات المحلية "+"\n\n");
		paragraph5.add("                - المحافظة على المعدات "+"\n\n");
		paragraph5.add("                - ارجاعها في التاريخ الذي تم تحديده "+"\n\n");
		paragraph5.add("                - الالتزام بتعويض أي معدة تعرضت للإتلاف "+"\n\n");
		paragraph5.cloneShallow(false);
		paragraph5.setSpacingAfter(30);
		paragraph5.setSpacingBefore(3);
		ct.addElement(paragraph5);


		Paragraph paragraph4=new Paragraph();
		paragraph4.setFont(textFont);
		paragraph4.setAlignment(1);
		paragraph4.add("التوقيع");
		paragraph4.cloneShallow(false);
		paragraph4.setSpacingAfter(70);
		paragraph4.setSpacingBefore(3);
		ct.addElement(paragraph4);

		try {
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	// Pret Salle
	public boolean downloadCartePretSalle(PretSalle p, String chemin)
	{
		try
		{
			Document document = new Document(PageSize.A4);
			String nom;
			nom="إعارة "+p.getSalle().getDesignation()+" لفائدة "+p.getBeneficiare()+" "+getArabicDate(p.getDateDemande())+".pdf";

			PdfWriter pw=PdfWriter.getInstance(document, new FileOutputStream(chemin+"/"+nom));
			document.open();
			document.top(10);
			fillDemandePretSalle(document,p,pw);
			fillCartePretSalle(document,p,pw);
			document.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	private void fillCartePretSalle(Document document, PretSalle p, PdfWriter pw) {
		ColumnText ct;

		document.newPage();
		ct= new ColumnText(pw.getDirectContent());
		ct.setAlignment(2);
		ct.setSimpleColumn(36, 800, 569, 36);
		ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		Paragraph paragraph=new Paragraph();
		paragraph.setFont(font);
		paragraph.add("                المملكة المغربية\n                 جماعة كرامة");
		paragraph.setSpacingAfter(20);
		paragraph.setExtraParagraphSpace(2);
		ct.addElement(paragraph);
		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph para=new Paragraph("بطاقة خاصة بالستغلال القاعة : "+p.getSalle().getDesignation()+"\n\nرقم : "+p.getNumero()+"     سنة : "+p.getYear(),font);
		cell= new PdfPCell(para);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		ct.addElement(titleTable);

		Paragraph paragraph3=new Paragraph();
		paragraph3.setFont(nomFont);
		paragraph3.setSpacingAfter(20);
		paragraph3.setSpacingBefore(5);
		paragraph3.setExtraParagraphSpace(10);
		paragraph3.add("                صاحب الطلب : ");
		paragraph3.add(new Phrase(" "+p.getRepresentant()+"    رقم الهاتف : "+p.getPhone()+"\n", titleFont));
		paragraph3.add("                المستفيد : ");
		paragraph3.add(new Phrase(" "+p.getBeneficiare()+ "\n", titleFont));
		paragraph3.add("                تاريخ إيداع الطلب :    ");
		paragraph3.add(new Phrase(getArabicDate(p.getDateDemande())+" \n", titleFont));
		paragraph3.add("                تاريخ استغلال القاعة : ");
		String text;
		if(p.getDateDebut().equals(p.getDateFin()))
			text=getArabicDate(p.getDateDebut())+"  ابتداء من الساعة : "+timeFormater(p.getHeureDebut())+"  إلى الساعة  :  "+timeFormater(p.getHeureFin())+" .\n";
		else
			text=" من يوم : "+getArabicDate(p.getDateDebut())+"  إلى يوم  :"+getArabicDate(p.getDateFin())+"\n                "+
					"ابتداء من الساعة : "+timeFormater(p.getHeureDebut())+"  إلى الساعة  :  "+timeFormater(p.getHeureFin())+" .\n";
		paragraph3.add(text);
		paragraph3.add("                طبيعة النشاط  :    ");
		paragraph3.add(new Phrase(p.getActivite()+"\n", titleFont));
		ct.addElement(paragraph3);

		Paragraph paragraph4=new Paragraph();
		paragraph4.setFont(nomFont);
		paragraph4.setAlignment(1);
		paragraph4.add("توقيع صاحب الطلب                                                                  توقيع الجماعة ");
		paragraph4.cloneShallow(false);
		paragraph4.setSpacingAfter(70);
		paragraph4.setSpacingBefore(3);
		ct.addElement(paragraph4);

		try {
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}


	private void fillDemandePretSalle(Document document,PretSalle p,PdfWriter pw)
	{
		ColumnText ct;

		document.newPage();
		ct= new ColumnText(pw.getDirectContent());
		ct.setAlignment(2);
		ct.setSimpleColumn(36, 800, 569, 36);
		ct.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

		Paragraph paragraph=new Paragraph();
		paragraph.setAlignment(2);
		paragraph.setFont(font);
		paragraph.add("كرامة في : "+ getArabicDate(p.getDateDemande()));

		Paragraph para=new Paragraph();
		para.setFont(font);
		para.add("   المؤسسة : "+p.getBeneficiare()+"\n\n");
		para.add("   ممثلها  : "+p.getRepresentant());

		PdfPTable table = new PdfPTable(3);
		table.setSpacingAfter(30);
		table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		table.setTotalWidth(Utilities.millimetersToPoints(200));
		table.setLockedWidth(true);
		table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		PdfPCell leftCell=new PdfPCell(paragraph);
		leftCell.setBorder(0);
		leftCell.setHorizontalAlignment(2);

		PdfPCell rightCell=new PdfPCell(para);
		rightCell.setBorder(0);
		rightCell.setHorizontalAlignment(0);
		PdfPCell centerCell=new PdfPCell(new Paragraph(""));
		centerCell.setBorder(0);
		centerCell.setHorizontalAlignment(0);

		table.setHorizontalAlignment(1);
		table.addCell(rightCell);
		table.addCell(centerCell);
		table.addCell(leftCell);
		ct.addElement(table);


		PdfPTable titleTable=new PdfPTable(1);
		titleTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		titleTable.setWidthPercentage(50);
		PdfPCell cell;
		Paragraph par=new Paragraph("طلب استغلال القاعة ",font);
		cell= new PdfPCell(par);
		cell.setPaddingTop(10);
		cell.setPaddingBottom(10);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		titleTable.addCell(cell);
		titleTable.setSpacingAfter(20);
		ct.addElement(titleTable);

		Paragraph parag2=new Paragraph();
		parag2.setFont(textFont);
		parag2.add("                سلام تام بوجود مولانا الإمام ");
		parag2.setSpacingAfter(10);
		parag2.setSpacingBefore(20);
		parag2.setExtraParagraphSpace(2);
		ct.addElement(parag2);

		Paragraph paragraph3=new Paragraph();
		paragraph3.setFont(textFont);
		paragraph3.add("                وبعد، يشرفني أن ألتمس منكم السماح لمؤسستنا باستغلال القاعة : "+p.getSalle().getDesignation()+"\n                ");
		String text;
		if(p.getDateDebut().equals(p.getDateFin()))
			text="  يوم : "+getArabicDate(p.getDateDebut())+"  ابتداء من الساعة : "+timeFormater(p.getHeureDebut())+"  إلى الساعة  :  "+timeFormater(p.getHeureFin())+" .\n";
		else
			text=" من يوم : "+getArabicDate(p.getDateDebut())+"  إلى يوم  :"+getArabicDate(p.getDateFin())+"\n                "+
					"ابتداء من الساعة : "+timeFormater(p.getHeureDebut())+"  إلى الساعة  :  "+timeFormater(p.getHeureFin())+" .\n";
		paragraph3.add(text);
		paragraph3.add("                وذلك من أجل :  "+p.getActivite());
		paragraph3.cloneShallow(false);
		paragraph3.setSpacingAfter(15);
		paragraph3.setSpacingBefore(3);
		paragraph3.setExtraParagraphSpace(10);
		ct.addElement(paragraph3);

		Paragraph paragrap=new Paragraph();
		paragrap.setFont(textFont);
		paragrap.add("                هذا وأصرح بما يلي : ");
		paragrap.cloneShallow(false);
		paragrap.setSpacingAfter(10);
		paragrap.setSpacingBefore(3);
		ct.addElement(paragrap);

		Paragraph paragraph5=new Paragraph();
		paragraph5.setFont(textFont);
		paragraph5.add("                - أنني أخبرت السلطات الإدارية بهذا النشاط "+"\n\n");
		paragraph5.add("                - أنني سأحافظ على المعدات المتواجدة بالقاعة والعمل على حسن استغلالها"+"\n\n");
		paragraph5.add("                - ألتزم بتعويض أي معدة تعرضت للإتلاف "+"\n\n");
		paragraph5.add("                - أن أحافظ على النظام بالقاعة أثناء ممارسة النشاط"+"\n\n");
		paragraph5.cloneShallow(false);
		paragraph5.setSpacingAfter(30);
		paragraph5.setSpacingBefore(3);
		ct.addElement(paragraph5);

		Paragraph paragraph4=new Paragraph();
		paragraph4.setFont(textFont);
		paragraph4.setAlignment(1);
		paragraph4.add("التوقيع");
		paragraph4.cloneShallow(false);
		paragraph4.setSpacingAfter(70);
		paragraph4.setSpacingBefore(3);
		ct.addElement(paragraph4);

		try {
			ct.go();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}




	// Outils

	private PdfPCell getCell(int cm,String info) {
	    PdfPCell cell = new PdfPCell();
	    cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
	    cell.setColspan(cm);
	    cell.setMinimumHeight(25);
	    cell.setUseAscender(true);
	    cell.setUseDescender(true);
	    cell.setSpaceCharRatio(10);
	    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	    Paragraph p = new Paragraph(
	            String.format("\n%s", info),font);
	    p.setAlignment(Element.ALIGN_CENTER);
	    cell.addElement(p);
	    return cell;
	}

	private PdfPCell getCellList(int cm,String info) {
		PdfPCell cell = new PdfPCell();
		cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		cell.setColspan(cm);
		//cell.setMinimumHeight(25);
		cell.setUseAscender(true);
		cell.setUseDescender(true);
		cell.setSpaceCharRatio(10);
		cell.setPaddingTop(6);
		cell.setPaddingBottom(6);
		cell.setPaddingLeft(4);
		cell.setPaddingRight(4);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		Paragraph p = new Paragraph(
				String.format("%s", info),fontList);
		p.setAlignment(Element.ALIGN_LEFT);
		cell.addElement(p);
		return cell;
	}

	private String timeFormater(Time time)
	{
		return time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	private String dateFormater(Date date)
	{
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		return format1.format(date);
	}

	private String getFrenchDate(Date date)
	{
		final String[] months= {"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return ""+calendar.get(Calendar.DAY_OF_MONTH)+" "+months[date.getMonth()]+" "+calendar.get(Calendar.YEAR);
	}


	private String getDayName(Date date){
		final String[] days= {"الأحد","الإثنين","الثلاثاء","الأربعاء","الخميس","الجمعة","السبت","الأحد"};
		return ""+days[(date.getDay())];
	}

	private String getArabicDate(Date date)
	{
		final String[] months= {"يناير","فبراير","مارس","أبريل","ماي","يونيو","يوليوز","غشت","شتنبر","أكتوبر","نونبر","دجنبر"};
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return ""+calendar.get(Calendar.DAY_OF_MONTH)+" "+months[date.getMonth()]+" "+calendar.get(Calendar.YEAR);
	}
}
