package pl.dmcs.bujazdowski.calculator

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import tornadofx.View


class Calculator : View() {
    override val root: VBox by fxml()

    @FXML
    lateinit var display: Label

    init {
        title = "Calculator TornadoFx"

        root.lookupAll(".button").forEach { b ->
            b.setOnMouseClicked {
                action((b as Button).text)
            }
        }

        root.addEventFilter(KeyEvent.KEY_TYPED) {
            action(it.character.toUpperCase().replace("\r", "="))
        }

    }

    private fun action(x: String) {
        if (Onp.isOperand(x) || Regex("[0-9.]").matches(x)) {
            display.text += x
        }  else if (x == "=") {
            val onp = Onp.convert(display.text)
            display.text = Onp.evaluate(onp).toString()
        } else if (x == "C") {
            display.text = ""
        }
    }
}
