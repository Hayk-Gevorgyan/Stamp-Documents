package org.stamp.java;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessStreamCache;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Stamping {
    private static void stampPDF(File inputFile, File outputFile) throws IOException {
        PDDocument document = Loader.loadPDF(inputFile);
        PDImageXObject stampImage = PDImageXObject.createFromFile(StampBuilder.STAMP, document);
        PDPage lastPage = document.getPages().get(document.getNumberOfPages() - 1);
        PDPageContentStream contentStream = new PDPageContentStream(document, lastPage, PDPageContentStream.AppendMode.OVERWRITE, false);

        float x = lastPage.getMediaBox().getLowerLeftX() + 20;
        float y = lastPage.getMediaBox().getLowerLeftY() + 20;

        contentStream.drawImage(stampImage, x, y);
        contentStream.close();
        try {
            document.save(outputFile);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stamp() {
        Scanner scanner = new Scanner(System.in);
        outer:
        while (true) {
            System.out.println("Enter the .pdf file path or 'exit' to exit the app");
            String inputFilePath = scanner.nextLine();
            if (inputFilePath.equals("exit")) {
                break;
            }
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                System.out.println("WARNING : Wrong File Path , File Does Not Exist!");
                continue;
            }
            File outputFile;
            while (true) {
                System.out.println("Enter the output file location and name or 'exit' to exit the app");
                String outputFilePath = scanner.nextLine();
                if (outputFilePath.equals("exit")) {
                    break outer;
                }
                try {
                    outputFile = new File(outputFilePath);
                    if (!outputFile.getParentFile().exists()) {
                        throw new IOException("No such directory");
                    }
                } catch (IOException e) {
                    System.out.println("Error : " + e.getMessage());
                    continue;
                } catch (NullPointerException e) {
                    System.out.println("Error : Not a path");
                    continue;
                }
                break;
            }
            try {
                stampPDF(inputFile, outputFile);
                System.out.println("file " + outputFile.getName() + " stamped and saved at " + outputFile.getParentFile().getName() + " successfully");
            } catch (IOException e) {
                System.out.println("Couldn't save the file");
            }
        }
    }
}
