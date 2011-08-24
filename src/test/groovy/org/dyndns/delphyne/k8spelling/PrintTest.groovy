package org.dyndns.delphyne.k8spelling

import java.awt.Graphics
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob

import javax.swing.JPanel
import javax.swing.RepaintManager

import org.dyndns.delphyne.k8spelling.view.MainWindow
import org.dyndns.delphyne.k8spelling.view.WordStatusPanel
import org.junit.Test

import groovy.util.logging.Slf4j

class PrintTest extends TestBase {
    @Test
    void testPrintSetup() {
        def mw = new MainWindow()
        def toBePrinted = mw.swing.wordStatusPanel
        
        PrinterJob job = PrinterJob.printerJob
        job.setPrintable(new PrintThisCanvas(toBePrinted: toBePrinted))
        if (job.printDialog()) {
            job.print()
        }
    }
}

@Slf4j
class PrintThisCanvas implements Printable {
    JPanel toBePrinted
    int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        log.info "attempt to print page ${pageIndex}"
        if (pageIndex == 0) {
            log.info "Printing page ${pageIndex}"
            graphics.translate(pageFormat.imageableX, pageFormat.imageableY)
            RepaintManager.currentManager(toBePrinted).doubleBufferingEnabled = false
            toBePrinted.paint(graphics)
            RepaintManager.currentManager(toBePrinted).doubleBufferingEnabled = true
            PAGE_EXISTS
        } else {
            log "No such page"
            NO_SUCH_PAGE
        }
    }
}