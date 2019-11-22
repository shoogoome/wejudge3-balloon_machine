package net.wejudge.utils;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.sun.net.httpserver.HttpHandler;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import net.wejudge.controller.EditController;
import net.wejudge.controller.ListenController;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import gui.ava.html.image.generator.HtmlImageGenerator;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;


import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;
import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer {

    /**
     * 获取可使用的打印机列表
     *
     * @return
     */
    public static Map<String, PrintService> findPrinterServices() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        Map<String, PrintService> printerServiceName = new HashMap<>();

        for (int i = 0; i < printServices.length; i++) {
            printerServiceName.put(printServices[i].getName(), printServices[i]);
        }
        return printerServiceName;
    }

    /**
     * html转image
     *
     * @param htmlStr
     * @return
     */
    public static Image generatePDF(String htmlStr) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(htmlStr);
        return SwingFXUtils.toFXImage(imageGenerator.getBufferedImage(), null);
    }

    /**
     * 打印
     * //     * @param printService
     *
     * @param htmlStr
     */
    public static void printerStatus(String htmlStr) throws Exception{

        ListenController listenController = ListenController.getInstance();
        if (listenController.printList.getValue() == null) {
            throw new Exception("未选择打印机");
        }
        Map<String, PrintService> a = Printer.findPrinterServices();
        PrintService printService = a.get(listenController.printList.getValue());
        // 打印类型
        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        // 打印属性
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        // 设置打印尺寸，请勿修改。经过多次试验调整的数值
        MediaPrintableArea area = new MediaPrintableArea(0, 0, 110,  110, MediaPrintableArea.MM);
        pras.add(area);

        DocPrintJob job = printService.createPrintJob();
        DocAttributeSet das = new HashDocAttributeSet();

        // 输入流方式
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(htmlStr);
        imageGenerator.getBufferedImage();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(imageGenerator.getBufferedImage(), "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            Doc doc = new SimpleDoc(input, flavor, das);
            job.print(doc, pras);
        } catch (PrintException e) {
            throw new Exception("打印失败");
        }
    }


    public static void main(String[] args) {
//        Printer.printerStatus(EditController.printStyleDescription);
    }

}
