package org.dyndns.delphyne.k8spelling.view

import java.awt.Component

import javax.swing.JButton
import javax.swing.JPanel

interface GuiPanel {
    JPanel getWidget()
    Component getDefaultFocus()
    JButton getDefaultButton()
}
