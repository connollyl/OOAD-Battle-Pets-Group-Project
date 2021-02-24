package GUI_Components;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Class that applies regular expression for format of each text box.
 * 
 * @author cummingsa
 *
 */
public class TextFieldMasker {

	/**
	 * Adds listener to text Text Field Objects based on regular expression.
	 * 
	 * @param txtF
	 * @param regex
	 */
	public static void addMask(TextField txtF, String regex) {
		txtF.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches(regex)) {
					try {

						Platform.runLater(() -> {

							txtF.setText(newValue.substring(0, newValue.length() - 1));
							txtF.positionCaret(newValue.length() - 1);
						});

					} catch (Exception e) {
						System.err.println(e);
					}

				}

			}
		});
	}

}
