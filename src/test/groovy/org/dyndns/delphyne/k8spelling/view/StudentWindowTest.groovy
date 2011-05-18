package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import org.dyndns.delphyne.k8spelling.GuiTestBase

class StudentWindowTest extends GuiTestBase {
    public static void main(String[] args) {
        buildGui(new StudentPanel().widget)
    }
}
