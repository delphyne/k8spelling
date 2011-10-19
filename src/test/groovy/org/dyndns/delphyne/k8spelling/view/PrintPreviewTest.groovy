package org.dyndns.delphyne.k8spelling.view

import org.dyndns.delphyne.k8spelling.print.PrintPreview

class PrintPreviewTest {
    static void main(String[] args) {
        def pp = new PrintPreview()
        pp.pack()
        pp.visible = true
    }
}
