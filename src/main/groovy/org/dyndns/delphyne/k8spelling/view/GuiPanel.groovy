package org.dyndns.delphyne.k8spelling.view

import java.awt.Component

import javax.swing.JButton
import javax.swing.JPanel

interface GuiPanel {
    Component getDefaultFocus()
    JButton getDefaultButton()
    void onFocus()
}
