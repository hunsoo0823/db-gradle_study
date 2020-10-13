package com.oreilly.hh

import javax.persistence.*

import java.awt.*
import java.awt.event.*
import javax.swing.*

class QueryTest2 : JPanel() {

    var model: DefaultListModel<String> = DefaultListModel()   // Lets us manipulate the list contents
    var list: JList<String> = JList(model)       // Will contain tracks associated with current artist

    /**
     * Build the panel containing UI elements
     */
    init {
        layout = BorderLayout()
        add(JScrollPane(list), BorderLayout.SOUTH)

        val artistField = JTextField(28)
        artistField.addKeyListener(object : KeyAdapter() {
            override fun keyTyped(e: KeyEvent?) {
                SwingUtilities.invokeLater { updateTracks(artistField.text) }
            }
        })
        add(artistField, BorderLayout.EAST)
        add(JLabel("Artist: "), BorderLayout.WEST)
    }

    /**
     * Update the list to contain the tracks associated with an artist
     */
    private fun updateTracks(name: String) {
        model.removeAllElements()       // Clear out previous tracks
        if (name.isEmpty()) return      // Nothing to do

        emf.createEntityManager().use { em ->
            em.transaction.use {
                val artist = getArtist(name, false, em)
                if (artist == null) {  // Unknown artist
                    model.addElement("Artist not found")
                } else {
                    // List the tracks associated with the artist
                    for (aTrack in artist.tracks) {
                        model.addElement("Track: '${aTrack.title}' ${aTrack.playTime}\n")
                    }
                }
            }
        }
    }

    companion object {
        val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("default")
    }
}

fun main() {
    // Set up the UI
    val frame = JFrame("Artist Track Lookup")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.contentPane = QueryTest2()
    frame.setSize(400, 180)
    frame.isVisible = true
}
