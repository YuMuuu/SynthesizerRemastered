import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{FlowPane, HBox, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{LinearGradient, Stops}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text

object App extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 613
    height = 357
    scene = new Scene {
      fill = Black
      content = new VBox {
        padding = Insets(20)
        children = Seq(
//          new Text {
//            text = "Hello "
//            style = "-fx-font-size: 48pt"
//            fill = new LinearGradient(
//              endX = 0,
//              stops = Stops(PaleGreen, SeaGreen))
//          },
//          new Text {
//            text = "World!!!"
//            style = "-fx-font-size: 48pt"
//            fill = new LinearGradient(
//              endX = 0,
//              stops = Stops(Cyan, DodgerBlue)
//            )
//            effect = new DropShadow {
//              color = DodgerBlue
//              radius = 25
//              spread = 0.25
//            }
//          }
          new FlowPane {
//            fill = LightGreen
          }
        )
      }
    }

  }
}
